import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule
  ]
})
export class DashboardComponent {
  stats = [
    { label: 'Compétitions', value: 5, icon: 'emoji_events' },
    { label: 'Participants', value: 150, icon: 'groups' },
    { label: 'Passages', value: 300, icon: 'sports_score' }
  ];
} 