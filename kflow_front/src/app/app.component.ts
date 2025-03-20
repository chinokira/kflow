import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  template: `
    <div class="app-container">
      <app-navbar></app-navbar>
      <main>
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .app-container {
      height: 100vh;
      width: 100%;
      display: flex;
      flex-direction: column;
    }
    main {
      flex: 1;
      padding: 20px;
      overflow-y: auto;
    }
  `]
})
export class AppComponent {
  title = 'kflow';
}
