import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CapteurService } from '../../services/capteur.service';
import { ObservationService } from '../../services/observation.service';
import { RecommandationService } from '../../services/recommandation.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  stats = {
    totalCapteurs: 0,
    capteursActifs: 0,
    totalObservations: 0,
    totalRecommandations: 0
  };
  loading = true;

  private capteurService = inject(CapteurService);
  private observationService = inject(ObservationService);
  private recommandationService = inject(RecommandationService);

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    forkJoin({
      capteurs: this.capteurService.getAllCapteurs(),
      observations: this.observationService.getAllObservations(),
      recommandations: this.recommandationService.getAllRecommandations()
    }).subscribe({
      next: (data) => {
        this.stats.totalCapteurs = data.capteurs.length;
        this.stats.capteursActifs = data.capteurs.filter(c => c.actif).length;
        this.stats.totalObservations = data.observations.length;
        this.stats.totalRecommandations = data.recommandations.length;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement du dashboard', error);
        this.loading = false;
      }
    });
  }
}
