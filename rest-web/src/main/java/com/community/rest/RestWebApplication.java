package com.community.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableJpaAuditing // JPA Aduiting annotation 활성화
@SpringBootApplication
public class RestWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestWebApplication.class, args);
    }

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize와 @PostAuthorize 사용하기 위함
    @EnableWebSecurity
    static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin(CorsConfiguration.ALL); // CorsConfiguration == *
            configuration.addAllowedMethod(CorsConfiguration.ALL);
            configuration.addAllowedHeader(CorsConfiguration.ALL);

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
    }
}
