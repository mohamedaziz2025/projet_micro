package com.irrigation.collecte.repository;

import com.irrigation.collecte.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findByCapteurId(Long capteurId);
    List<Observation> findByDateBetween(LocalDateTime debut, LocalDateTime fin);
    
    @Query("SELECT o FROM Observation o WHERE o.capteurId IN " +
           "(SELECT c.id FROM Capteur c WHERE c.parcelleId = :parcelleId) " +
           "ORDER BY o.date DESC")
    List<Observation> findByParcelleId(Long parcelleId);
    
    @Query("SELECT o FROM Observation o WHERE o.capteurId IN " +
           "(SELECT c.id FROM Capteur c WHERE c.parcelleId = :parcelleId) " +
           "AND o.date >= :depuis ORDER BY o.date DESC")
    List<Observation> findRecentByParcelleId(Long parcelleId, LocalDateTime depuis);
}
