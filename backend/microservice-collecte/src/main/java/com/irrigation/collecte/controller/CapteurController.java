package com.irrigation.collecte.controller;

import com.irrigation.collecte.model.Capteur;
import com.irrigation.collecte.service.CapteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capteurs")
@RequiredArgsConstructor
@Tag(name = "Capteurs", description = "API de gestion des capteurs IoT")
public class CapteurController {
    
    private final CapteurService capteurService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les capteurs")
    public ResponseEntity<List<Capteur>> getAllCapteurs() {
        return ResponseEntity.ok(capteurService.getAllCapteurs());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un capteur par son ID")
    public ResponseEntity<Capteur> getCapteurById(@PathVariable Long id) {
        return capteurService.getCapteurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/parcelle/{parcelleId}")
    @Operation(summary = "Récupérer les capteurs d'une parcelle")
    public ResponseEntity<List<Capteur>> getCapteursByParcelleId(@PathVariable Long parcelleId) {
        return ResponseEntity.ok(capteurService.getCapteursByParcelleId(parcelleId));
    }
    
    @GetMapping("/type/{type}")
    @Operation(summary = "Récupérer les capteurs par type")
    public ResponseEntity<List<Capteur>> getCapteursByType(@PathVariable String type) {
        return ResponseEntity.ok(capteurService.getCapteursByType(type));
    }
    
    @GetMapping("/actifs")
    @Operation(summary = "Récupérer tous les capteurs actifs")
    public ResponseEntity<List<Capteur>> getCapteursActifs() {
        return ResponseEntity.ok(capteurService.getCapteursActifs());
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau capteur")
    public ResponseEntity<Capteur> createCapteur(@Valid @RequestBody Capteur capteur) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(capteurService.createCapteur(capteur));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un capteur")
    public ResponseEntity<Capteur> updateCapteur(@PathVariable Long id, 
                                                  @Valid @RequestBody Capteur capteur) {
        return ResponseEntity.ok(capteurService.updateCapteur(id, capteur));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un capteur")
    public ResponseEntity<Void> deleteCapteur(@PathVariable Long id) {
        capteurService.deleteCapteur(id);
        return ResponseEntity.noContent().build();
    }
}
