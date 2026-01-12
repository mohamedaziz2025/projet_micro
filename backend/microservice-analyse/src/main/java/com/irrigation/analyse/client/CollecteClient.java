package com.irrigation.analyse.client;

import com.irrigation.analyse.dto.ObservationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Client Feign pour communiquer avec le microservice Collecte
 */
@FeignClient(name = "MICROSERVICE-COLLECTE")
public interface CollecteClient {
    
    @GetMapping("/observations/parcelle/{parcelleId}")
    List<ObservationDto> getObservationsByParcelleId(@PathVariable Long parcelleId);
    
    @GetMapping("/observations/parcelle/{parcelleId}/recentes")
    List<ObservationDto> getRecentObservationsByParcelleId(
            @PathVariable Long parcelleId,
            @RequestParam(defaultValue = "24") int heures);
}
