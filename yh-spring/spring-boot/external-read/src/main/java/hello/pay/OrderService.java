package hello.pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PayClient payClient;

    public void order(int money) {
        payClient.pay(money);
    }
}
