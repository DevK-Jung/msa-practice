package com.example.orderservice.messageQueue;

import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final List<Field> fields = Arrays.asList(
            new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("int32", true, "product_id"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price")
    );

    private final Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();

    public OrderDto send(String topic, OrderDto orderDto) {
        Payload payload = Payload.fromOrderDto(orderDto);

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);

        try {
            String jsonInString = objectMapper.writeValueAsString(kafkaOrderDto);

            kafkaTemplate.send(topic, jsonInString);

            log.info("Order Producer sent data from the Order Service: {}", kafkaOrderDto);

            return orderDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
