package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.messageQueue.KafkaProducer;
import com.example.orderservice.messageQueue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;

    private final OrderService orderService;

    private final ModelMapper modelMapper;

    private final KafkaProducer kafkaProducer;

    private final OrderProducer orderProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @Validated @RequestBody RequestOrder orderDetails) {

        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);

        orderDto.setUserId(userId);

        /* jpa */
//        OrderDto createOrder = orderService.createOrder(orderDto);
//        ResponseOrder responseOrder = modelMapper.map(createOrder, ResponseOrder.class);

        /* kafka */
        orderDto.setOrderId((UUID.randomUUID().toString()));
        orderDto.setTotalPrice(orderDetails.getQty() * orderDetails.getUnitPrice());

        kafkaProducer.send("example-catalog-topic", orderDto);
        orderProducer.send("orders", orderDto);

        ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);

        return new ResponseEntity<>(responseOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable("userId") String userId) {
        List<OrderEntity> ordersByUserId = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> list = ordersByUserId.stream()
                .map(v -> modelMapper.map(v, ResponseOrder.class))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
