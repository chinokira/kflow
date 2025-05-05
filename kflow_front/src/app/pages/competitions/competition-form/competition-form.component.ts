import { Component, OnInit } from '@angular/core';
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
import { AuthenticationService } from '../../../services/authentication.service';

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
export class CompetitionFormComponent implements OnInit {
  competitionForm: FormGroup;
  isLoading = false;
  error: string | null = null;
  isAdmin = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly competitionService: CompetitionService,
    private readonly router: Router,
    private readonly snackBar: MatSnackBar,
    private readonly authService: AuthenticationService
  ) {
    this.competitionForm = this.fb.group({
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      place: ['', Validators.required],
      level: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.authService.connectedUser$.subscribe(user => {
      this.isAdmin = user?.role === 'ADMIN';
      if (!this.isAdmin) {
        this.router.navigate(['/']);
        this.snackBar.open('Vous n\'avez pas les droits nécessaires pour créer une compétition', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      }
    });
  }

  goToImport(): void {
    this.router.navigate(['/competitions/import']);
  }

  onSubmit() {
    if (this.competitionForm.valid && this.isAdmin) {
      this.isLoading = true;
      this.error = null;

      const formValue = this.competitionForm.value;
      const startDate = this.formatDate(formValue.startDate);
      const endDate = this.formatDate(formValue.endDate);
      const competitionData = {
        ...formValue,
        startDate,
        endDate
      };

      this.competitionService.create(competitionData).subscribe({
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
          if (error.status === 401) {
            this.error = 'Votre session a expiré. Veuillez vous reconnecter.';
            this.authService.logout();
            this.router.navigate(['/login']);
          } else {
            this.error = 'Erreur lors de la création de la compétition';
          }
          this.snackBar.open(this.error, 'Fermer', {
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

  private formatDate(date: any): string {
    if (!date) return '';
    if (typeof date === 'string' && date.match(/^\d{4}-\d{2}-\d{2}$/)) {
      return date;
    }
    const d = new Date(date);
    return d.toISOString().slice(0, 10);
  }
} 