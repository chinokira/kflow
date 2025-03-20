import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatListModule,
    FooterComponent
  ],
  template: `
    <mat-sidenav-container class="sidenav-container">
      <mat-sidenav #drawer class="sidenav" fixedInViewport
          [mode]="'side'"
          [opened]="true">
        <mat-nav-list>
          <a mat-list-item routerLink="/home" routerLinkActive="active">
            <mat-icon>home</mat-icon>
            <span>Accueil</span>
          </a>
          <a mat-list-item routerLink="/competitions" routerLinkActive="active">
            <mat-icon>emoji_events</mat-icon>
            <span>Compétitions</span>
          </a>
          <a mat-list-item routerLink="/users" routerLinkActive="active">
            <mat-icon>manage_accounts</mat-icon>
            <span>Utilisateurs</span>
          </a>
        </mat-nav-list>
      </mat-sidenav>
      <mat-sidenav-content>
        <mat-toolbar color="primary">
          <button
            type="button"
            aria-label="Toggle sidenav"
            mat-icon-button
            (click)="drawer.toggle()">
            <mat-icon aria-label="Side nav toggle icon">menu</mat-icon>
          </button>
          <span class="app-title">KFlow</span>
        </mat-toolbar>
        <div class="content-container">
          <router-outlet></router-outlet>
        </div>
        <app-footer></app-footer>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .sidenav-container {
      height: 100vh;
    }
    .sidenav {
      width: 250px;
    }
    .app-title {
      margin-left: 1rem;
      font-size: 1.5rem;
    }
    .content-container {
      padding: 1rem;
      margin-bottom: 60px;
    }
    .active {
      background-color: rgba(255, 255, 255, 0.1);
    }
  `]
})
export class NavbarComponent { } 