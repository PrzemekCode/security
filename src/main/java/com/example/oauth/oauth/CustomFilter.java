package com.example.oauth.oauth;

import com.example.oauth.fingerprint.IpGeolocationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {

    private final IpGeolocationClient ipGeolocationClient;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(httpServletRequest.getHeader(AUTHORIZATION));
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpServletRequest) {
            @Override
            public String getHeader(String name) {
                if(AUTHORIZATION.equals(name)) {
                    return "Basic Y2xpZW50SWQ6c2VjcmV0";
                }
                return super.getHeader(name);
            }
        };
        System.out.println(ipGeolocationClient.findById("89.64.111.235"));
        String deviceDetails = getDeviceDetails(wrapper.getHeader("user-agent"));
        System.out.println(deviceDetails);
        System.out.println(extractIp(wrapper));
        filterChain.doFilter(wrapper, httpServletResponse);
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

    /*private String getIpLocation(String ip) throws UnknownHostException {
        //String location = UNKNOWN;
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse cityResponse = databaseReader
                .city(ipAddress);

        if (Objects.nonNull(cityResponse) &&
                Objects.nonNull(cityResponse.getCity()) &&
                !Strings.isNullOrEmpty(cityResponse.getCity().getName())) {
            location = cityResponse.getCity().getName();
        }
        System.out.println(ipAddress);
        return null;
    }*/

    private String getDeviceDetails(String userAgent) {
        String deviceDetails = null;
        Client client = new Parser().parse(userAgent);
        if (Objects.nonNull(client)) {
            deviceDetails = client.userAgent.family
                    + " " + client.userAgent.major + "."
                    + client.userAgent.minor + " - "
                    + client.os.family + " " + client.os.major
                    + "." + client.os.minor;
        }
        return deviceDetails;
    }
}
