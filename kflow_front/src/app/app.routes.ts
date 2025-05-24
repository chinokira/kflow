import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { UsersComponent } from './components/users/users.component';
import { CompetitionsComponent } from './pages/competitions/competitions.component';
import { CompetitionDetailComponent } from './pages/competitions/competition-detail/competition-detail.component';
import { CompetitionEditComponent } from './pages/competitions/competition-edit/competition-edit.component';
import { CompetitionImportComponent } from './pages/competitions/competition-import/competition-import.component';
import { LoginComponent } from './components/login/login.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { authGuard } from './guards/auth.guard';
import { adminGuard } from './guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'competitions', component: CompetitionsComponent, canActivate: [authGuard] },
  { path: 'competitions/import', component: CompetitionImportComponent, canActivate: [authGuard, adminGuard] },
  { path: 'competitions/:id', component: CompetitionDetailComponent, canActivate: [authGuard] },
  { path: 'competitions/:id/edit', component: CompetitionEditComponent, canActivate: [authGuard, adminGuard] },
  { path: 'users', component: UsersComponent, canActivate: [authGuard, adminGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: SignUpComponent },
  { path: 'profile', component: UserProfileComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '/home' }
];
