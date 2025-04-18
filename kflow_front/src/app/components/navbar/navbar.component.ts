import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterModule } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';
import { FooterComponent } from '../footer/footer.component';

interface ConnectedUser {
  id: number;
  name: string;
  accessToken: string;
  refreshToken: string;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MatSidenavModule, MatListModule, MatIconModule, RouterModule, MatToolbarModule, FooterComponent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  connectedUser: ConnectedUser | undefined;

  constructor(private readonly authService: AuthenticationService) {}

  ngOnInit(): void {
    this.authService.connectedUser$.subscribe(user => {
      this.connectedUser = user;
    });
  }

  logout(): void {
    this.authService.logout();
  }
} 