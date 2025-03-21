import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UsersComponent } from './pages/users/users.component';
import { CompetitionsComponent } from './pages/competitions/competitions.component';
import { CompetitionDetailComponent } from './pages/competitions/competition-detail/competition-detail.component';
import { CompetitionFormComponent } from './pages/competitions/competition-form/competition-form.component';
import { CompetitionImportComponent } from './pages/competitions/competition-import/competition-import.component';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'competitions', component: CompetitionsComponent },
  { path: 'competitions/new', component: CompetitionFormComponent },
  { path: 'competitions/import', component: CompetitionImportComponent },
  { path: 'competitions/:id', component: CompetitionDetailComponent },
  { path: 'users', component: UsersComponent }
];
