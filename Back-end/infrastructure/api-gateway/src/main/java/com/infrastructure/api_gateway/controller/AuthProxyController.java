package com.infrastructure.api_gateway.controller;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthProxyController {

    private final RestTemplate restTemplate;
    private final String authBaseUrl;

    public AuthProxyController(RestTemplate restTemplate,
            @Value("${gateway.auth.base-url}") String authBaseUrl) {
        this.restTemplate = restTemplate;
        this.authBaseUrl = authBaseUrl;
    }

    @RequestMapping("/**")
    public ResponseEntity<byte[]> proxy(HttpServletRequest request, @RequestBody(required = false) byte[] body) {
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        String targetUrl = authBaseUrl + requestUri + (queryString != null ? "?" + queryString : "");

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames != null && headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            if (HttpHeaders.HOST.equalsIgnoreCase(name)) {
                continue;
            }
            Enumeration<String> values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                headers.add(name, values.nextElement());
            }
        }

        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                targetUrl,
                method,
                new org.springframework.http.HttpEntity<>(body, headers),
                byte[].class);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.putAll(response.getHeaders());
            responseHeaders.remove(HttpHeaders.TRANSFER_ENCODING);

            return ResponseEntity.status(response.getStatusCode())
                .headers(responseHeaders)
                .body(response.getBody());
        } catch (HttpStatusCodeException ex) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.putAll(ex.getResponseHeaders());
            responseHeaders.remove(HttpHeaders.TRANSFER_ENCODING);
            return ResponseEntity.status(ex.getStatusCode())
                .headers(responseHeaders)
                .body(ex.getResponseBodyAsByteArray());
        }
    }
}
