import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterModule } from '@angular/router';
import { Competition, CompetitionService } from '../../services/competition.service';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-competitions',
  templateUrl: './competitions.component.html',
  styleUrls: ['./competitions.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatTooltipModule
  ]
})
export class CompetitionsComponent implements OnInit {
  displayedColumns: string[] = ['place', 'date', 'level', 'actions'];
  competitions: Competition[] = [];
  isLoading = false;
  error: string | null = null;

  constructor(
    private competitionService: CompetitionService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadCompetitions();
  }

  loadCompetitions() {
    this.isLoading = true;
    this.error = null;
    
    this.competitionService.findAll().subscribe({
      next: (data) => {
        if (!data || data.length === 0) {
          this.competitions = [];
          this.error = 'Aucune compétition trouvée';
        } else {
          this.competitions = data;
        }
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des compétitions:', error);
        this.error = 'Impossible de charger les compétitions';
        this.isLoading = false;
        this.competitions = [];
      }
    });
  }

  deleteCompetition(id: number) {
    this.competitionService.deleteById(id).subscribe({
      next: () => {
        this.competitions = this.competitions.filter(c => c.id !== id);
        this.snackBar.open('Compétition supprimée avec succès', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      },
      error: (error) => {
        console.error('Erreur lors de la suppression:', error);
        this.snackBar.open('Erreur lors de la suppression de la compétition', 'Fermer', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      }
    });
  }
} 