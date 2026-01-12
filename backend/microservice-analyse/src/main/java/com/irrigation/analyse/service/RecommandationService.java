package com.irrigation.analyse.service;

import com.irrigation.analyse.client.CollecteClient;
import com.irrigation.analyse.dto.ObservationDto;
import com.irrigation.analyse.model.Modele;
import com.irrigation.analyse.model.Recommandation;
import com.irrigation.analyse.repository.ModeleRepository;
import com.irrigation.analyse.repository.RecommandationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommandationService {
    
    private final RecommandationRepository recommandationRepository;
    private final ModeleRepository modeleRepository;
    private final CollecteClient collecteClient;
    private final DecisionEngine decisionEngine;
    
    public List<Recommandation> getAllRecommandations() {
        log.info("Récupération de toutes les recommandations");
        return recommandationRepository.findAll();
    }
    
    public List<Recommandation> getRecommandationsByParcelleId(Long parcelleId) {
        log.info("Récupération des recommandations pour la parcelle: {}", parcelleId);
        return recommandationRepository.findByParcelleId(parcelleId);
    }
    
    public Recommandation getLastRecommandationByParcelleId(Long parcelleId) {
        log.info("Récupération de la dernière recommandation pour la parcelle: {}", parcelleId);
        return recommandationRepository.findFirstByParcelleIdOrderByDateCalculDesc(parcelleId)
                .orElse(null);
    }
    
    @Transactional
    public Recommandation genererRecommandationPourParcelle(Long parcelleId) {
        log.info("Génération d'une nouvelle recommandation pour la parcelle: {}", parcelleId);
        
        // Récupérer les observations récentes via Feign
        List<ObservationDto> observations = collecteClient.getRecentObservationsByParcelleId(parcelleId, 24);
        
        if (observations.isEmpty()) {
            log.warn("Aucune observation trouvée pour la parcelle {}", parcelleId);
            throw new RuntimeException("Aucune observation disponible pour générer une recommandation");
        }
        
        // Récupérer le modèle actif
        Modele modele = modeleRepository.findFirstByActifTrueOrderByDateApprentissageDesc()
                .orElse(null);
        
        // Générer la recommandation
        Recommandation recommandation = decisionEngine.genererRecommandation(parcelleId, observations, modele);
        
        return recommandationRepository.save(recommandation);
    }
    
    /**
     * Consomme les observations depuis Kafka et génère automatiquement des recommandations
     */
    @KafkaListener(topics = "observations-topic", groupId = "analyse-group")
    public void consommerObservation(ObservationDto observation) {
        log.info("Observation reçue via Kafka: {}", observation);
        
        // Pour chaque nouvelle observation, on pourrait déclencher une analyse
        // Ici, on log simplement pour éviter de générer trop de recommandations
        // Dans un système réel, on pourrait agréger les observations et générer périodiquement
        log.info("Observation traitée: capteur={}, valeur={}{}", 
                observation.getCapteurId(), observation.getValeur(), observation.getUnite());
    }
}
