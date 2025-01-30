package com.example.catalogservice.messageQueue;

import com.example.catalogservice.entity.CatalogEntity;
import com.example.catalogservice.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "example-catalog-topic")
    public void updateQty(String kafkaMessage) {
        log.info("Kafka Message: -> {}", kafkaMessage);

        try {
            Map<Object, Object> map = objectMapper.readValue(kafkaMessage, new TypeReference<>() {
            });
            CatalogEntity entity = catalogRepository.findByProductId((String) map.get("productId"));

            if (entity != null) {
                entity.setStock(entity.getStock() - (Integer) map.get("qty"));
//            catalogRepository.save(entity);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
