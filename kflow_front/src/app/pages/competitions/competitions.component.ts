import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CompetitionService } from '../../services/competition.service';
import { Competition } from '../../models/competition-detail.model';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-competitions',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './competitions.component.html',
  styleUrls: ['./competitions.component.scss']
})
export class CompetitionsComponent implements OnInit {
  competitions: Competition[] = [];
  isLoading = false;
  error: string | null = null;
  isAdmin = false;

  constructor(
    private readonly competitionService: CompetitionService,
    private readonly snackBar: MatSnackBar,
    private readonly authService: AuthenticationService
  ) {
    this.authService.connectedUser$.subscribe(user => {
      this.isAdmin = user?.role === 'ADMIN';
    });
  }

  ngOnInit() {
    this.loadCompetitions();
  }

  loadCompetitions() {
    this.isLoading = true;
    this.error = null;
    
    this.competitionService.findAll().subscribe({
      next: (data) => {
        this.competitions = data;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des compétitions:', error);
        this.error = 'Erreur lors du chargement des compétitions';
        this.isLoading = false;
        this.snackBar.open('Erreur lors du chargement des compétitions', 'Fermer', {
          duration: 5000
        });
      }
    });
  }

  deleteCompetition(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette compétition ?')) {
      this.competitionService.delete(id).subscribe({
        next: () => {
          this.competitions = this.competitions.filter(c => c.id !== id);
          this.snackBar.open('Compétition supprimée avec succès', 'Fermer', {
            duration: 3000
          });
        },
        error: (error: HttpErrorResponse) => {
          console.error('Erreur lors de la suppression de la compétition:', error);
          this.snackBar.open('Erreur lors de la suppression de la compétition', 'Fermer', {
            duration: 5000
          });
        }
      });
    }
  }
} 