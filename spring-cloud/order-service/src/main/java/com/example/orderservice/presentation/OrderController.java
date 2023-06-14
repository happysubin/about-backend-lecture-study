package com.example.orderservice.presentation;

import com.example.orderservice.application.OrderDto;
import com.example.orderservice.application.OrderService;
import com.example.orderservice.application.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrderDto> createOrder(@PathVariable String userId, @RequestBody RequestOrder request) {
        log.info("Before add orders data");

        OrderDto orderDto = request.toServiceDto(userId);
        /* jpa */
        ResponseOrderDto res = orderService.createOrder(orderDto);
        log.info("After added orders data");
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrderDto>> getOrder(@PathVariable("userId") String userId) throws Exception {
        log.info("Before retrieve orders data");

        List<ResponseOrderDto> result = orderService.getOrdersByUserId(userId);

//        try {
//            Thread.sleep(1000);
//            throw new Exception("장애 발생");
//        } catch(InterruptedException ex) {
//            log.warn(ex.getMessage());
//        }

        log.info("Add retrieved orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
