import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SignUpComponent } from './sign-up.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { UserService } from '../../services/user.service';
import { Role } from '../../models/user.model';

class MockUserService {
  save(user: any) {
    if (user.email === 'success@test.com') {
      return of({});
    } else {
      return throwError(() => new Error('Erreur d\'inscription'));
    }
  }
}

describe('SignUpComponent', () => {
  let component: SignUpComponent;
  let fixture: ComponentFixture<SignUpComponent>;
  let router: jasmine.SpyObj<Router>;
  let userService: MockUserService;

  beforeEach(async () => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate', 'navigateByUrl']);
    routerSpy.navigateByUrl.and.returnValue(Promise.resolve(true));
    routerSpy.navigate.and.returnValue(Promise.resolve(true));

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule,
        NoopAnimationsModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatCardModule
      ],
      providers: [
        { provide: Router, useValue: routerSpy },
        { provide: UserService, useClass: MockUserService }
      ]
    }).compileComponents();

    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    userService = TestBed.inject(UserService) as MockUserService;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('doit créer le composant', () => {
    expect(component).toBeTruthy();
  });

  it('doit valider le formulaire', () => {
    const form = component.userFormGroup;
    expect(form.valid).toBeFalsy();

    form.setValue({
      id: 0,
      name: 'Test User',
      email: 'test@example.com',
      password: 'password123',
      role: Role.USER
    });

    expect(form.valid).toBeTruthy();
  });

  it('doit inscrire avec succès', (done) => {
    component.userFormGroup.setValue({
      id: 0,
      name: 'Test User',
      email: 'success@test.com',
      password: 'password123',
      role: Role.USER
    });

    component.onSubmit();

    setTimeout(() => {
      expect(router.navigateByUrl).toHaveBeenCalledWith('/login');
      done();
    });
  });

  it('doit afficher une erreur si l\'inscription échoue', () => {
    spyOn(console, 'error');
    component.userFormGroup.setValue({
      id: 0,
      name: 'Test User',
      email: 'fail@test.com',
      password: 'password123',
      role: Role.USER
    });

    component.onSubmit();

    expect(console.error).toHaveBeenCalledWith('Erreur lors de l\'inscription:', jasmine.any(Error));
    expect(router.navigateByUrl).not.toHaveBeenCalled();
  });

  it('doit annuler et rediriger', () => {
    component.onCancel();
    expect(router.navigateByUrl).toHaveBeenCalledWith('');
  });
}); 