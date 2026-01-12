# Système d'Irrigation Intelligente - Architecture Microservices

## 📋 Description du Projet

Application web complète d'aide à la décision pour l'irrigation optimisée, basée sur une architecture microservices. Le système collecte des données de capteurs IoT, les analyse à l'aide d'une logique intelligente, et génère des recommandations d'irrigation optimisées par parcelle.

## 🏗️ Architecture

### Vue d'ensemble

```
┌─────────────────────────────────────────────────────────────┐
│                        Frontend Angular                      │
│                      (Port 4200 / 80)                        │
└────────────────────────┬────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────┐
│                      API Gateway                             │
│                      (Port 8080)                             │
│              Point d'entrée unique + Routage                 │
└──────────┬──────────────────────────────────┬───────────────┘
           │                                   │
           ▼                                   ▼
┌──────────────────────┐           ┌──────────────────────────┐
│ Microservice Collecte│           │ Microservice Analyse     │
│     (Port 8081)      │           │     (Port 8082)          │
│                      │◄─────────►│                          │
│ - Capteurs IoT       │  Feign    │ - Modèles d'analyse      │
│ - Observations       │  Client   │ - Recommandations        │
│ - Kafka Producer     │           │ - Kafka Consumer         │
└──────────┬───────────┘           └──────────┬───────────────┘
           │                                   │
           │         ┌─────────────┐          │
           └────────►│    Kafka    │◄─────────┘
                     │  (Port 9092)│
                     └─────────────┘

┌─────────────────────────────────────────────────────────────┐
│              Services d'Infrastructure                       │
│                                                              │
│  ┌──────────────────┐      ┌─────────────────────┐         │
│  │  Config Server   │      │   Eureka Server     │         │
│  │   (Port 8888)    │      │    (Port 8761)      │         │
│  │   Configuration  │      │ Service Discovery   │         │
│  │   Centralisée    │      │                     │         │
│  └──────────────────┘      └─────────────────────┘         │
└─────────────────────────────────────────────────────────────┘
```

### Microservices

#### 1. **Config Server** (Port 8888)
- Centralisation des configurations
- Gestion des fichiers de configuration depuis un repository Git local
- Configuration externe pour tous les microservices

#### 2. **Eureka Server** (Port 8761)
- Service Discovery
- Enregistrement automatique des microservices
- Load balancing côté client

#### 3. **API Gateway** (Port 8080)
- Point d'entrée unique pour le système
- Routage dynamique vers les microservices
- Configuration CORS pour le frontend
- Routes :
  - `/api/collecte/**` → Microservice Collecte
  - `/api/analyse/**` → Microservice Analyse

#### 4. **Microservice Collecte** (Port 8081)
Responsabilités :
- Gestion CRUD des capteurs IoT
- Enregistrement des observations
- Publication des observations sur Kafka

Entités :
- `Capteur` : type, parcelleId, localisation, actif
- `Observation` : capteurId, valeur, unite, date

API Endpoints :
- `GET/POST /capteurs` - Gestion des capteurs
- `GET /capteurs/parcelle/{id}` - Capteurs par parcelle
- `POST /observations` - Créer une observation
- `GET /observations/parcelle/{id}` - Observations par parcelle

#### 5. **Microservice Analyse** (Port 8082)
Responsabilités :
- Analyse des données collectées
- Génération de recommandations d'irrigation
- Consommation des observations depuis Kafka
- Communication avec Collecte via Feign Client

Entités :
- `Modele` : type, version, dateApprentissage, precision
- `Recommandation` : parcelleId, quantiteEau, justification, urgence

API Endpoints :
- `GET /recommandations` - Liste des recommandations
- `GET /recommandations/parcelle/{id}` - Recommandations par parcelle
- `POST /recommandations/parcelle/{id}/generer` - Générer une recommandation
- `GET/POST /modeles` - Gestion des modèles

### Communication

#### Synchrone (REST)
- Frontend ↔ API Gateway : HTTP REST
- API Gateway ↔ Microservices : HTTP REST + Load Balancing
- Microservice Analyse → Collecte : Feign Client

#### Asynchrone (Kafka)
- Topic : `observations-topic`
- Producer : Microservice Collecte (publie les nouvelles observations)
- Consumer : Microservice Analyse (consomme et traite les observations)

### Logique d'Aide à la Décision

Le système implémente une logique basée sur des règles métier :

**Règles d'irrigation** :
1. **Humidité du sol** :
   - < 30% : Irrigation critique (15mm)
   - 30-50% : Irrigation élevée (10mm)
   - 50-70% : Irrigation modérée (5mm)
   - > 70% : Aucune irrigation nécessaire

2. **Température** :
   - > 30°C : +3mm (compensation évaporation)
   - > 25°C : +1.5mm

3. **Pluviométrie** :
   - > 10mm récent : -5mm (réduction irrigation)

**Architecture extensible** pour intégrer ultérieurement :
- Random Forest
- LSTM (réseaux de neurones récurrents)
- Autres modèles ML

## 🛠️ Technologies Utilisées

