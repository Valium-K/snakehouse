package dev.valium.snakehouse.infra.spring;

import dev.valium.snakehouse.infra.security.CustomBCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public static CustomBCryptPasswordEncoder passwordEncoder() {
        return new CustomBCryptPasswordEncoder();
    }
}
