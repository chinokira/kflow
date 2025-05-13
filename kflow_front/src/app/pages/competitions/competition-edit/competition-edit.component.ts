import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CompetitionService } from '../../../services/competition.service';
import { Competition } from '../../../models/competition-detail.model';

@Component({
  selector: 'app-competition-edit',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './competition-edit.component.html',
  styleUrls: ['./competition-edit.component.scss']
})
export class CompetitionEditComponent implements OnInit {
  competitionForm: FormGroup;
  competition: Competition | null = null;
  isLoading = false;
  error: string | null = null;

  constructor(
    private readonly fb: FormBuilder,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly competitionService: CompetitionService,
    private readonly snackBar: MatSnackBar
  ) {
    this.competitionForm = this.fb.group({
      place: ['', Validators.required],
      level: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadCompetition(Number(id));
    }
  }

  loadCompetition(id: number) {
    this.isLoading = true;
    this.error = null;

    this.competitionService.findById(id).subscribe({
      next: (competition) => {
        this.competition = competition;
        this.competitionForm.patchValue({
          place: competition.place,
          level: competition.level,
          startDate: new Date(competition.startDate),
          endDate: new Date(competition.endDate)
        });
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement de la compétition:', error);
        this.error = 'Erreur lors du chargement de la compétition';
        this.isLoading = false;
        this.snackBar.open('Erreur lors du chargement de la compétition', 'Fermer', {
          duration: 5000
        });
      }
    });
  }

  onSubmit() {
    if (this.competitionForm.valid && this.competition) {
      const formValue = this.competitionForm.value;
      
      // Formatage des dates au format ISO (YYYY-MM-DD)
      const startDate = formValue.startDate instanceof Date ? 
        formValue.startDate.toISOString().split('T')[0] : 
        formValue.startDate;
      
      const endDate = formValue.endDate instanceof Date ? 
        formValue.endDate.toISOString().split('T')[0] : 
        formValue.endDate;

      const updatedCompetition: Partial<Competition> = {
        id: this.competition.id,
        place: formValue.place,
        level: formValue.level,
        startDate: startDate,
        endDate: endDate,
        categories: this.competition.categories || []
      };

      console.log('Sending competition update:', updatedCompetition);

      this.competitionService.update(updatedCompetition).subscribe({
        next: () => {
          this.snackBar.open('Compétition mise à jour avec succès', 'Fermer', {
            duration: 3000
          });
          this.router.navigate(['/competitions']);
        },
        error: (error) => {
          console.error('Erreur lors de la mise à jour de la compétition:', error);
          console.error('Request details:', {
            url: error.url,
            status: error.status,
            statusText: error.statusText,
            headers: error.headers,
            error: error.error
          });
          this.snackBar.open('Erreur lors de la mise à jour de la compétition', 'Fermer', {
            duration: 5000
          });
        }
      });
    }
  }

  onCancel() {
    this.router.navigate(['/competitions']);
  }
} 