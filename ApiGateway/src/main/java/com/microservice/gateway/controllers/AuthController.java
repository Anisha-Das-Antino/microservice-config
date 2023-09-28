package com.microservice.gateway.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.gateway.models.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
            @AuthenticationPrincipal OidcUser user,
            Model model
    ) {

        logger.info("user email id : {} ", user.getEmail());

        //creating auth response object
        AuthResponse authResponse = new AuthResponse();

        //setting email to authresponse
        authResponse.setUserId(user.getEmail());

        //setting token to auth response
        authResponse.setAccessToken(client.getAccessToken().getTokenValue());
        
        // Set the refresh token in the AuthResponse
        authResponse.setRefreshToken(client.getRefreshToken().getTokenValue());

        // Set the expiration time of the access token in the AuthResponse
        authResponse.setExpireAt(client.getAccessToken().getExpiresAt().getEpochSecond());

        // Extract the authorities (permissions) of the user and store them in AuthResponse
        List<String> authorities = user
						            .getAuthorities()
						            .stream()
						            .map(grantedAuthority -> {
						                return grantedAuthority.getAuthority();
						            })
						            .collect(Collectors.toList());

        authResponse.setAuthorities(authorities);

        // Return a ResponseEntity with the AuthResponse and HTTP status OK
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
        
    }

}
