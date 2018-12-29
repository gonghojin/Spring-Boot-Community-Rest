package com.community.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRestApplication.class, args);
    }
/*
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
    }*/
}
