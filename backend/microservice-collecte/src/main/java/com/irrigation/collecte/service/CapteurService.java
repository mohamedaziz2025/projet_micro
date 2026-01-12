package com.irrigation.collecte.service;

import com.irrigation.collecte.model.Capteur;
import com.irrigation.collecte.repository.CapteurRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CapteurService {
    
    private final CapteurRepository capteurRepository;
    
    public List<Capteur> getAllCapteurs() {
        log.info("Récupération de tous les capteurs");
        return capteurRepository.findAll();
    }
    
    public Optional<Capteur> getCapteurById(Long id) {
        log.info("Récupération du capteur avec l'ID: {}", id);
        return capteurRepository.findById(id);
    }
    
    public List<Capteur> getCapteursByParcelleId(Long parcelleId) {
        log.info("Récupération des capteurs pour la parcelle: {}", parcelleId);
        return capteurRepository.findByParcelleId(parcelleId);
    }
    
    public List<Capteur> getCapteursByType(String type) {
        log.info("Récupération des capteurs de type: {}", type);
        return capteurRepository.findByType(type);
    }
    
    public List<Capteur> getCapteursActifs() {
        log.info("Récupération des capteurs actifs");
        return capteurRepository.findByActifTrue();
    }
    
    @Transactional
    public Capteur createCapteur(Capteur capteur) {
        log.info("Création d'un nouveau capteur: {}", capteur);
        return capteurRepository.save(capteur);
    }
    
    @Transactional
    public Capteur updateCapteur(Long id, Capteur capteurDetails) {
        log.info("Mise à jour du capteur avec l'ID: {}", id);
        Capteur capteur = capteurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Capteur non trouvé avec l'ID: " + id));
        
        capteur.setType(capteurDetails.getType());
        capteur.setParcelleId(capteurDetails.getParcelleId());
        capteur.setLocalisation(capteurDetails.getLocalisation());
        capteur.setActif(capteurDetails.getActif());
        
        return capteurRepository.save(capteur);
    }
    
    @Transactional
    public void deleteCapteur(Long id) {
        log.info("Suppression du capteur avec l'ID: {}", id);
        capteurRepository.deleteById(id);
    }
}
