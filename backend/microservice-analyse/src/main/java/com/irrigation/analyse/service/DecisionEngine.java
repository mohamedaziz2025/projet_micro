package com.irrigation.analyse.service;

import com.irrigation.analyse.dto.ObservationDto;
import com.irrigation.analyse.model.Modele;
import com.irrigation.analyse.model.Recommandation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service de logique d'aide à la décision
 * Contient les algorithmes de calcul des recommandations
 */
@Service
@Slf4j
public class DecisionEngine {
    
    /**
     * Génère une recommandation basée sur des règles simples
     * Architecture extensible pour intégrer des modèles ML plus tard
     */
    public Recommandation genererRecommandation(Long parcelleId, 
                                                 List<ObservationDto> observations, 
                                                 Modele modele) {
        log.info("Génération de recommandation pour la parcelle {}", parcelleId);
        
        Recommandation recommandation = new Recommandation();
        recommandation.setParcelleId(parcelleId);
        recommandation.setModeleId(modele != null ? modele.getId() : null);
        
        // Regrouper les observations par type de capteur
        Map<String, List<ObservationDto>> observationsParType = observations.stream()
                .collect(Collectors.groupingBy(obs -> inferTypeFromValue(obs)));
        
        // Analyser les données
        double humidite = calculerMoyenne(observationsParType.get("HUMIDITE"));
        double temperature = calculerMoyenne(observationsParType.get("TEMPERATURE"));
        double pluviometrie = calculerMoyenne(observationsParType.get("PLUVIOMETRIE"));
        
        // Logique de décision basée sur des règles
        double quantiteEau = 0.0;
        StringBuilder justification = new StringBuilder();
        Recommandation.NiveauUrgence urgence;
        double confiance;
        
        // Règle 1: Humidité du sol
        if (humidite < 30) {
            quantiteEau += 15.0;
            justification.append("Humidité très faible (").append(String.format("%.1f", humidite))
                    .append("%), irrigation importante nécessaire. ");
            urgence = Recommandation.NiveauUrgence.CRITIQUE;
            confiance = 0.95;
        } else if (humidite < 50) {
            quantiteEau += 10.0;
            justification.append("Humidité faible (").append(String.format("%.1f", humidite))
                    .append("%), irrigation modérée recommandée. ");
            urgence = Recommandation.NiveauUrgence.ELEVE;
            confiance = 0.85;
        } else if (humidite < 70) {
            quantiteEau += 5.0;
            justification.append("Humidité correcte (").append(String.format("%.1f", humidite))
                    .append("%), irrigation légère suffisante. ");
            urgence = Recommandation.NiveauUrgence.MOYEN;
            confiance = 0.75;
        } else {
            quantiteEau = 0.0;
            justification.append("Humidité optimale (").append(String.format("%.1f", humidite))
                    .append("%), aucune irrigation nécessaire. ");
            urgence = Recommandation.NiveauUrgence.FAIBLE;
            confiance = 0.90;
        }
        
        // Règle 2: Température
        if (temperature > 30) {
            quantiteEau += 3.0;
            justification.append("Température élevée (").append(String.format("%.1f", temperature))
                    .append("°C), ajout d'eau pour compenser l'évaporation. ");
        } else if (temperature > 25) {
            quantiteEau += 1.5;
            justification.append("Température modérée (").append(String.format("%.1f", temperature))
                    .append("°C). ");
        }
        
        // Règle 3: Pluviométrie récente
        if (pluviometrie > 10) {
            quantiteEau = Math.max(0, quantiteEau - 5.0);
            justification.append("Pluie récente détectée (").append(String.format("%.1f", pluviometrie))
                    .append("mm), réduction de l'irrigation. ");
        }
        
        recommandation.setQuantiteEau(Math.round(quantiteEau * 100.0) / 100.0);
        recommandation.setJustification(justification.toString());
        recommandation.setUrgence(urgence);
        recommandation.setConfianceScore(confiance);
        
        log.info("Recommandation générée: {} mm d'eau, urgence: {}", 
                recommandation.getQuantiteEau(), urgence);
        
        return recommandation;
    }
    
    private String inferTypeFromValue(ObservationDto obs) {
        // Logique simplifiée pour déterminer le type basé sur l'unité
        if (obs.getUnite().equals("%")) {
            return "HUMIDITE";
        } else if (obs.getUnite().equals("°C") || obs.getUnite().equals("C")) {
            return "TEMPERATURE";
        } else if (obs.getUnite().equals("mm")) {
            return "PLUVIOMETRIE";
        }
        return "AUTRE";
    }
    
    private double calculerMoyenne(List<ObservationDto> observations) {
        if (observations == null || observations.isEmpty()) {
            return 0.0;
        }
        return observations.stream()
                .mapToDouble(ObservationDto::getValeur)
                .average()
                .orElse(0.0);
    }
}
