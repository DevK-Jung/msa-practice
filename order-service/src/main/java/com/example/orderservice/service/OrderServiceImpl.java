package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper mapper;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId((UUID.randomUUID().toString()));
        orderDto.setTotalPrice(orderDto.getQty() * orderDto.getUnitPrice());

        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);

        orderRepository.save(orderEntity);

        return mapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId)
                .orElseThrow();

        return mapper.map(orderEntity, OrderDto.class);
    }

    @Override
    public List<OrderEntity> getOrdersByUserId(String userId) {
        log.info("before getOrdersByUserId");
        return orderRepository.findByUserId(userId);
    }
}
