import { Component } from '@angular/core';
import { User, Role } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { InsertErrorDirective } from '../../directives/insert-error.directive';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css',
  imports: [CommonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatButtonModule, InsertErrorDirective],
  standalone: true
})
export class SignUpComponent {

  userFormGroup = User.formGroup();

  constructor(
    private readonly userService: UserService,
    private readonly router: Router
  ) {}

  onSubmit() {
    if (this.userFormGroup.valid) {
      const { name, email, password } = this.userFormGroup.value;
      this.userService.save({ 
        name, 
        email, 
        password,
        role: Role.USER 
      } as User).subscribe({
        next: () => this.router.navigateByUrl("/login"),
        error: (error) => console.error('Erreur lors de l\'inscription:', error)
      });
    } else {
      this.userFormGroup.markAllAsTouched();
    }
  }

  onCancel() {
    this.router.navigateByUrl("");
  }
}
