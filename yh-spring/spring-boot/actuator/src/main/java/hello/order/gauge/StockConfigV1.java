package hello.order.gauge;

import hello.order.OrderService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
public class StockConfigV1 {

    @Bean
    public MyStockMetric myStockMetric(OrderService orderService, MeterRegistry registry) {

        return new MyStockMetric(orderService, registry);
    }


    @Slf4j
    static class MyStockMetric{

        private OrderService orderService;
        private MeterRegistry meterRegistry;

        public MyStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
            this.orderService = orderService;
            this.meterRegistry = meterRegistry;
        }

        @PostConstruct
        public void init(){
            Gauge.builder("my.stock", orderService, service -> {
                log.info("stock gauge call");
                return service.getStock().get();
            }).register(meterRegistry);
        }
    }
}
