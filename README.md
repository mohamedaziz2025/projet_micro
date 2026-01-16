# Système d'Irrigation Intelligente

Application web pour la gestion de l'irrigation basée sur des capteurs IoT et une analyse automatique des données. Le système collecte les mesures (humidité, température, pluviométrie) et génère des recommandations d'irrigation adaptées.

## Architecture

L'application utilise une architecture microservices avec Spring Boot et Angular :

- **Frontend** (Angular 17) - Interface utilisateur sur le port 4200
- **API Gateway** (Port 8080) - Point d'entrée unique pour les requêtes
- **Microservice Collecte** (Port 8081) - Gestion des capteurs et observations
- **Microservice Analyse** (Port 8082) - Génération des recommandations
- **Config Server** (Port 8888) - Configuration centralisée
- **Eureka Server** (Port 8761) - Découverte de services
- **Kafka** - Communication asynchrone entre services

## Technologies

**Backend:** Spring Boot 3.2, Spring Cloud, Kafka, H2 Database

**Frontend:** Angular 17, TypeScript

**DevOps:** Docker, Docker Compose, Kubernetes

## Structure du projet

```
backend/
├── config-server/              # Configuration centralisée
├── eureka-server/              # Service discovery
├── api-gateway/                # Point d'entrée API
├── microservice-collecte/      # Gestion capteurs et observations
└── microservice-analyse/       # Analyse et recommandations

frontend/                       # Application Angular
config-repo/                    # Fichiers de configuration
kubernetes/                     # Manifestes K8s
```

## Installation

### Prérequis
- Docker Desktop installé
- Au moins 8 GB de RAM disponibles

### Démarrage rapide

1. **Initialiser la configuration**

```powershell
cd config-repo
git init
git add .
git commit -m "Initial config"
cd ..
```

2. **Builder et lancer l'application**

Windows:
```powershell
.\build-all.ps1
docker-compose up -d
```

Linux/Mac:
```bash
chmod +x build-all.sh
./build-all.sh
docker-compose up -d
```

3. **Attendre le démarrage** (2-3 minutes)

4. **Accéder à l'application**
- Application: http://localhost:4200
- Eureka Dashboard: http://localhost:8761
- API Gateway: http://localhost:8080

## Utilisation

1. Créer des capteurs (humidité, température, pluviométrie)
2. Ajouter des observations
3. Générer des recommandations d'irrigation pour les parcelles

## Documentation API

- Microservice Collecte: http://localhost:8081/swagger-ui.html
- Microservice Analyse: http://localhost:8082/swagger-ui.html

## Licence

Projet académique
