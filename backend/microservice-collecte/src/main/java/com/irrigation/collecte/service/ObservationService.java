package com.irrigation.collecte.service;

import com.irrigation.collecte.model.Observation;
import com.irrigation.collecte.repository.ObservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ObservationService {
    
    private final ObservationRepository observationRepository;
    private final KafkaTemplate<String, Observation> kafkaTemplate;
    private static final String TOPIC = "observations-topic";
    
    public List<Observation> getAllObservations() {
        log.info("Récupération de toutes les observations");
        return observationRepository.findAll();
    }
    
    public Optional<Observation> getObservationById(Long id) {
        log.info("Récupération de l'observation avec l'ID: {}", id);
        return observationRepository.findById(id);
    }
    
    public List<Observation> getObservationsByCapteurId(Long capteurId) {
        log.info("Récupération des observations pour le capteur: {}", capteurId);
        return observationRepository.findByCapteurId(capteurId);
    }
    
    public List<Observation> getObservationsByParcelleId(Long parcelleId) {
        log.info("Récupération des observations pour la parcelle: {}", parcelleId);
        return observationRepository.findByParcelleId(parcelleId);
    }
    
    public List<Observation> getRecentObservationsByParcelleId(Long parcelleId, int heures) {
        LocalDateTime depuis = LocalDateTime.now().minusHours(heures);
        log.info("Récupération des observations récentes pour la parcelle {} depuis {}", parcelleId, depuis);
        return observationRepository.findRecentByParcelleId(parcelleId, depuis);
    }
    
    @Transactional
    public Observation createObservation(Observation observation) {
        log.info("Création d'une nouvelle observation: {}", observation);
        Observation savedObservation = observationRepository.save(observation);
        
        // Publication sur Kafka
        try {
            kafkaTemplate.send(TOPIC, savedObservation);
            log.info("Observation publiée sur Kafka: {}", savedObservation.getId());
        } catch (Exception e) {
            log.error("Erreur lors de la publication sur Kafka", e);
        }
        
        return savedObservation;
    }
    
    public List<Observation> getObservationsBetween(LocalDateTime debut, LocalDateTime fin) {
        log.info("Récupération des observations entre {} et {}", debut, fin);
        return observationRepository.findByDateBetween(debut, fin);
    }
}
