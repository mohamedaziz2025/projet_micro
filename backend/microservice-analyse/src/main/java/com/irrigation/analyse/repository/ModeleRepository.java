package com.irrigation.analyse.repository;

import com.irrigation.analyse.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Long> {
    List<Modele> findByType(String type);
    List<Modele> findByActifTrue();
    Optional<Modele> findFirstByActifTrueOrderByDateApprentissageDesc();
}
