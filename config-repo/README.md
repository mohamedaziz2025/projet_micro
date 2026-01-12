# Configuration Repository

Ce dossier contient les fichiers de configuration pour tous les microservices.
Le Config Server lit ces fichiers pour fournir la configuration centralisée.

## Fichiers de configuration

- `eureka-server.yml` - Configuration pour Eureka Server
- `api-gateway.yml` - Configuration pour API Gateway
- `microservice-collecte.yml` - Configuration pour le microservice Collecte
- `microservice-analyse.yml` - Configuration pour le microservice Analyse

## Initialisation Git

Pour que le Config Server puisse lire ces fichiers, initialisez ce dossier comme repository Git :

```bash
cd config-repo
git init
git add .
git commit -m "Initial configuration"
```

## Note

Le Config Server est configuré pour lire depuis un repository Git local.
En production, vous devriez utiliser un repository Git distant (GitHub, GitLab, etc.).
