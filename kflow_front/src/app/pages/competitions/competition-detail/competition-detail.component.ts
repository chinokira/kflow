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
                if (participants && participants.length > 0) {
                    let loadedCount = 0;
                    participants.forEach(p => {
                        this.participantService.getRuns(p.id).subscribe({
                            next: (runs: Run[]) => {
                                p.runs = runs;
                                loadedCount++;
                                if (loadedCount === participants.length) {
                                    categorie.participants = this.sortParticipantsByStages(participants);
                                    this.loadingParticipants[categorie.id] = false;
                                }
                            },
                            error: () => {
                                p.runs = [];
                                loadedCount++;
                                if (loadedCount === participants.length) {
                                    categorie.participants = this.sortParticipantsByStages(participants);
                                    this.loadingParticipants[categorie.id] = false;
                                }
                            }
                        });
                    });
                } else {
                    this.loadingParticipants[categorie.id] = false;
                }
            },
            error: () => {
                categorie.participants = [];
                this.loadingParticipants[categorie.id] = false;
            }
        });
    }

    private sortParticipantsByStages(participants: Participant[]): Participant[] {
        return [...participants].sort((a, b) => {
            const aStages = this.getUniqueStages(a);
            const bStages = this.getUniqueStages(b);
            // 1. Trier par nombre de stages (décroissant)
            if (bStages.length !== aStages.length) {
                return bStages.length - aStages.length;
            }
            // 2. Si égalité, trier par score du dernier stage (avec gestion des égalités)
            return this.compareLastStageRuns(a, b, aStages);
        });
    }

    private compareLastStageRuns(a: Participant, b: Participant, stages: string[]): number {
        if (!stages.length) return 0;
        const lastStage = stages[stages.length - 1];
        const aRuns = (a.runs || []).filter(run => (run.stage?.name || 'Stage inconnu') === lastStage).map(run => run.score || 0).sort((x, y) => y - x);
        const bRuns = (b.runs || []).filter(run => (run.stage?.name || 'Stage inconnu') === lastStage).map(run => run.score || 0).sort((x, y) => y - x);
        const len = Math.max(aRuns.length, bRuns.length);
        // Pour 1 stage : somme, puis run par run
        // Pour 3 stages : somme des 2 meilleurs, puis run par run
        // Pour 2 ou 4 stages : run par run
        if (stages.length === 1) {
            const aSum = aRuns.reduce((sum, v) => sum + v, 0);
            const bSum = bRuns.reduce((sum, v) => sum + v, 0);
            if (bSum !== aSum) return bSum - aSum;
        } else if (stages.length === 3) {
            const aSum = (aRuns[0] || 0) + (aRuns[1] || 0);
            const bSum = (bRuns[0] || 0) + (bRuns[1] || 0);
            if (bSum !== aSum) return bSum - aSum;
        }
        // Comparaison run par run (meilleur, puis 2e, puis 3e, etc.)
        for (let i = 0; i < len; i++) {
            const aScore = aRuns[i] || 0;
            const bScore = bRuns[i] || 0;
            if (bScore !== aScore) return bScore - aScore;
        }
        return 0;
    }

    public getUniqueStages(participant: Participant): string[] {
        // Retourne la liste des noms de stages uniques, dans l'ordre d'apparition des runs
        const seen = new Set<string>();
        const stages: string[] = [];
        (participant.runs || []).forEach(run => {
            const name = run.stage?.name || 'Stage inconnu';
            if (!seen.has(name)) {
                seen.add(name);
                stages.push(name);
            }
        });
        return stages;
    }

    private getLastStageScore(participant: Participant, stages: string[]): number {
        if (!stages.length) return 0;
        const lastStage = stages[stages.length - 1];
        const runs = (participant.runs || []).filter(run => (run.stage?.name || 'Stage inconnu') === lastStage);
        if (stages.length === 1) {
            // 1 stage : somme de tous les runs
            return runs.reduce((sum, run) => sum + (run.score || 0), 0);
        } else if (stages.length === 3) {
            // 3 stages : somme des deux meilleurs runs
            const sorted = runs.map(run => run.score || 0).sort((a, b) => b - a);
            return (sorted[0] || 0) + (sorted[1] || 0);
        } else {
            // 2 ou 4 stages (ou tout autre cas) : meilleur run
            return Math.max(...runs.map(run => run.score || 0), 0);
        }
    }

    loadRunsForParticipant(participant: Participant) {
        if (!participant || (participant.runs && participant.runs.length > 0)) {
            return;
        }
        this.loadingRuns[participant.id] = true;
        this.participantService.getRuns(participant.id).subscribe({
            next: (runs: Run[]) => {
                participant.runs = runs;
                if (participant.categories && participant.categories.length > 0) {
                    const categorie = participant.categories[0];
                    categorie.participants = this.sortParticipantsByStages(categorie.participants || []);
                }
                this.loadingRuns[participant.id] = false;
            },
            error: () => {
                participant.runs = [];
                this.loadingRuns[participant.id] = false;
            }
        });
    }

    groupRunsByStage(runs: Run[]): { stageName: string; runs: Run[] }[] {
        const groupedRuns = new Map<string, Run[]>();
        
        runs.forEach(run => {
            const stageName = run.stage?.name || 'Stage inconnu';
            if (!groupedRuns.has(stageName)) {
                groupedRuns.set(stageName, []);
            }
            groupedRuns.get(stageName)?.push(run);
        });

        return Array.from(groupedRuns.entries()).map(([stageName, runs]) => ({
            stageName,
            runs
        }));
    }

    getClassementScore(participant: Participant, stages: string[]): number {
        if (!stages.length) return 0;
        const lastStage = stages[stages.length - 1];
        const runs = (participant.runs || []).filter(run => (run.stage?.name || 'Stage inconnu') === lastStage).map(run => run.score || 0).sort((a, b) => b - a);
        if (stages.length === 1) {
            return runs.reduce((sum, v) => sum + v, 0);
        } else if (stages.length === 3) {
            return (runs[0] || 0) + (runs[1] || 0);
        } else {
            return runs[0] || 0;
        }
    }
} 