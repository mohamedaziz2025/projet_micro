import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Observation } from '../models/observation.model';
import { appConfig } from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class ObservationService {
  private http = inject(HttpClient);
  private apiUrl = `${appConfig.apiUrl}/collecte/observations`;

  getAllObservations(): Observable<Observation[]> {
    return this.http.get<Observation[]>(this.apiUrl);
  }

  getObservationById(id: number): Observable<Observation> {
    return this.http.get<Observation>(`${this.apiUrl}/${id}`);
  }

  getObservationsByCapteurId(capteurId: number): Observable<Observation[]> {
    return this.http.get<Observation[]>(`${this.apiUrl}/capteur/${capteurId}`);
  }

  getObservationsByParcelleId(parcelleId: number): Observable<Observation[]> {
    return this.http.get<Observation[]>(`${this.apiUrl}/parcelle/${parcelleId}`);
  }

  createObservation(observation: Observation): Observable<Observation> {
    return this.http.post<Observation>(this.apiUrl, observation);
  }
}
