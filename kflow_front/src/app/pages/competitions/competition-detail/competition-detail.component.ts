import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { CompetitionService } from '../../../services/competition.service';
import { Competition } from '../../../models/competition-detail.model';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-competition-detail',
    standalone: true,
    imports: [
        CommonModule,
        MatProgressSpinnerModule,
        MatCardModule,
        MatDividerModule,
        MatExpansionModule
    ],
    providers: [CompetitionService],
    template: `
    <div class="competition-detail">
      <div *ngIf="loading" class="loading">
        <mat-spinner></mat-spinner>
      </div>
      <div *ngIf="error" class="error">
        {{ error }}
      </div>
      <div *ngIf="competition && !loading" class="content">
        <mat-card class="competition-header">
          <mat-card-header>
            <mat-card-title>{{ competition.place }}</mat-card-title>
            <mat-card-subtitle>{{ competition.level }}</mat-card-subtitle>
          </mat-card-header>
          <mat-card-content>
            <p class="dates">Du {{ competition.startDate | date:'dd/MM/yyyy' }} au {{ competition.endDate | date:'dd/MM/yyyy' }}</p>
          </mat-card-content>
        </mat-card>

        <div class="categories-section">
          <h2>Catégories</h2>
          <div class="categories-grid">
            <mat-card *ngFor="let category of competition.categories || []" class="category-card">
              <mat-card-header>
                <mat-card-title>{{ category.name }}</mat-card-title>
                <mat-card-subtitle>{{ (category.participants || []).length }} participants</mat-card-subtitle>
              </mat-card-header>
              <mat-card-content>
                <mat-accordion>
                  <mat-expansion-panel>
                    <mat-expansion-panel-header>
                      <mat-panel-title>
                        Liste des participants
                      </mat-panel-title>
                    </mat-expansion-panel-header>
                    <div *ngFor="let participant of category.participants || []" class="participant-item">
                      <strong>{{ participant.name }}</strong>
                      <span class="bib-number">Dossard #{{ participant.bibNb }}</span>
                      <div class="runs-list">
                        <div *ngFor="let run of participant.runs || []" class="run-item">
                          <span class="stage-name">{{ run.stageName }}</span>
                          <span class="run-details">Durée: {{ run.duration }}s - Score: {{ run.score }}</span>
                        </div>
                      </div>
                    </div>
                  </mat-expansion-panel>
                </mat-accordion>
              </mat-card-content>
            </mat-card>
          </div>
        </div>
      </div>
    </div>
  `,
    styles: [`
    .competition-detail {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }
    .loading {
      display: flex;
      justify-content: center;
      padding: 20px;
    }
    .error {
      color: red;
      padding: 20px;
      text-align: center;
    }
    .competition-header {
      margin-bottom: 2rem;
    }
    .dates {
      font-size: 1.1em;
      color: #666;
      margin-top: 1rem;
    }
    .categories-section {
      margin-top: 2rem;
    }
    .categories-section h2 {
      margin-bottom: 1rem;
      color: #333;
    }
    .categories-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 1rem;
    }
    .category-card {
      margin-bottom: 1rem;
    }
    .participant-item {
      padding: 0.5rem 0;
      border-bottom: 1px solid #eee;
    }
    .participant-item:last-child {
      border-bottom: none;
    }
    .bib-number {
      color: #666;
      margin-left: 1rem;
    }
    .runs-list {
      margin-top: 0.5rem;
      padding-left: 1rem;
    }
    .run-item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0.25rem 0;
      font-size: 0.9em;
    }
    .stage-name {
      font-weight: 500;
      color: #444;
    }
    .run-details {
      color: #666;
    }
  `]
})
export class CompetitionDetailComponent implements OnInit, OnDestroy {
    competition: Competition | null = null;
    loading = true;
    error: string | null = null;
    private competitionId: string | null = null;
    private subscription: Subscription | null = null;

    constructor(
        private route: ActivatedRoute,
        private competitionService: CompetitionService
    ) { }

    ngOnInit() {
        this.competitionId = this.route.snapshot.paramMap.get('id');
        if (this.competitionId) {
            this.loadCompetitionData();
        } else {
            this.error = "ID de compétition non trouvé";
            this.loading = false;
        }
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

    private loadCompetitionData() {
        if (!this.competitionId) return;

        this.loading = true;
        this.error = null;

        this.subscription = this.competitionService.findById(Number(this.competitionId))
            .subscribe({
                next: (data) => {
                    this.competition = data;
                    this.loading = false;
                },
                error: (error) => {
                    console.error('Erreur lors du chargement de la compétition:', error);
                    this.error = "Impossible de charger la compétition. Vérifiez que le serveur est démarré et que la compétition existe.";
                    this.loading = false;
                }
            });
    }
} 