import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Capteur } from '../models/capteur.model';
import { appConfig } from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class CapteurService {
  private http = inject(HttpClient);
  private apiUrl = `${appConfig.apiUrl}/collecte/capteurs`;

  getAllCapteurs(): Observable<Capteur[]> {
    return this.http.get<Capteur[]>(this.apiUrl);
  }

  getCapteurById(id: number): Observable<Capteur> {
    return this.http.get<Capteur>(`${this.apiUrl}/${id}`);
  }

  getCapteursByParcelleId(parcelleId: number): Observable<Capteur[]> {
    return this.http.get<Capteur[]>(`${this.apiUrl}/parcelle/${parcelleId}`);
  }

  getCapteursActifs(): Observable<Capteur[]> {
    return this.http.get<Capteur[]>(`${this.apiUrl}/actifs`);
  }

  createCapteur(capteur: Capteur): Observable<Capteur> {
    return this.http.post<Capteur>(this.apiUrl, capteur);
  }

  updateCapteur(id: number, capteur: Capteur): Observable<Capteur> {
    return this.http.put<Capteur>(`${this.apiUrl}/${id}`, capteur);
  }

  deleteCapteur(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
