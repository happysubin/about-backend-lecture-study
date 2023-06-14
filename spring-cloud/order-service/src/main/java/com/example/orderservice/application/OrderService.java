package com.example.orderservice.application;

import com.example.orderservice.domain.Order;
import com.example.orderservice.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public ResponseOrderDto createOrder(OrderDto dto){
        Order order = dto.toOrderEntity();
        orderRepository.save(order);
        return ResponseOrderDto.of(order);
    }

    public ResponseOrderDto getOrderByOrderId(String orderId){
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("주문이 존재하지 않습니다."));
        return ResponseOrderDto.of(order);
    }

    public List<ResponseOrderDto> getOrdersByUserId(String userId){
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> ResponseOrderDto.of(order))
                .collect(Collectors.toList());
    }

}
