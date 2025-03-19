import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { MatTabsModule } from '@angular/material/tabs';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';

interface Competition {
  id: number;
  nom: string;
  lieu: string;
  dateDebut: string;
  dateFin: string;
  stages?: Stage[];
}

interface Stage {
  id: number;
  nom: string;
  phase: string;
  runs: Run[];
}

interface Run {
  id: number;
  score: number;
  participant: Participant;
  numero: number;
  phase?: string;
}

interface Participant {
  id: number;
  nom: string;
  prenom: string;
  club: string;
  dossard: string;
}

@Component({
  selector: 'app-competition-detail',
  templateUrl: './competition-detail.component.html',
  styleUrls: ['./competition-detail.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    MatTabsModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ]
})
export class CompetitionDetailComponent implements OnInit {
  competitionId: string | null = null;
  competition: Competition | null = null;
  participants: (Participant & { runs: Run[] })[] = [];
  displayedColumns: string[] = [
    'position',
    'dossard',
    'nom',
    'club',
    'qualification_run1',
    'qualification_run2',
    'qualification_total',
    'demiFinale_run1',
    'demiFinale_run2',
    'finale_run1',
    'finale_run2',
    'finale_run3'
  ];
  isLoading = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit() {
    this.competitionId = this.route.snapshot.paramMap.get('id');
    if (this.competitionId) {
      this.loadCompetitionData();
    } else {
      this.error = "ID de compétition non trouvé";
    }
  }

  private loadCompetitionData() {
    if (!this.competitionId) return;

    this.isLoading = true;
    this.error = null;

    // Charger la compétition avec ses stages et runs
    this.http.get<Competition>(`${environment.apiUrl}/competitions/${this.competitionId}`)
      .pipe(
        catchError(error => {
          console.error('Erreur lors du chargement de la compétition:', error);
          this.error = "Impossible de charger la compétition. Vérifiez que le serveur est démarré et que la compétition existe.";
          return of(null);
        }),
        finalize(() => {
          this.isLoading = false;
        })
      )
      .subscribe(competition => {
        if (!competition) return;
        
        this.competition = competition;

        if (!competition.stages || competition.stages.length === 0) {
          this.error = "Aucun stage trouvé pour cette compétition";
          return;
        }

        // Organiser les données pour l'affichage
        const participantsMap = new Map<number, Participant & { runs: Run[] }>();

        // Parcourir tous les stages et leurs runs
        competition.stages.forEach(stage => {
          stage.runs.forEach(run => {
            const participant = run.participant;
            if (!participantsMap.has(participant.id)) {
              participantsMap.set(participant.id, {
                ...participant,
                runs: []
              });
            }
            participantsMap.get(participant.id)?.runs.push({
              ...run,
              phase: stage.phase
            });
          });
        });

        this.participants = Array.from(participantsMap.values());

        if (this.participants.length === 0) {
          this.error = "Aucun participant trouvé pour cette compétition";
        }
      });
  }

  getRunValue(participant: Participant & { runs: Run[] }, phase: string, runNumber: number): number | null {
    const run = participant.runs.find(r => r.phase === phase && r.numero === runNumber);
    return run ? run.score : null;
  }

  getPhaseTotal(participant: Participant & { runs: Run[] }, phase: string): number | null {
    const phaseRuns = participant.runs.filter(r => r.phase === phase);
    if (phaseRuns.length === 0) return null;
    
    // Pour la qualification, on prend la somme des deux meilleurs runs
    if (phase === 'QUALIFICATION') {
      const scores = phaseRuns.map(r => r.score).sort((a, b) => b - a);
      return scores.slice(0, 2).reduce((sum, score) => sum + score, 0);
    }
    
    // Pour les autres phases, on prend le meilleur score
    return Math.max(...phaseRuns.map(r => r.score));
  }
} 