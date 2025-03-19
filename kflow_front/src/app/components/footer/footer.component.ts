import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  template: `
    <footer class="footer">
      <p>&copy; 2024 KFlow. Tous droits réservés.</p>
    </footer>
  `,
  styles: [`
    .footer {
      padding: 1rem;
      text-align: center;
      background-color: #f5f5f5;
      margin-top: auto;
    }
  `]
})
export class FooterComponent {} 