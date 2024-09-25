package dev.mightyelephants.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Bean
    public OAuth2TokenValidator<Jwt> tokenValidator() {

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator("676832423324-ho9rcav2ddnapfo2olvl6pm509ubpcc4.apps.googleusercontent.com");

        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(issuerUri);

        return new DelegatingOAuth2TokenValidator<>(audienceValidator, issuerValidator);
    }
    
}
