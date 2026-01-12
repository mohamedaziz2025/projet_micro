package com.irrigation.collecte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un capteur IoT
 */
@Entity
@Table(name = "capteurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capteur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le type de capteur est obligatoire")
    private String type; // TEMPERATURE, HUMIDITE, PLUVIOMETRIE, etc.
    
    @NotNull(message = "L'identifiant de la parcelle est obligatoire")
    private Long parcelleId;
    
    @NotBlank(message = "La localisation est obligatoire")
    private String localisation;
    
    private Boolean actif = true;
}
