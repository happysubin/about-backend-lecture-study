package hello.proxy;

import hello.proxy.config.v1_proxy.ConcreteProxyConfig;
import hello.proxy.config.v1_proxy.InterfaceProxyConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import hello.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import({AppV1Config.class, AppV2Config.class})
//@Import(InterfaceProxyConfig.class)
@Import(DynamicProxyFilterConfig.class)
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

	@Bean
	public LogTrace logTrace(){
		return new ThreadLocalLogTrace();
	}
}

/**
 *
 *
 * 요구사항
	 원본 코드를 전혀 수정하지 않고, 로그 추적기를 적용해라. 특정 메서드는 로그를 출력하지 않는 기능  보안상 일부는 로그를 출력하면 안된다.
	 다음과 같은 다양한 케이스에 적용할 수 있어야 한다.
	 v1 - 인터페이스가 있는 구현 클래스에 적용
	 v2 - 인터페이스가 없는 구체 클래스에 적용
	 v3 - 컴포넌트 스캔 대상에 기능 적용
 */