import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-stages',
  templateUrl: './stages.component.html',
  styleUrls: ['./stages.component.scss'],
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
export class StagesComponent {
  displayedColumns: string[] = ['id', 'name', 'competition', 'date', 'status', 'actions'];
  stages = [
    { 
      id: 1, 
      name: 'Qualifications', 
      competition: 'Championnat de France',
      date: '2024-03-20',
      status: 'À venir'
    },
    { 
      id: 2, 
      name: 'Finale', 
      competition: 'Championnat de France',
      date: '2024-03-21',
      status: 'À venir'
    }
  ];
} 