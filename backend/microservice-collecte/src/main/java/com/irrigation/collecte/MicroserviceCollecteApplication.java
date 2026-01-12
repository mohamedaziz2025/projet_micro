package com.irrigation.collecte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Microservice de collecte des données IoT
 * Responsable de la gestion des capteurs et des observations
 * Publie les observations sur Kafka
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class MicroserviceCollecteApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCollecteApplication.class, args);
    }
}
