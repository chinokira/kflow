import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-participants',
  templateUrl: './participants.component.html',
  styleUrls: ['./participants.component.scss'],
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
export class ParticipantsComponent {
  displayedColumns: string[] = ['id', 'name', 'category', 'club', 'competition', 'status', 'actions'];
  participants = [
    { 
      id: 1, 
      name: 'Jean Dupont', 
      category: 'Senior', 
      club: 'Club Freestyle Paris',
      competition: 'Championnat de France',
      status: 'Inscrit' 
    },
    { 
      id: 2, 
      name: 'Marie Martin', 
      category: 'Junior', 
      club: 'Club Freestyle Lyon',
      competition: 'Championnat de France',
      status: 'Inscrit' 
    }
  ];
} 