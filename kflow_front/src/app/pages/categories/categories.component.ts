import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
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
export class CategoriesComponent {
  displayedColumns: string[] = ['id', 'name', 'description', 'ageMin', 'ageMax', 'actions'];
  categories = [
    { 
      id: 1, 
      name: 'Junior', 
      description: 'Catégorie pour les jeunes compétiteurs',
      ageMin: 12,
      ageMax: 16
    },
    { 
      id: 2, 
      name: 'Senior', 
      description: 'Catégorie principale',
      ageMin: 17,
      ageMax: 35
    }
  ];
} 