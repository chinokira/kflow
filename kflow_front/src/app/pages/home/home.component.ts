import { Component } from '@angular/core';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';

@Component({
    standalone: true,
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss'],
    imports: [
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    RouterModule
]
})
export class HomeComponent {
  welcomeMessage = 'Bienvenue sur KFlow';
  description = 'Application de gestion des compétitions de kayak freestyle';
} 