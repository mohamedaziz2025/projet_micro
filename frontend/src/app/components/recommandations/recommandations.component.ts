import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RecommandationService } from '../../services/recommandation.service';
import { Recommandation } from '../../models/recommandation.model';

@Component({
  selector: 'app-recommandations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './recommandations.component.html',
  styleUrls: ['./recommandations.component.css']
})
export class RecommandationsComponent implements OnInit {
  recommandations: Recommandation[] = [];
  loading = true;
  parcelleId: number = 1;
  generationInProgress = false;

  private recommandationService = inject(RecommandationService);

  ngOnInit(): void {
    this.loadRecommandations();
  }

  loadRecommandations(): void {
    this.recommandationService.getAllRecommandations().subscribe({
      next: (data) => {
        this.recommandations = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des recommandations', error);
        this.loading = false;
      }
    });
  }

  genererRecommandation(): void {
    this.generationInProgress = true;
    this.recommandationService.genererRecommandation(this.parcelleId).subscribe({
      next: (recommandation) => {
        this.loadRecommandations();
        this.generationInProgress = false;
        alert('Recommandation générée avec succès !');
      },
      error: (error) => {
        console.error('Erreur lors de la génération', error);
        this.generationInProgress = false;
        alert('Erreur : ' + (error.error?.message || 'Impossible de générer la recommandation'));
      }
    });
  }

  getUrgenceClass(urgence: string): string {
    switch(urgence) {
      case 'CRITIQUE': return 'badge-danger';
      case 'ELEVE': return 'badge-warning';
      case 'MOYEN': return 'badge-info';
      case 'FAIBLE': return 'badge-success';
      default: return 'badge-info';
    }
  }
}
