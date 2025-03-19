import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';

interface Task {
  id: number;
  title: string;
  description: string;
  project: string;
  assignedTo: string;
  dueDate: Date;
  priority: string;
  status: string;
}

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements OnInit {
  displayedColumns: string[] = ['id', 'title', 'description', 'project', 'assignedTo', 'dueDate', 'priority', 'status', 'actions'];
  dataSource = new MatTableDataSource<Task>();

  constructor(private dialog: MatDialog) { }

  ngOnInit(): void {
    // TODO: Charger les tâches depuis le backend
    this.dataSource.data = [
      {
        id: 1,
        title: 'Tâche A',
        description: 'Description de la tâche A',
        project: 'Projet A',
        assignedTo: 'John Doe',
        dueDate: new Date('2024-03-31'),
        priority: 'Haute',
        status: 'En cours'
      },
      {
        id: 2,
        title: 'Tâche B',
        description: 'Description de la tâche B',
        project: 'Projet B',
        assignedTo: 'Jane Smith',
        dueDate: new Date('2024-04-15'),
        priority: 'Moyenne',
        status: 'À faire'
      }
    ];
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openAddTaskDialog() {
    // TODO: Implémenter l'ouverture du dialogue d'ajout de tâche
  }

  editTask(task: Task) {
    // TODO: Implémenter l'édition d'une tâche
  }

  deleteTask(task: Task) {
    // TODO: Implémenter la suppression d'une tâche
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString('fr-FR');
  }

  getPriorityColor(priority: string): string {
    switch (priority.toLowerCase()) {
      case 'haute':
        return '#f44336';
      case 'moyenne':
        return '#ff9800';
      case 'basse':
        return '#4caf50';
      default:
        return '#757575';
    }
  }
} 