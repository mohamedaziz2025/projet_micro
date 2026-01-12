# Données de test pour initialiser le système

## 1. Créer des modèles d'analyse

POST http://localhost:8080/api/analyse/modeles
Content-Type: application/json

{
  "type": "REGLE_SIMPLE",
  "version": "1.0.0",
  "precision": 85.5,
  "actif": true,
  "description": "Modèle basé sur des règles métier simples pour l'irrigation"
}

## 2. Créer des capteurs

### Capteur Humidité - Parcelle 1
POST http://localhost:8080/api/collecte/capteurs
Content-Type: application/json

{
  "type": "HUMIDITE",
  "parcelleId": 1,
  "localisation": "Parcelle Nord - Zone A",
  "actif": true
}

### Capteur Température - Parcelle 1
POST http://localhost:8080/api/collecte/capteurs
Content-Type: application/json

{
  "type": "TEMPERATURE",
  "parcelleId": 1,
  "localisation": "Parcelle Nord - Zone A",
  "actif": true
}

### Capteur Pluviométrie - Parcelle 1
POST http://localhost:8080/api/collecte/capteurs
Content-Type: application/json

{
  "type": "PLUVIOMETRIE",
  "parcelleId": 1,
  "localisation": "Parcelle Nord - Zone A",
  "actif": true
}

### Capteur Humidité - Parcelle 2
POST http://localhost:8080/api/collecte/capteurs
Content-Type: application/json

{
  "type": "HUMIDITE",
  "parcelleId": 2,
  "localisation": "Parcelle Sud - Zone B",
  "actif": true
}

## 3. Créer des observations

### Observations pour Parcelle 1 (Capteur ID 1 = Humidité)
POST http://localhost:8080/api/collecte/observations
Content-Type: application/json

{
  "capteurId": 1,
  "valeur": 35.5,
  "unite": "%",
  "date": "2026-01-03T08:00:00"
}

### Observations pour Parcelle 1 (Capteur ID 2 = Température)
POST http://localhost:8080/api/collecte/observations
Content-Type: application/json

{
  "capteurId": 2,
  "valeur": 28.5,
  "unite": "°C",
  "date": "2026-01-03T08:00:00"
}

### Observations pour Parcelle 1 (Capteur ID 3 = Pluviométrie)
POST http://localhost:8080/api/collecte/observations
Content-Type: application/json

{
  "capteurId": 3,
  "valeur": 2.5,
  "unite": "mm",
  "date": "2026-01-03T08:00:00"
}

## 4. Générer une recommandation

POST http://localhost:8080/api/analyse/recommandations/parcelle/1/generer

## 5. Récupérer les recommandations

GET http://localhost:8080/api/analyse/recommandations/parcelle/1

## 6. Obtenir la dernière recommandation

GET http://localhost:8080/api/analyse/recommandations/parcelle/1/derniere
