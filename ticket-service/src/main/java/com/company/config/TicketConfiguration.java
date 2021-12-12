package com.company.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@ComponentScan("com.company")
public class TicketConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}