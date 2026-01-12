package com.irrigation.collecte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entité représentant une observation d'un capteur
 */
@Entity
@Table(name = "observations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Observation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "L'identifiant du capteur est obligatoire")
    private Long capteurId;
    
    @NotNull(message = "La valeur est obligatoire")
    private Double valeur;
    
    @NotBlank(message = "L'unité est obligatoire")
    private String unite; // °C, %, mm, etc.
    
    @NotNull(message = "La date est obligatoire")
    private LocalDateTime date;
    
    @PrePersist
    protected void onCreate() {
        if (date == null) {
            date = LocalDateTime.now();
        }
    }
}
