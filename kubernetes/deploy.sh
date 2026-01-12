# Script de déploiement Kubernetes
# Exécuter dans l'ordre

# 1. Créer le namespace (optionnel)
# kubectl create namespace irrigation

# 2. Appliquer la ConfigMap
kubectl apply -f configmap.yaml

# 3. Déployer Config Server (doit être prêt avant les autres)
kubectl apply -f config-server-deployment.yaml
echo "Attendre que Config Server soit prêt..."
kubectl wait --for=condition=ready pod -l app=config-server --timeout=120s

# 4. Déployer Eureka Server
kubectl apply -f eureka-server-deployment.yaml
echo "Attendre que Eureka Server soit prêt..."
kubectl wait --for=condition=ready pod -l app=eureka-server --timeout=120s

# 5. Déployer les microservices métiers
kubectl apply -f microservice-collecte-deployment.yaml
kubectl apply -f microservice-analyse-deployment.yaml

# 6. Déployer API Gateway
kubectl apply -f api-gateway-deployment.yaml

# 7. Déployer Frontend
kubectl apply -f frontend-deployment.yaml

# Vérifier le statut
echo "Vérification du statut des pods..."
kubectl get pods
kubectl get services

echo "Déploiement terminé!"
