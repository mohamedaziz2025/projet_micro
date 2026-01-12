import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav>
      <ul>
        <li><a routerLink="/dashboard" routerLinkActive="active">Dashboard</a></li>
        <li><a routerLink="/capteurs" routerLinkActive="active">Capteurs</a></li>
        <li><a routerLink="/observations" routerLinkActive="active">Observations</a></li>
        <li><a routerLink="/recommandations" routerLinkActive="active">Recommandations</a></li>
      </ul>
    </nav>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: []
})
export class AppComponent {
  title = 'Système d\'Irrigation Intelligente';
}
