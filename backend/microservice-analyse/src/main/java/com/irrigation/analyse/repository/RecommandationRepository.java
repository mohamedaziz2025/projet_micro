package com.irrigation.analyse.repository;

import com.irrigation.analyse.model.Recommandation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecommandationRepository extends JpaRepository<Recommandation, Long> {
    List<Recommandation> findByParcelleId(Long parcelleId);
    List<Recommandation> findByDateCalculBetween(LocalDateTime debut, LocalDateTime fin);
    Optional<Recommandation> findFirstByParcelleIdOrderByDateCalculDesc(Long parcelleId);
    List<Recommandation> findByUrgence(Recommandation.NiveauUrgence urgence);
}
