package com.amazon.amazon_api_gateway.filter;


import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public class CorrelationPropagationFilterFunctions {

    public static HandlerFilterFunction<ServerResponse, ServerResponse> correlationPropagation()
    {
        return ((request, next) ->
        {
            Object CorrelationIdAtribute = request
                                            .servletRequest()
                                            .getAttribute(
                                             CorrelationIdFilter.CORRELATION_ID);
            if(CorrelationIdAtribute == null)
            {
                return next.handle(request);
            }

            String CorrelationId = CorrelationIdAtribute.toString();

            ServerRequest modifiedRequest = ServerRequest.from(request)
                    .header(CorrelationIdFilter.CORRELATION_ID,CorrelationId).build();

            return next.handle(modifiedRequest);
        });
    }
}
