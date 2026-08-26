package com.amazon.amazon_api_gateway.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
@Order(1)
public class GlobalLoggingFilter extends OncePerRequestFilter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain)
        throws IOException, ServletException {
    long startTime = System.currentTimeMillis();
    String method=request.getMethod();
    String uri=request.getRequestURI();

    log.info("Global Filter - incoming request method :{} ,URI:{}",method,uri);

    try{
        filterChain.doFilter(request, response);
    }finally{
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info(
                "Global filter - Completed Request -> Method: {}, URI: {}, Status: {}, Duration: {} ms",
                method,
                uri,
                response.getStatus(),
                executionTime
        );
    }
}
}
