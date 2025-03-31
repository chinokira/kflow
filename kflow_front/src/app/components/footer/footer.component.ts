import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit, OnDestroy {
  isVisible = false;

  @HostListener('window:scroll')
  onScroll() {
    const scrollPosition = window.scrollY + window.innerHeight;
    const documentHeight = document.documentElement.scrollHeight;
    const threshold = 50; // Réduit de 100px à 50px pour une apparition plus tardive

    this.isVisible = scrollPosition >= documentHeight - threshold;
  }

  ngOnInit() {
    // Vérifier la position initiale
    this.onScroll();
  }

  ngOnDestroy() {
    // Nettoyage si nécessaire
  }
} 