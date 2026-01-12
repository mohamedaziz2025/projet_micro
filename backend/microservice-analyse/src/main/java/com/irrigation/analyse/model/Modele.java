package com.irrigation.analyse.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant un modèle d'analyse
 */
@Entity
@Table(name = "modeles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Modele {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le type de modèle est obligatoire")
    private String type; // REGLE_SIMPLE, RANDOM_FOREST, LSTM, etc.
    
    @NotBlank(message = "La version est obligatoire")
    private String version;
    
    private LocalDateTime dateApprentissage;
    
    private Double precision; // Pourcentage de précision du modèle
    
    private Boolean actif = true;
    
    @Column(length = 1000)
    private String description;
    
    @PrePersist
    protected void onCreate() {
        if (dateApprentissage == null) {
            dateApprentissage = LocalDateTime.now();
        }
    }
}
