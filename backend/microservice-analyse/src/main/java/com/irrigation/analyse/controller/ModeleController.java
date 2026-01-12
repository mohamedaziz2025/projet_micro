package com.irrigation.analyse.controller;

import com.irrigation.analyse.model.Modele;
import com.irrigation.analyse.service.ModeleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modeles")
@RequiredArgsConstructor
@Tag(name = "Modèles", description = "API de gestion des modèles d'analyse")
public class ModeleController {
    
    private final ModeleService modeleService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les modèles")
    public ResponseEntity<List<Modele>> getAllModeles() {
        return ResponseEntity.ok(modeleService.getAllModeles());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un modèle par son ID")
    public ResponseEntity<Modele> getModeleById(@PathVariable Long id) {
        return modeleService.getModeleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/actifs")
    @Operation(summary = "Récupérer tous les modèles actifs")
    public ResponseEntity<List<Modele>> getModelesActifs() {
        return ResponseEntity.ok(modeleService.getModelesActifs());
    }
    
    @GetMapping("/actif-recent")
    @Operation(summary = "Récupérer le modèle actif le plus récent")
    public ResponseEntity<Modele> getModeleActifRecent() {
        return modeleService.getModeleActifRecent()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau modèle")
    public ResponseEntity<Modele> createModele(@Valid @RequestBody Modele modele) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modeleService.createModele(modele));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un modèle")
    public ResponseEntity<Modele> updateModele(@PathVariable Long id, 
                                                @Valid @RequestBody Modele modele) {
        return ResponseEntity.ok(modeleService.updateModele(id, modele));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un modèle")
    public ResponseEntity<Void> deleteModele(@PathVariable Long id) {
        modeleService.deleteModele(id);
        return ResponseEntity.noContent().build();
    }
}
