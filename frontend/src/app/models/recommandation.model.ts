export interface Recommandation {
  id?: number;
  parcelleId: number;
  quantiteEau: number;
  justification: string;
  dateCalcul: string;
  modeleId?: number;
  urgence: 'FAIBLE' | 'MOYEN' | 'ELEVE' | 'CRITIQUE';
  confianceScore?: number;
}
