package springapi.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{

            auth.inMemoryAuthentication()
                    .withUser("debin")
                    .password("{noop}1234") //인코딩을 없이 사용하기 위해 옵션 부여 {noop}
                    .roles("USER");

    }
}
//yml이 아니라 이 클래스를 사용해 설정
