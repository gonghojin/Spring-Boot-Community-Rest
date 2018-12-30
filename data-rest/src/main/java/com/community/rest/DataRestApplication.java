package com.community.rest;

import com.community.rest.event.BoardEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class DataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRestApplication.class, args);
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin(CorsConfiguration.ALL); // CorsConfiguration.ALL == *
            configuration.addAllowedHeader(CorsConfiguration.ALL);
            configuration.addAllowedMethod(CorsConfiguration.ALL);

            UrlBasedCorsConfigurationSource source =
                    new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);

            http.httpBasic()
                    .and()
                    .authorizeRequests().anyRequest().permitAll()
                    .and()
                    .cors().configurationSource(source)
                    .and()
                    .csrf().disable();
        }

        @Bean
        InMemoryUserDetailsManager userDetailsManager() {
            /**
             * 스프링 부트 2.0(시큐리티 5.0이상)부터는 암호화 인코딩 방식을 지정해야함.
             * 예를들어, sha256방식은 {sha256}과 같이 지정해야 함.
             * 예제에서는 인코딩 방식을 지정하지 않기 위해 {noop}로 표기
             */
            User.UserBuilder commonUser = User.withUsername("commonUser")
                    .password("{noop}common").roles("USER");
            User.UserBuilder gongdel = User.withUsername("gongdel")
                    .password("{noop}test").roles("USER", "ADMIN");

            List<UserDetails> userDetailsList = new ArrayList<>();
            userDetailsList.add(commonUser.build());
            userDetailsList.add(gongdel.build());

            return new InMemoryUserDetailsManager(userDetailsList);
        }

        @Bean
        BoardEventHandler boardEventHandler() {
            return new BoardEventHandler();
        }
    }
}
