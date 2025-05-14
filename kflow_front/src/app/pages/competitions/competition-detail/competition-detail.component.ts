import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { CompetitionService } from '../../../services/competition.service';
import { CategorieService } from '../../../services/categorie.service';
import { Competition, Categorie, Participant, Run } from '../../../models/competition-detail.model';
import { Subscription } from 'rxjs';
import { ParticipantService } from '../../../services/participant.service';


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
    providers: [CompetitionService, CategorieService, ParticipantService],
    templateUrl: './competition-detail.component.html',
    styleUrls: ['./competition-detail.component.scss']
})
export class CompetitionDetailComponent implements OnInit, OnDestroy {
    competition: Competition | null = null;
    loading = true;
    error: string | null = null;
    private competitionId: string | null = null;
    private subscription: Subscription | null = null;
    loadingParticipants: { [categorieId: number]: boolean } = {};
    loadingRuns: { [participantId: number]: boolean } = {};

    constructor(
        private readonly route: ActivatedRoute,
        private readonly competitionService: CompetitionService,
        private readonly categorieService: CategorieService,
        private readonly participantService: ParticipantService,
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

        this.subscription = this.competitionService.getCompetitionWithDetails(Number(this.competitionId))
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

    loadParticipantsForCategorie(categorie: Categorie) {
        if (!categorie || (categorie.participants && categorie.participants.length > 0)) {
            return;
        }
        this.loadingParticipants[categorie.id] = true;
        this.categorieService.getParticipants(categorie.id).subscribe({
            next: (participants: Participant[]) => {
                console.log('Participants reçus pour catégorie', categorie.name, ':', participants);
                categorie.participants = participants;
                if (participants) {
                    participants.forEach(p => this.loadRunsForParticipant(p));
                }
                this.loadingParticipants[categorie.id] = false;
            },
            error: () => {
                categorie.participants = [];
                this.loadingParticipants[categorie.id] = false;
            }
        });
    }

    loadRunsForParticipant(participant: Participant) {
        if (!participant || (participant.runs && participant.runs.length > 0)) {
            return;
        }
        this.loadingRuns[participant.id] = true;
        this.participantService.getRuns(participant.id).subscribe({
            next: (runs: Run[]) => {
                participant.runs = runs;
                this.loadingRuns[participant.id] = false;
            },
            error: () => {
                participant.runs = [];
                this.loadingRuns[participant.id] = false;
            }
        });
    }
} 