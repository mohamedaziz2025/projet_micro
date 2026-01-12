import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ObservationService } from '../../services/observation.service';
import { CapteurService } from '../../services/capteur.service';
import { Observation } from '../../models/observation.model';
import { Capteur } from '../../models/capteur.model';

@Component({
  selector: 'app-observations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './observations.component.html',
  styleUrls: ['./observations.component.css']
})
export class ObservationsComponent implements OnInit {
  observations: Observation[] = [];
  capteurs: Capteur[] = [];
  loading = true;
  showForm = false;
  
  observation: Observation = {
    capteurId: 0,
    valeur: 0,
    unite: '',
    date: new Date().toISOString()
  };

  private observationService = inject(ObservationService);
  private capteurService = inject(CapteurService);

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.capteurService.getAllCapteurs().subscribe({
      next: (capteurs) => {
        this.capteurs = capteurs;
        this.loadObservations();
      },
      error: (error) => console.error('Erreur lors du chargement des capteurs', error)
    });
  }

  loadObservations(): void {
    this.observationService.getAllObservations().subscribe({
      next: (data) => {
        this.observations = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des observations', error);
        this.loading = false;
      }
    });
  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (!this.showForm) {
      this.resetForm();
    }
  }

  resetForm(): void {
    this.observation = {
      capteurId: 0,
      valeur: 0,
      unite: '',
      date: new Date().toISOString()
    };
  }

  saveObservation(): void {
    this.observationService.createObservation(this.observation).subscribe({
      next: () => {
        this.loadObservations();
        this.toggleForm();
      },
      error: (error) => console.error('Erreur lors de la création', error)
    });
  }

  getCapteurInfo(capteurId: number): string {
    const capteur = this.capteurs.find(c => c.id === capteurId);
    return capteur ? `${capteur.type} - Parcelle ${capteur.parcelleId}` : 'Capteur inconnu';
  }
}
