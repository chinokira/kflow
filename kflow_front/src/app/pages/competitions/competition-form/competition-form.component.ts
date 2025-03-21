import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CompetitionService } from '../../../services/competition.service';
import { Router } from '@angular/router';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { Competition } from '../../../models/competition-detail.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-competition-form',
  templateUrl: './competition-form.component.html',
  styleUrls: ['./competition-form.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatIconModule
  ]
})
export class CompetitionFormComponent {
  competitionForm: FormGroup;
  isLoading = false;
  error: string | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly competitionService: CompetitionService,
    private readonly router: Router,
    private readonly snackBar: MatSnackBar
  ) {
    this.competitionForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      place: ['', Validators.required],
      level: ['', Validators.required]
    });
  }

  goToImport(): void {
    this.router.navigate(['/competitions/import']);
  }

  onSubmit() {
    if (this.competitionForm.valid) {
      this.isLoading = true;
      this.error = null;

      this.competitionService.create(this.competitionForm.value).subscribe({
        next: (competition: Competition) => {
          this.snackBar.open('Compétition créée avec succès', 'Fermer', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top'
          });
          this.router.navigate(['/competitions', competition.id]);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Erreur lors de la création:', error);
          this.error = 'Erreur lors de la création de la compétition';
          this.snackBar.open('Erreur lors de la création de la compétition', 'Fermer', {
            duration: 3000,
            horizontalPosition: 'end',
            verticalPosition: 'top'
          });
        },
        complete: () => {
          this.isLoading = false;
        }
      });
    }
  }
} 