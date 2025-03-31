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
    templateUrl: './competition-detail.component.html',
    styleUrls: ['./competition-detail.component.scss']
})
export class CompetitionDetailComponent implements OnInit, OnDestroy {
    competition: Competition | null = null;
    loading = true;
    error: string | null = null;
    private competitionId: string | null = null;
    private subscription: Subscription | null = null;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly competitionService: CompetitionService
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