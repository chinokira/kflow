import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CompetitionService } from '../../services/competition.service';
import { CategorieService } from '../../services/categorie.service';
import { Competition, Categorie, Participant, Run, Stage } from '../../models/competition-detail.model';

@Component({
    selector: 'app-competition-detail',
    templateUrl: './competition-detail.component.html',
    styleUrls: ['./competition-detail.component.scss'],
    standalone: true,
    imports: [
        CommonModule,
        MatExpansionModule,
        MatProgressSpinnerModule
    ]
})
export class CompetitionDetailComponent implements OnInit {
    competition?: Competition;
    selectedCategorie?: Categorie;
    loading = false;

    constructor(
        private route: ActivatedRoute,
        private competitionService: CompetitionService,
        private categorieService: CategorieService
    ) { }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.loadCompetition(+id);
        }
    }

    loadCompetition(id: number): void {
        this.loading = true;
        this.competitionService.findById(id).subscribe({
            next: (competition) => {
                console.log('Competition chargée:', competition);
                this.competition = competition;
                this.loading = false;
            },
            error: (error) => {
                console.error('Erreur lors du chargement de la compétition:', error);
                this.loading = false;
            }
        });
    }

    loadCategorieParticipants(categorie: Categorie): void {
        console.log('Chargement des participants pour la catégorie:', categorie);
        if (this.selectedCategorie?.id === categorie.id) {
            console.log('Désélection de la catégorie');
            this.selectedCategorie = undefined;
            return;
        }

        this.loading = true;
        this.categorieService.getParticipants(categorie.id).subscribe({
            next: (categorieWithParticipants) => {
                console.log('Participants chargés:', categorieWithParticipants);
                this.selectedCategorie = categorieWithParticipants;
                this.loading = false;
            },
            error: (error) => {
                console.error('Erreur lors du chargement des participants:', error);
                this.loading = false;
            }
        });
    }

    getRunsForStage(participant: Participant, stageName: string): Run[] {
        if (!participant.runs) return [];
        return participant.runs.filter(run => run.stageName === stageName);
    }

    hasRunForStage(participant: Participant, stageName: string): boolean {
        return this.getRunsForStage(participant, stageName).length > 0;
    }
} 