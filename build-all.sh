#!/bin/bash

# Script de build complet pour tous les microservices
# Exécuter depuis la racine du projet

echo "========================================"
echo "Build de tous les microservices"
echo "========================================"

# Config Server
echo -e "\nBuild Config Server..."
cd backend/config-server
mvn clean package -DskipTests
if [ $? -ne 0 ]; then echo "Erreur lors du build de Config Server"; exit 1; fi
cd ../..

# Eureka Server
echo -e "\nBuild Eureka Server..."
cd backend/eureka-server
mvn clean package -DskipTests
if [ $? -ne 0 ]; then echo "Erreur lors du build de Eureka Server"; exit 1; fi
cd ../..

# API Gateway
echo -e "\nBuild API Gateway..."
cd backend/api-gateway
mvn clean package -DskipTests
if [ $? -ne 0 ]; then echo "Erreur lors du build de API Gateway"; exit 1; fi
cd ../..

# Microservice Collecte
echo -e "\nBuild Microservice Collecte..."
cd backend/microservice-collecte
mvn clean package -DskipTests
if [ $? -ne 0 ]; then echo "Erreur lors du build de Microservice Collecte"; exit 1; fi
cd ../..

# Microservice Analyse
echo -e "\nBuild Microservice Analyse..."
cd backend/microservice-analyse
mvn clean package -DskipTests
if [ $? -ne 0 ]; then echo "Erreur lors du build de Microservice Analyse"; exit 1; fi
cd ../..

echo -e "\n========================================"
echo "Build terminé avec succès!"
echo "========================================"
echo -e "\nVous pouvez maintenant exécuter:"
echo "  docker-compose up --build"
