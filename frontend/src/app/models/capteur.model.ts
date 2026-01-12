export interface Capteur {
  id?: number;
  type: string;
  parcelleId: number;
  localisation: string;
  actif: boolean;
}
