import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CapteurService } from '../../services/capteur.service';
import { Capteur } from '../../models/capteur.model';

@Component({
  selector: 'app-capteurs',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './capteurs.component.html',
  styleUrls: ['./capteurs.component.css']
})
export class CapteursComponent implements OnInit {
  capteurs: Capteur[] = [];
  loading = true;
  showForm = false;
  editMode = false;
  
  capteur: Capteur = {
    type: '',
    parcelleId: 1,
    localisation: '',
    actif: true
  };

  private capteurService = inject(CapteurService);

  ngOnInit(): void {
    this.loadCapteurs();
  }

  loadCapteurs(): void {
    this.capteurService.getAllCapteurs().subscribe({
      next: (data) => {
        this.capteurs = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des capteurs', error);
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
    this.capteur = {
      type: '',
      parcelleId: 1,
      localisation: '',
      actif: true
    };
    this.editMode = false;
  }

  editCapteur(c: Capteur): void {
    this.capteur = { ...c };
    this.editMode = true;
    this.showForm = true;
  }

  saveCapteur(): void {
    if (this.editMode && this.capteur.id) {
      this.capteurService.updateCapteur(this.capteur.id, this.capteur).subscribe({
        next: () => {
          this.loadCapteurs();
          this.toggleForm();
        },
        error: (error) => console.error('Erreur lors de la mise à jour', error)
      });
    } else {
      this.capteurService.createCapteur(this.capteur).subscribe({
        next: () => {
          this.loadCapteurs();
          this.toggleForm();
        },
        error: (error) => console.error('Erreur lors de la création', error)
      });
    }
  }

  deleteCapteur(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce capteur ?')) {
      this.capteurService.deleteCapteur(id).subscribe({
        next: () => this.loadCapteurs(),
        error: (error) => console.error('Erreur lors de la suppression', error)
      });
    }
  }
}
