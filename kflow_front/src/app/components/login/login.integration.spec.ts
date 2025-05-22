import { TestBed, ComponentFixture, fakeAsync, tick } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';


describe('LoginComponent (intégration)', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpMock: HttpTestingController;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule.withRoutes([]),
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule
      ],
      providers: [AuthenticationService]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('doit se connecter et naviguer en cas de succès', fakeAsync(() => {
    spyOn(router, 'navigateByUrl').and.returnValue(Promise.resolve(true));
    component.credentials.setValue({ email: 'test@example.com', password: 'password123' });
    component.onSubmit();

    const req = httpMock.expectOne(req => req.url.includes('/authenticate'));
    expect(req.request.method).toBe('POST');
    
    // Simuler une réponse avec un token JWT valide
    const mockResponse = {
      accessToken: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJUZXN0IFVzZXIiLCJyb2xlIjoiVVNFUiIsImV4cCI6MTczNTY4OTYwMH0.signature',
      refreshToken: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwidXNlcm5hbWUiOiJUZXN0IFVzZXIiLCJyb2xlIjoiVVNFUiIsImV4cCI6MTczNTY4OTYwMH0.signature'
    };
    req.flush(mockResponse);

    tick();
    expect(router.navigateByUrl).toHaveBeenCalledWith('/');
    expect(component.error).toBe('');
  }));

  it('doit afficher une erreur si le backend retourne une erreur', fakeAsync(() => {
    spyOn(router, 'navigateByUrl');
    component.credentials.setValue({ email: 'test@example.com', password: 'wrong' });
    component.onSubmit();

    const req = httpMock.expectOne(req => req.url.includes('/authenticate'));
    expect(req.request.method).toBe('POST');
    req.flush({ message: 'Invalid credentials' }, { status: 401, statusText: 'Unauthorized' });

    tick();
    expect(component.error).toBeTruthy();
    expect(router.navigateByUrl).not.toHaveBeenCalled();
  }));
}); 