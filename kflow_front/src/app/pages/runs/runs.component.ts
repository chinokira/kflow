import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-runs',
  templateUrl: './runs.component.html',
  styleUrls: ['./runs.component.scss'],
  standalone: true,
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class RunsComponent {
  displayedColumns: string[] = ['id', 'participant', 'stage', 'score', 'status', 'actions'];
  runs = [
    { 
      id: 1, 
      participant: 'Jean Dupont', 
      stage: 'Qualifications', 
      score: 85.5, 
      status: 'Terminé' 
    },
    { 
      id: 2, 
      participant: 'Marie Martin', 
      stage: 'Qualifications', 
      score: 92.0, 
      status: 'Terminé' 
    }
  ];
} 