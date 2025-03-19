import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

interface Project {
  id: number;
  name: string;
  description: string;
  startDate: Date;
  endDate: Date;
  status: string;
  progress: number;
}

@Component({
  selector: 'app-projects',
  templateUrl: './projects.component.html',
  styleUrls: ['./projects.component.scss'],
  standalone: true,
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ]
})
export class ProjectsComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'description', 'startDate', 'endDate', 'status', 'progress', 'actions'];
  dataSource = new MatTableDataSource<Project>();

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
    // TODO: Charger les projets depuis le backend
    this.dataSource.data = [
      {
        id: 1,
        name: 'Projet A',
        description: 'Description du projet A',
        startDate: new Date('2024-01-01'),
        endDate: new Date('2024-12-31'),
        status: 'En cours',
        progress: 75
      },
      {
        id: 2,
        name: 'Projet B',
        description: 'Description du projet B',
        startDate: new Date('2024-02-01'),
        endDate: new Date('2024-08-31'),
        status: 'Planifié',
        progress: 0
      }
    ];
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddProjectDialog() {
    // TODO: Implémenter l'ouverture du dialogue d'ajout de projet
  }

  editProject(project: Project) {
    // TODO: Implémenter l'édition d'un projet
  }

  deleteProject(project: Project) {
    // TODO: Implémenter la suppression d'un projet
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }
} 