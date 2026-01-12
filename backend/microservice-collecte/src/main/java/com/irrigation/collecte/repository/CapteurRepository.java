package com.irrigation.collecte.repository;

import com.irrigation.collecte.model.Capteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapteurRepository extends JpaRepository<Capteur, Long> {
    List<Capteur> findByParcelleId(Long parcelleId);
    List<Capteur> findByType(String type);
    List<Capteur> findByActifTrue();
}
