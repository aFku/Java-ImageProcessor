package org.rcbg.afku.ImageAdjusterApp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwk;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Bean(name = "cryptoGeneratorJwtDecoder")
    JwtDecoder jwtDecoder(){
        return new CustomJwtDecoder(jwk, issuer);
    }

    public JwtDecoder getDecoder(){
        return this.jwtDecoder();
    }
}

final class CustomJwtDecoder implements JwtDecoder {

    private final String jwkSetUri;
    private final String issuerUri;
    private final JwtDecoder delegate;

    public CustomJwtDecoder(String jwkSetUri, String issuerUri) {
        this.jwkSetUri = jwkSetUri;
        this.issuerUri = issuerUri;
        this.delegate = createDelegate();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        return delegate.decode(token);
    }

    private JwtDecoder createDelegate() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        return decoder;
    }
}