### Back-end
- **Spring Boot 3.2.0** - Framework principal
- **Spring Cloud 2023.0.0** - Microservices
  - Spring Cloud Config
  - Netflix Eureka
  - Spring Cloud Gateway
  - OpenFeign
- **Spring Data JPA** - Persistance
- **H2 Database** - Base de données en mémoire
- **Apache Kafka** - Messaging asynchrone
- **Lombok** - Réduction boilerplate
- **SpringDoc OpenAPI** - Documentation API (Swagger)

### Front-end
- **Angular 17** - Framework frontend
- **TypeScript** - Langage
- **RxJS** - Programmation réactive
- **HttpClient** - Communication HTTP

### DevOps
- **Docker** - Conteneurisation
- **Docker Compose** - Orchestration locale
- **Kubernetes** - Orchestration production
- **Maven** - Build Java
- **npm** - Build Angular

## 📦 Structure du Projet

```
projet_micro/
├── backend/
│   ├── config-server/
│   │   ├── src/main/java/com/irrigation/config/
│   │   ├── src/main/resources/
│   │   ├── Dockerfile
│   │   └── pom.xml
│   ├── eureka-server/
│   │   ├── src/main/java/com/irrigation/eureka/
│   │   ├── src/main/resources/
│   │   ├── Dockerfile
│   │   └── pom.xml
│   ├── api-gateway/
│   │   ├── src/main/java/com/irrigation/gateway/
│   │   ├── src/main/resources/
│   │   ├── Dockerfile
│   │   └── pom.xml
│   ├── microservice-collecte/
│   │   ├── src/main/java/com/irrigation/collecte/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── controller/
│   │   ├── src/main/resources/
│   │   ├── Dockerfile
│   │   └── pom.xml
│   └── microservice-analyse/
│       ├── src/main/java/com/irrigation/analyse/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── service/
│       │   ├── controller/
│       │   ├── client/
│       │   └── dto/
│       ├── src/main/resources/
│       ├── Dockerfile
│       └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/
│   │   │   ├── models/
│   │   │   └── services/
│   │   └── environments/
│   ├── angular.json
│   ├── package.json
│   ├── Dockerfile
│   └── nginx.conf
├── config-repo/
│   ├── eureka-server.yml
│   ├── api-gateway.yml
│   ├── microservice-collecte.yml
│   └── microservice-analyse.yml
├── kubernetes/
│   ├── configmap.yaml
│   ├── config-server-deployment.yaml
│   ├── eureka-server-deployment.yaml
│   ├── api-gateway-deployment.yaml
│   ├── microservice-collecte-deployment.yaml
│   ├── microservice-analyse-deployment.yaml
│   ├── frontend-deployment.yaml
│   └── deploy.sh
├── docker-compose.yml
└── README.md
```

## 🚀 Installation et Exécution

### Prérequis

- **Java 17+**
- **Maven 3.8+**
- **Node.js 18+**
- **Docker & Docker Compose**
- **Kubernetes** (optionnel, pour déploiement K8s)

### Option 1 : Exécution Locale (sans Docker)

#### 1. Compiler les microservices

```bash
# Config Server
cd backend/config-server
mvn clean package

# Eureka Server
cd ../eureka-server
mvn clean package

# API Gateway
cd ../api-gateway
mvn clean package

# Microservice Collecte
cd ../microservice-collecte
mvn clean package

# Microservice Analyse
cd ../microservice-analyse
mvn clean package
```

#### 2. Démarrer Kafka

```bash
# Démarrer Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Démarrer Kafka
bin/kafka-server-start.sh config/server.properties
```

#### 3. Démarrer les microservices (dans l'ordre)

```bash
# 1. Config Server
cd backend/config-server
java -jar target/config-server-1.0.0.jar

# 2. Eureka Server (attendre que Config soit prêt)
cd ../eureka-server
java -jar target/eureka-server-1.0.0.jar

# 3. API Gateway (attendre que Eureka soit prêt)
cd ../api-gateway
java -jar target/api-gateway-1.0.0.jar

# 4. Microservice Collecte
cd ../microservice-collecte
java -jar target/microservice-collecte-1.0.0.jar

# 5. Microservice Analyse
cd ../microservice-analyse
java -jar target/microservice-analyse-1.0.0.jar
```

#### 4. Démarrer le Frontend

```bash
cd frontend
npm install
ng serve
```

### Option 2 : Exécution avec Docker Compose (Recommandé)

#### 1. Compiler tous les microservices

```bash
# Compiler tous les projets Spring Boot
cd backend/config-server && mvn clean package && cd ../..
cd backend/eureka-server && mvn clean package && cd ../..
cd backend/api-gateway && mvn clean package && cd ../..
cd backend/microservice-collecte && mvn clean package && cd ../..
cd backend/microservice-analyse && mvn clean package && cd ../..
```

#### 2. Lancer Docker Compose

```bash
# Depuis la racine du projet
docker-compose up --build
```

