import { Component, OnInit } from '@angular/core';

import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { AuthenticationService } from '../../services/authentication.service';

interface ConnectedUser {
  id: number;
  name: string;
  accessToken: string;
  refreshToken: string;
}

@Component({
    standalone: true,
    selector: 'app-user-profile',
    imports: [MatCardModule, MatListModule, MatIconModule],
    template: `
    <div class="profile-container">
      <mat-card>
        <mat-card-header>
          <mat-card-title>Mon Profil</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <mat-list>
            <mat-list-item>
              <mat-icon matListItemIcon>person</mat-icon>
              <div matListItemTitle>Nom d'utilisateur</div>
              <div matListItemLine>{{user?.name}}</div>
            </mat-list-item>
            <mat-list-item>
              <mat-icon matListItemIcon>fingerprint</mat-icon>
              <div matListItemTitle>ID</div>
              <div matListItemLine>{{user?.id}}</div>
            </mat-list-item>
          </mat-list>
        </mat-card-content>
      </mat-card>
    </div>
  `,
    styles: [`
    .profile-container {
      padding: 20px;
      max-width: 600px;
      margin: 0 auto;
    }
    mat-card {
      margin-top: 20px;
    }
  `]
})
export class UserProfileComponent implements OnInit {
  user: ConnectedUser | undefined;

  constructor(private readonly authService: AuthenticationService) {}

  ngOnInit(): void {
    this.authService.connectedUser$.subscribe(user => {
      this.user = user;
    });
  }
} 