package com.example.oauth.rest;

import com.example.oauth.fingerprint.IpGeolocationClient;
import com.example.oauth.model.IpGeolocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final IpGeolocationClient ipGeolocationClient;

    @GetMapping("ip")
    public IpGeolocationResponse test(HttpServletRequest request) {
        return ofNullable(extractIp(request))
                .map(ipGeolocationClient::findById)
                .orElse(null);
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
