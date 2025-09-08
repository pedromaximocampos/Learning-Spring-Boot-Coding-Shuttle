package com.week4.prod_ready_features.prod_ready_features.filters;

import com.week4.prod_ready_features.prod_ready_features.clients.EmployeeClient.Impl.EmployeeClientImpl;
import com.week4.prod_ready_features.prod_ready_features.services.Auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class LogsFilters extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    private Logger log = LoggerFactory.getLogger(LogsFilters.class);

    private final JwtService jwtService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            // Capture start time
            final Date startedAt = new Date();
            // Request details
            final String queryParameters = request.getQueryString() != null ? "?" + request.getQueryString() : "";
            final String contentType = request.getContentType() != null ? request.getQueryString() : "N/A";
            final String userAgent = request.getHeader("User-Agent") != null ? request.getHeader("User-Agent") : "N/A";
            final String accept = request.getHeader("Accept") != null ?  request.getHeader("Accept") : "N/A";
            final Integer contentLength = request.getContentLength() != -1 ? request.getContentLength() : 0;

            // Logging request details
            log.trace("Incoming request: {} {} {}", request.getMethod(), request.getRequestURI(), queryParameters);
            log.trace("Request received at: {}", startedAt);
            log.trace("IP Address: {}", request.getRemoteAddr());
            log.trace("Content-Type: {}" ,  contentType);
            log.trace("User-Agent: {}" ,  userAgent);
            log.trace("Accept: {}" ,  accept);
            log.trace("Content-Length: {} bytes" ,  contentLength);

            // Authentication details
            final String authHeader = request.getHeader("Authorization") == null ? "" : request.getHeader("Authorization");

            log.info("Authorization Header: {}", authHeader);

            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                try{
                    String jwt = authHeader.split("Bearer ")[1];
                    Long userId = jwtService.getUserIdFromToken(jwt);
                    log.info("Authenticated User ID from JWT: {}", userId);
                }catch (Exception e){
                    log.error("Invalid JWT token; {}", e.getMessage());
                }
            } else {
                log.info("No Bearer token found in Authorization header.");
            }

            filterChain.doFilter(request, response);

            final Date finishedAt = new Date();
            final long duration = finishedAt.getTime() - startedAt.getTime();

            // Logging response details
            log.trace("Response Status: {}", response.getStatus());
            log.trace("Response sent at: {}", finishedAt);
            log.trace("Request processing time: {} ms", duration);

        }catch (Exception e){
            log.error("Error in LogsFilter: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