Les services seront disponibles :
- **Frontend** : http://localhost:4200
- **API Gateway** : http://localhost:8080
- **Eureka Dashboard** : http://localhost:8761
- **Config Server** : http://localhost:8888

#### 3. Arrêter les services

```bash
docker-compose down
```

### Option 3 : Déploiement Kubernetes

#### 1. Construire les images Docker

```bash
# Construire toutes les images
docker build -t irrigation/config-server:latest backend/config-server
docker build -t irrigation/eureka-server:latest backend/eureka-server
docker build -t irrigation/api-gateway:latest backend/api-gateway
docker build -t irrigation/microservice-collecte:latest backend/microservice-collecte
docker build -t irrigation/microservice-analyse:latest backend/microservice-analyse
docker build -t irrigation/frontend:latest frontend
```

#### 2. Déployer sur Kubernetes

```bash
cd kubernetes

# Appliquer tous les manifests
kubectl apply -f configmap.yaml
kubectl apply -f config-server-deployment.yaml
kubectl apply -f eureka-server-deployment.yaml
kubectl apply -f api-gateway-deployment.yaml
kubectl apply -f microservice-collecte-deployment.yaml
kubectl apply -f microservice-analyse-deployment.yaml
kubectl apply -f frontend-deployment.yaml

# Vérifier le statut
kubectl get pods
kubectl get services
```

Ou utiliser le script de déploiement :

```bash
chmod +x deploy.sh
./deploy.sh
```

## 🧪 Test de l'Application

### 1. Vérifier que tous les services sont enregistrés
- Accéder à Eureka : http://localhost:8761
- Vérifier que `MICROSERVICE-COLLECTE` et `MICROSERVICE-ANALYSE` sont enregistrés

### 2. Tester via l'interface Angular
- Accéder à : http://localhost:4200
- Créer des capteurs
- Ajouter des observations
- Générer des recommandations

### 3. Tester les API directement

#### Créer un capteur
```bash
curl -X POST http://localhost:8080/api/collecte/capteurs \
  -H "Content-Type: application/json" \
  -d '{
    "type": "HUMIDITE",
    "parcelleId": 1,
    "localisation": "Parcelle Nord",
    "actif": true
  }'
```

#### Créer une observation
```bash
curl -X POST http://localhost:8080/api/collecte/observations \
  -H "Content-Type: application/json" \
  -d '{
    "capteurId": 1,
    "valeur": 35.5,
    "unite": "%",
    "date": "2026-01-03T10:00:00"
  }'
```

#### Générer une recommandation
```bash
curl -X POST http://localhost:8080/api/analyse/recommandations/parcelle/1/generer
```

## 📚 Documentation API

Une fois les services démarrés, la documentation Swagger est accessible :

- **Microservice Collecte** : http://localhost:8081/swagger-ui.html
- **Microservice Analyse** : http://localhost:8082/swagger-ui.html

## 🔍 Monitoring et Administration

- **Eureka Dashboard** : http://localhost:8761 - Vue d'ensemble des services
- **Config Server** : http://localhost:8888/eureka-server/default - Configurations
- **H2 Console Collecte** : http://localhost:8081/h2-console
- **H2 Console Analyse** : http://localhost:8082/h2-console

## 🎯 Fonctionnalités Clés

### Frontend Angular
✅ Dashboard avec statistiques
✅ Gestion complète des capteurs (CRUD)
✅ Visualisation des observations
✅ Consultation des recommandations
✅ Génération de recommandations à la demande
✅ Interface responsive et moderne

### Backend
✅ Architecture microservices complète
✅ Configuration centralisée
✅ Service discovery automatique
✅ API Gateway avec routage dynamique
✅ Communication synchrone (REST + Feign)
✅ Communication asynchrone (Kafka)
✅ Logique d'aide à la décision intelligente
✅ Base de données H2 pour chaque microservice
✅ Documentation API (Swagger)
✅ Health checks et actuator

### DevOps
✅ Dockerfiles pour tous les services
✅ Docker Compose pour orchestration locale
✅ Manifests Kubernetes complets
✅ Scripts de déploiement
✅ Configuration CORS
✅ Load balancing

## 🌟 Points d'Extension Futurs

- 🔐 Authentification et autorisation (Spring Security + JWT)
- 📊 Dashboard avancé avec graphiques (Chart.js, D3.js)
- 🤖 Intégration de modèles ML (TensorFlow, Scikit-learn)
- 💾 Migration vers PostgreSQL pour production
- 📧 Notifications par email/SMS
- 🌍 Support multi-langue (i18n)
- 📱 Application mobile (Flutter, React Native)
- 🔄 CI/CD avec Jenkins/GitLab CI
- 📈 Monitoring avec Prometheus + Grafana

## 👥 Auteur

Projet académique - Architecture microservices pour système d'irrigation intelligente

## 📄 Licence

Ce projet est réalisé dans un cadre académique.

---

**Note importante** : Avant le premier démarrage, assurez-vous que tous les services d'infrastructure (Config Server, Eureka, Kafka) sont complètement démarrés avant de lancer les microservices métiers.
