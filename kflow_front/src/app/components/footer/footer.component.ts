import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <footer class="footer">
      <div class="footer-content">
        <p>&copy; 2024 KFlow. Tous droits réservés.</p>
      </div>
    </footer>
  `,
  styles: [`
    .footer {
      position: fixed;
      bottom: 0;
      width: 100%;
      background-color: #f5f5f5;
      padding: 1rem;
      text-align: center;
      border-top: 1px solid #ddd;
    }
    .footer-content {
      max-width: 1200px;
      margin: 0 auto;
    }
  `]
})
export class FooterComponent {} 