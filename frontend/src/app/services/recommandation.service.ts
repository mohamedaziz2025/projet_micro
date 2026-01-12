import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Recommandation } from '../models/recommandation.model';
import { appConfig } from '../app.config';

@Injectable({
  providedIn: 'root'
})
export class RecommandationService {
  private http = inject(HttpClient);
  private apiUrl = `${appConfig.apiUrl}/analyse/recommandations`;

  getAllRecommandations(): Observable<Recommandation[]> {
    return this.http.get<Recommandation[]>(this.apiUrl);
  }

  getRecommandationsByParcelleId(parcelleId: number): Observable<Recommandation[]> {
    return this.http.get<Recommandation[]>(`${this.apiUrl}/parcelle/${parcelleId}`);
  }

  getLastRecommandationByParcelleId(parcelleId: number): Observable<Recommandation> {
    return this.http.get<Recommandation>(`${this.apiUrl}/parcelle/${parcelleId}/derniere`);
  }

  genererRecommandation(parcelleId: number): Observable<Recommandation> {
    return this.http.post<Recommandation>(`${this.apiUrl}/parcelle/${parcelleId}/generer`, {});
  }
}
