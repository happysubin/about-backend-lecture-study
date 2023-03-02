package hello;

import hello.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Import;

//@Import(MyDataSourceValueConfig.class)
@Import(MyDataSourceConfigV3.class)
@SpringBootApplication(scanBasePackages = "hello.datasource")
//@ConfigurationPropertiesScan({ "hello.datasource" }) //컴포넌트 스캔 자동 등록?
public class ExternalReadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalReadApplication.class, args);
    }
}
