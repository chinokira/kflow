import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { Routes, provideRouter } from '@angular/router';
import { CompetitionsComponent } from './app/pages/competitions/competitions.component';
import { CompetitionDetailComponent } from './app/components/competition-detail/competition-detail.component';
import { HomeComponent } from './app/pages/home/home.component';
import { UsersComponent } from './app/pages/users/users.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UsersComponent },
  { path: 'competitions', component: CompetitionsComponent },
  { path: 'competitions/:id', component: CompetitionDetailComponent }
];

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter(routes),
    importProvidersFrom(BrowserAnimationsModule)
  ]
}).catch(err => console.error(err));
