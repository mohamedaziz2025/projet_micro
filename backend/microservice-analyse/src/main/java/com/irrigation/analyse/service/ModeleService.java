package com.irrigation.analyse.service;

import com.irrigation.analyse.model.Modele;
import com.irrigation.analyse.repository.ModeleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModeleService {
    
    private final ModeleRepository modeleRepository;
    
    public List<Modele> getAllModeles() {
        log.info("Récupération de tous les modèles");
        return modeleRepository.findAll();
    }
    
    public Optional<Modele> getModeleById(Long id) {
        log.info("Récupération du modèle avec l'ID: {}", id);
        return modeleRepository.findById(id);
    }
    
    public List<Modele> getModelesActifs() {
        log.info("Récupération des modèles actifs");
        return modeleRepository.findByActifTrue();
    }
    
    public Optional<Modele> getModeleActifRecent() {
        log.info("Récupération du modèle actif le plus récent");
        return modeleRepository.findFirstByActifTrueOrderByDateApprentissageDesc();
    }
    
    @Transactional
    public Modele createModele(Modele modele) {
        log.info("Création d'un nouveau modèle: {}", modele);
        return modeleRepository.save(modele);
    }
    
    @Transactional
    public Modele updateModele(Long id, Modele modeleDetails) {
        log.info("Mise à jour du modèle avec l'ID: {}", id);
        Modele modele = modeleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modèle non trouvé avec l'ID: " + id));
        
        modele.setType(modeleDetails.getType());
        modele.setVersion(modeleDetails.getVersion());
        modele.setDateApprentissage(modeleDetails.getDateApprentissage());
        modele.setPrecision(modeleDetails.getPrecision());
        modele.setActif(modeleDetails.getActif());
        modele.setDescription(modeleDetails.getDescription());
        
        return modeleRepository.save(modele);
    }
    
    @Transactional
    public void deleteModele(Long id) {
        log.info("Suppression du modèle avec l'ID: {}", id);
        modeleRepository.deleteById(id);
    }
}
