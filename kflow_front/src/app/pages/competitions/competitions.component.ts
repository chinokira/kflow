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

@Component({
  selector: 'app-competitions',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule
  ],
  template: `
    <div class="competitions-container">
      <div class="header">
        <h1>Compétitions</h1>
        <button mat-raised-button color="primary" routerLink="/competitions/new">
          <mat-icon>add</mat-icon>
          Nouvelle compétition
        </button>
      </div>

      <div class="competitions-grid">
        <mat-card *ngFor="let competition of competitions" class="competition-card">
          <mat-card-header>
            <mat-card-title>{{ competition.place }}</mat-card-title>
            <mat-card-subtitle>{{ competition.level }}</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <p>{{ competition.categories.length }} catégories</p>
            <p>Du {{ competition.startDate | date:'dd/MM/yyyy' }} au {{ competition.endDate | date:'dd/MM/yyyy' }}</p>
          </mat-card-content>
          <mat-card-actions>
            <button mat-button [routerLink]="['/competitions', competition.id]">
              <mat-icon>visibility</mat-icon> Voir
            </button>
            <button mat-button color="warn" (click)="deleteCompetition(competition.id)">
              <mat-icon>delete</mat-icon> Supprimer
            </button>
          </mat-card-actions>
        </mat-card>
      </div>
    </div>
  `,
  styles: [`
    .competitions-container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }
    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 2rem;
    }
    .competitions-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 1rem;
    }
    .competition-card {
      cursor: pointer;
      transition: transform 0.2s ease-in-out;
    }
    .competition-card:hover {
      transform: translateY(-5px);
    }
    mat-card-content {
      margin-top: 1rem;
    }
    mat-card-content p {
      margin: 0.5rem 0;
      color: #666;
    }
    mat-card-actions {
      display: flex;
      justify-content: flex-end;
      padding: 8px;
    }
  `]
})
export class CompetitionsComponent implements OnInit {
  competitions: Competition[] = [];

  constructor(
    private readonly competitionService: CompetitionService,
    private readonly snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.loadCompetitions();
  }

  private loadCompetitions() {
    this.competitionService.findAll().subscribe({
      next: (data) => {
        this.competitions = data;
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des compétitions:', error);
        this.snackBar.open('Erreur lors du chargement des compétitions', 'Fermer', {
          duration: 3000
        });
      }
    });
  }

  deleteCompetition(id: number) {
    this.competitionService.delete(id).subscribe({
      next: () => {
        this.competitions = this.competitions.filter(c => c.id !== id);
        this.snackBar.open('Compétition supprimée avec succès', 'Fermer', {
          duration: 3000
        });
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors de la suppression:', error);
        this.snackBar.open('Erreur lors de la suppression de la compétition', 'Fermer', {
          duration: 3000
        });
      }
    });
  }
} 