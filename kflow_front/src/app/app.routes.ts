import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UsersComponent } from './pages/users/users.component';
import { CompetitionsComponent } from './pages/competitions/competitions.component';
import { CompetitionDetailComponent } from './pages/competitions/competition-detail/competition-detail.component';
import { CompetitionFormComponent } from './pages/competitions/competition-form/competition-form.component';
import { CompetitionImportComponent } from './pages/competitions/competition-import/competition-import.component';
import { StagesComponent } from './pages/stages/stages.component';
import { RunsComponent } from './pages/runs/runs.component';
import { ParticipantsComponent } from './pages/participants/participants.component';
import { CategoriesComponent } from './pages/categories/categories.component';

export const routes: Routes = [
  { path: '', redirectTo: '/competitions', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'competitions', component: CompetitionsComponent },
  { path: 'competitions/new', component: CompetitionFormComponent },
  { path: 'competitions/import', component: CompetitionImportComponent },
  { path: 'competitions/:id', component: CompetitionDetailComponent },
  { path: 'users', component: UsersComponent },
  { path: 'stages', component: StagesComponent },
  { path: 'runs', component: RunsComponent },
  { path: 'participants', component: ParticipantsComponent },
  { path: 'categories', component: CategoriesComponent }
];
