package tobyspring.myboot.hello;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import tobyspring.myboot.config.MySpringBootApplication;

import javax.annotation.PostConstruct;

//@SpringBootApplication
@MySpringBootApplication
public class MybootApplicationV2 {

	/**
	 * 스프링 부트의 모든 애플리케이션 초기화 작업이 끝나고 실행되는 코드를 만들 때 ApplicationRunner 인터페이스를 구현한 오브젝트 또는 람다식을 빈으로 등록하면 된다.
	 * @param env
	 * @return
	 */
	@Bean
	ApplicationRunner applicationRunner(Environment env){
		return args -> {
			String property = env.getProperty("my.name");
			System.out.println("property = " + property); //env, os, property 파일에 모두 지정을해보고 출력해봄,
		};
	}


	private final JdbcTemplate jdbcTemplate;

	public MybootApplicationV2(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostConstruct
	void init() {
		jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
	}

	public static void main(String[] args) {
		SpringApplication.run(MybootApplicationV2.class, args);
	}

}