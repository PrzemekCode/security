package com.example.oauth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.nonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    public static final String USER_AGENT_HEADER = "user-agent";

    public static String extractIp(HttpServletRequest request) {
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
