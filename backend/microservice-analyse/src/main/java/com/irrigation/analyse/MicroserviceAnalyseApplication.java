package com.irrigation.analyse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Microservice d'analyse et de recommandations
 * Consomme les observations depuis Kafka
 * Communique avec le microservice Collecte via Feign
 * Génère des recommandations d'irrigation intelligentes
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableKafka
public class MicroserviceAnalyseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceAnalyseApplication.class, args);
    }
}
