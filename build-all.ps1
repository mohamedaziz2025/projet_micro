# Script de build complet pour tous les microservices
# Exécuter depuis la racine du projet

Write-Host "========================================" -ForegroundColor Green
Write-Host "Build de tous les microservices" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green

# Config Server
Write-Host "`nBuild Config Server..." -ForegroundColor Yellow
Set-Location backend/config-server
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { Write-Host "Erreur lors du build de Config Server" -ForegroundColor Red; exit 1 }
Set-Location ../..

# Eureka Server
Write-Host "`nBuild Eureka Server..." -ForegroundColor Yellow
Set-Location backend/eureka-server
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { Write-Host "Erreur lors du build de Eureka Server" -ForegroundColor Red; exit 1 }
Set-Location ../..

# API Gateway
Write-Host "`nBuild API Gateway..." -ForegroundColor Yellow
Set-Location backend/api-gateway
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { Write-Host "Erreur lors du build de API Gateway" -ForegroundColor Red; exit 1 }
Set-Location ../..

# Microservice Collecte
Write-Host "`nBuild Microservice Collecte..." -ForegroundColor Yellow
Set-Location backend/microservice-collecte
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { Write-Host "Erreur lors du build de Microservice Collecte" -ForegroundColor Red; exit 1 }
Set-Location ../..

# Microservice Analyse
Write-Host "`nBuild Microservice Analyse..." -ForegroundColor Yellow
Set-Location backend/microservice-analyse
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { Write-Host "Erreur lors du build de Microservice Analyse" -ForegroundColor Red; exit 1 }
Set-Location ../..

Write-Host "`n========================================" -ForegroundColor Green
Write-Host "Build terminé avec succès!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "`nVous pouvez maintenant exécuter:" -ForegroundColor Cyan
Write-Host "  docker-compose up --build" -ForegroundColor White
