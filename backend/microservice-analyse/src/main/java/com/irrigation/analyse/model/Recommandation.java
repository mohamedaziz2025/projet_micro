package com.irrigation.analyse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant une recommandation d'irrigation
 */
@Entity
@Table(name = "recommandations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommandation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "L'identifiant de la parcelle est obligatoire")
    private Long parcelleId;
    
    @NotNull(message = "La quantité d'eau est obligatoire")
    private Double quantiteEau; // en mm ou litres/m²
    
    @NotBlank(message = "La justification est obligatoire")
    @Column(length = 1000)
    private String justification;
    
    private LocalDateTime dateCalcul;
    
    private Long modeleId; // Référence au modèle utilisé
    
    @Enumerated(EnumType.STRING)
    private NiveauUrgence urgence;
    
    private Double confianceScore; // Score de confiance (0-1)
    
    @PrePersist
    protected void onCreate() {
        if (dateCalcul == null) {
            dateCalcul = LocalDateTime.now();
        }
    }
    
    public enum NiveauUrgence {
        FAIBLE, MOYEN, ELEVE, CRITIQUE
    }
}
