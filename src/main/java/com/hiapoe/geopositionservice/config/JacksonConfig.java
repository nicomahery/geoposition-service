package com.hiapoe.geopositionservice.config;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public JtsModule jstModule() {
        return new JtsModule();
    }
}
