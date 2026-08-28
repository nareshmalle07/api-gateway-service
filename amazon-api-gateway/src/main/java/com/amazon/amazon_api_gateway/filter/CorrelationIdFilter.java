package com.amazon.amazon_api_gateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.UUID;

@Component
@Order(1)
public class CorrelationIdFilter extends OncePerRequestFilter {

    static final String CORRELATION_ID = "X-Correlation-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {

            correlationId = UUID.randomUUID().toString();
        }

        MDC.put("correlationId", correlationId);

        // Store so other Gateway logic can access it
        request.setAttribute(CORRELATION_ID, correlationId);

//        final String finalCorrelationID=correlationId;

        response.setHeader(CORRELATION_ID, correlationId);

//        HttpServletRequestWrapper wrapperRequest = new HttpServletRequestWrapper(request) {
//            @Override
//            public String getHeader(String name) {
//                if(CORRELATION_ID.equalsIgnoreCase(name)) {
//                    return finalCorrelationID;
//                }
//                return super.getHeader(name);
//            }
//
//            @Override
//            public Enumeration<String> getHeaders(String name)
//            {
//                if(CORRELATION_ID.equalsIgnoreCase(name)) {
//                    return Collections.enumeration(Collections.singletonList(finalCorrelationID));
//                }
//                return super.getHeaders(name);
//            }
//        };
//
        try {
//            filterChain.doFilter(wrapperRequest, response);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
