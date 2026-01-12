package com.irrigation.collecte.controller;

import com.irrigation.collecte.model.Observation;
import com.irrigation.collecte.service.ObservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/observations")
@RequiredArgsConstructor
@Tag(name = "Observations", description = "API de gestion des observations des capteurs")
public class ObservationController {
    
    private final ObservationService observationService;
    
    @GetMapping
    @Operation(summary = "Récupérer toutes les observations")
    public ResponseEntity<List<Observation>> getAllObservations() {
        return ResponseEntity.ok(observationService.getAllObservations());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une observation par son ID")
    public ResponseEntity<Observation> getObservationById(@PathVariable Long id) {
        return observationService.getObservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/capteur/{capteurId}")
    @Operation(summary = "Récupérer les observations d'un capteur")
    public ResponseEntity<List<Observation>> getObservationsByCapteurId(@PathVariable Long capteurId) {
        return ResponseEntity.ok(observationService.getObservationsByCapteurId(capteurId));
    }
    
    @GetMapping("/parcelle/{parcelleId}")
    @Operation(summary = "Récupérer les observations d'une parcelle")
    public ResponseEntity<List<Observation>> getObservationsByParcelleId(@PathVariable Long parcelleId) {
        return ResponseEntity.ok(observationService.getObservationsByParcelleId(parcelleId));
    }
    
    @GetMapping("/parcelle/{parcelleId}/recentes")
    @Operation(summary = "Récupérer les observations récentes d'une parcelle")
    public ResponseEntity<List<Observation>> getRecentObservationsByParcelleId(
            @PathVariable Long parcelleId,
            @RequestParam(defaultValue = "24") int heures) {
        return ResponseEntity.ok(observationService.getRecentObservationsByParcelleId(parcelleId, heures));
    }
    
    @GetMapping("/periode")
    @Operation(summary = "Récupérer les observations sur une période")
    public ResponseEntity<List<Observation>> getObservationsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(observationService.getObservationsBetween(debut, fin));
    }
    
    @PostMapping
    @Operation(summary = "Créer une nouvelle observation")
    public ResponseEntity<Observation> createObservation(@Valid @RequestBody Observation observation) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(observationService.createObservation(observation));
    }
}
