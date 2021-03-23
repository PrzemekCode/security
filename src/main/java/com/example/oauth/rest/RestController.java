package com.example.oauth.rest;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("ip")
    public String test(HttpServletRequest request) {
        return extractIp(request);
    }

    private String extractIp(HttpServletRequest request) {
        String clientIp;
        String clientXForwardedForIp = request
                .getHeader("x-forwarded-for");
        if (nonNull(clientXForwardedForIp)) {
            clientIp = clientXForwardedForIp;
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
}
