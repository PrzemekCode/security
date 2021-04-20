package com.example.oauth.rest;

import com.example.oauth.fingerprint.IpGeolocationClient;
import com.example.oauth.model.IpGeolocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;

import static com.example.oauth.utils.RequestUtils.extractIp;
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

    @GetMapping("userAgentClient")
    public Client userAgentClient(HttpServletRequest request) {
        return new Parser().parse(request.getHeader("user-agent"));
    }

    @GetMapping("userAgent")
    public String userAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }
}
