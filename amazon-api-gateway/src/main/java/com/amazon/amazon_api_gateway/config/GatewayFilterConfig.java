package com.amazon.amazon_api_gateway.config;

import com.amazon.amazon_api_gateway.filter.CorrelationPropagationFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.SimpleFilterSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GatewayFilterConfig {

    @Bean
    public SimpleFilterSupplier correlationPropagationFilterSupplier() {

        return new SimpleFilterSupplier(
                CorrelationPropagationFilterFunctions.class
        );
    }

}
