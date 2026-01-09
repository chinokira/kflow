import { Component } from '@angular/core';
import { User, Role } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { InsertErrorDirective } from '../../directives/insert-error.directive';


@Component({
    selector: 'app-sign-up',
    standalone: true,
    templateUrl: './sign-up.component.html',
    styleUrl: './sign-up.component.css',
    imports: [ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, InsertErrorDirective]
})
export class SignUpComponent {

  userFormGroup = User.formGroup();
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(
    private readonly userService: UserService,
    private readonly router: Router
  ) {}

  onSubmit() {
    if (this.userFormGroup.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      
      this.userService.save(this.userFormGroup.value as User).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigateByUrl("/login");
        },
        error: (error) => {
          this.isLoading = false;
          console.error('Erreur lors de l\'inscription:', error);
          
          // Gérer les différents types d'erreurs
          if (error.status === 400 && error.error) {
            this.errorMessage = error.error;
          } else if (error.status === 409) {
            this.errorMessage = 'Un utilisateur avec cet email existe déjà';
          } else {
            this.errorMessage = 'Une erreur est survenue lors de l\'inscription. Veuillez réessayer.';
          }
        }
      });
    } else {
      this.userFormGroup.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigateByUrl("");
  }

  redirectToLogin() {
    this.router.navigateByUrl("/login");
  }

  redirectToHome() {
    this.router.navigateByUrl("/");
  }
  
}
