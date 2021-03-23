package com.example.oauth.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TokenEndPoint {

    private final TokenEndpoint tokenEndpoint;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationManager clientAuthenticationManager;

    @PostMapping("test")
    public void token(@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("clientId");
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken("clientId", "secret");
        result.setAuthenticated(true);
        //clientAuthenticationManager.authenticate(result);
        tokenEndpoint.postAccessToken(result, parameters);
    }
}
