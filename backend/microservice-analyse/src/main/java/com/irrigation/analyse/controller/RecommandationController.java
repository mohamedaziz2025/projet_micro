package com.irrigation.analyse.controller;

import com.irrigation.analyse.model.Recommandation;
import com.irrigation.analyse.service.RecommandationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommandations")
@RequiredArgsConstructor
@Tag(name = "Recommandations", description = "API de gestion des recommandations d'irrigation")
public class RecommandationController {
    
    private final RecommandationService recommandationService;
    
    @GetMapping
    @Operation(summary = "Récupérer toutes les recommandations")
    public ResponseEntity<List<Recommandation>> getAllRecommandations() {
        return ResponseEntity.ok(recommandationService.getAllRecommandations());
    }
    
    @GetMapping("/parcelle/{parcelleId}")
    @Operation(summary = "Récupérer les recommandations pour une parcelle")
    public ResponseEntity<List<Recommandation>> getRecommandationsByParcelleId(@PathVariable Long parcelleId) {
        return ResponseEntity.ok(recommandationService.getRecommandationsByParcelleId(parcelleId));
    }
    
    @GetMapping("/parcelle/{parcelleId}/derniere")
    @Operation(summary = "Récupérer la dernière recommandation pour une parcelle")
    public ResponseEntity<Recommandation> getLastRecommandationByParcelleId(@PathVariable Long parcelleId) {
        Recommandation recommandation = recommandationService.getLastRecommandationByParcelleId(parcelleId);
        if (recommandation != null) {
            return ResponseEntity.ok(recommandation);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/parcelle/{parcelleId}/generer")
    @Operation(summary = "Générer une nouvelle recommandation pour une parcelle")
    public ResponseEntity<Recommandation> genererRecommandation(@PathVariable Long parcelleId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recommandationService.genererRecommandationPourParcelle(parcelleId));
    }
}
