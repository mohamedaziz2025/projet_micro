# Guide de Démarrage Rapide

## 🚀 Démarrage en 5 minutes avec Docker Compose

### Étape 1 : Prérequis
- Docker Desktop installé et démarré
- Au moins 8 GB de RAM disponibles

### Étape 2 : Initialiser le repository Git de configuration

```powershell
cd config-repo
git init
git add .
git commit -m "Initial configuration"
cd ..
```

### Étape 3 : Builder tous les microservices

**Windows (PowerShell) :**
```powershell
.\build-all.ps1
```

**Linux/Mac :**
```bash
chmod +x build-all.sh
./build-all.sh
```

### Étape 4 : Lancer l'application

```bash
docker-compose up --build
```

⏳ **Attendre 2-3 minutes** que tous les services démarrent.

### Étape 5 : Accéder à l'application

- **Frontend** : http://localhost:4200
- **Eureka Dashboard** : http://localhost:8761
- **API Gateway** : http://localhost:8080

## 📊 Scénario de Test Complet

### 1. Accéder au Dashboard
Ouvrez http://localhost:4200 dans votre navigateur.

### 2. Créer un Modèle d'Analyse
Utilisez Postman ou curl :

```bash
curl -X POST http://localhost:8080/api/analyse/modeles \
  -H "Content-Type: application/json" \
  -d '{
    "type": "REGLE_SIMPLE",
    "version": "1.0.0",
    "precision": 85.5,
    "actif": true,
    "description": "Modèle basé sur des règles métier"
  }'
```

### 3. Créer des Capteurs via l'Interface
1. Cliquez sur "Capteurs"
2. Cliquez sur "Nouveau Capteur"
3. Créez 3 capteurs pour la Parcelle 1 :
   - Type : HUMIDITE, Parcelle : 1, Localisation : "Zone Nord"
   - Type : TEMPERATURE, Parcelle : 1, Localisation : "Zone Nord"
   - Type : PLUVIOMETRIE, Parcelle : 1, Localisation : "Zone Nord"

### 4. Ajouter des Observations
1. Cliquez sur "Observations"
2. Cliquez sur "Nouvelle Observation"
3. Ajoutez les observations suivantes :
   - Capteur Humidité : Valeur = 35, Unité = %
   - Capteur Température : Valeur = 28, Unité = °C
   - Capteur Pluviométrie : Valeur = 2, Unité = mm

### 5. Générer une Recommandation
1. Cliquez sur "Recommandations"
2. Entrez "1" dans Parcelle ID
3. Cliquez sur "Générer"
4. ✅ Vous devriez voir une recommandation avec :
   - Quantité d'eau recommandée
   - Niveau d'urgence
   - Justification détaillée

## 🔍 Vérification des Services

### Vérifier tous les services dans Eureka
```
http://localhost:8761
```

Vous devriez voir :
- ✅ MICROSERVICE-COLLECTE
- ✅ MICROSERVICE-ANALYSE
- ✅ API-GATEWAY

### Tester les API directement

**Lister les capteurs :**
```bash
curl http://localhost:8080/api/collecte/capteurs
```

**Lister les observations :**
```bash
curl http://localhost:8080/api/collecte/observations
```

**Lister les recommandations :**
```bash
curl http://localhost:8080/api/analyse/recommandations
```

## 📝 Documentation API (Swagger)

- **Microservice Collecte** : http://localhost:8081/swagger-ui.html
- **Microservice Analyse** : http://localhost:8082/swagger-ui.html

## 🐛 Résolution des Problèmes

### Les services ne démarrent pas
```bash
# Vérifier les logs
docker-compose logs -f

# Redémarrer un service spécifique
docker-compose restart config-server
```

### Erreur "Connection refused"
Attendez que tous les services soient complètement démarrés (2-3 minutes).

### Port déjà utilisé
```bash
# Vérifier les ports utilisés
netstat -an | findstr "8080 8761 8888"

# Arrêter tous les conteneurs
docker-compose down
```

### Kafka ne démarre pas
```bash
# Supprimer les volumes et redémarrer
docker-compose down -v
docker-compose up --build
```

## 🛑 Arrêter l'Application

```bash
# Arrêter tous les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v
```

## 📊 Scénarios de Test Avancés

### Scénario 1 : Humidité Critique
Créez une observation avec humidité < 30% :
```json
{
  "capteurId": 1,
  "valeur": 25,
  "unite": "%",
  "date": "2026-01-03T10:00:00"
}
```
➡️ Recommandation : Irrigation CRITIQUE avec 15mm d'eau

### Scénario 2 : Température Élevée
Créez une observation avec température > 30°C :
```json
{
  "capteurId": 2,
  "valeur": 35,
  "unite": "°C",
  "date": "2026-01-03T10:00:00"
}
```
➡️ Augmentation de la quantité d'eau recommandée

### Scénario 3 : Pluie Récente
Créez une observation avec pluviométrie > 10mm :
```json
{
  "capteurId": 3,
  "valeur": 15,
  "unite": "mm",
  "date": "2026-01-03T10:00:00"
}
```
➡️ Réduction de l'irrigation recommandée

## 🎓 Pour la Soutenance

### Points clés à présenter :

1. **Architecture** : Expliquer le diagramme d'architecture (README.md)
2. **Microservices** : 5 microservices (Config, Eureka, Gateway, Collecte, Analyse)
3. **Communication** : Synchrone (REST/Feign) + Asynchrone (Kafka)
4. **Logique IA** : Algorithme de décision basé sur règles (extensible ML)
5. **DevOps** : Docker, Docker Compose, Kubernetes
6. **Front-end** : Angular moderne avec architecture modulaire
7. **Demo live** : Suivre le scénario de test complet

### Questions attendues et réponses :

**Q: Pourquoi utiliser Kafka ?**
R: Communication asynchrone pour découpler les services, résilience, scalabilité

**Q: Comment gérer la haute disponibilité ?**
R: Replicas dans Kubernetes, Load balancing via Eureka

**Q: Comment ajouter un nouveau modèle ML ?**
R: Créer une nouvelle implémentation dans DecisionEngine, architecture extensible

**Q: Sécurité ?**
R: Points d'extension : Spring Security, JWT, OAuth2

Bonne chance ! 🚀
