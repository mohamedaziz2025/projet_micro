package com.irrigation.analyse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO pour recevoir les observations depuis Kafka ou Feign
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationDto {
    private Long id;
    private Long capteurId;
    private Double valeur;
    private String unite;
    private LocalDateTime date;
}
