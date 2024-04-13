package org.rcbg.afku.ImageAdjusterApp.configs;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.security.messaging.access.intercept.ChannelSecurityInterceptor;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("ws-interceptor")
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private String issuer;

    @Autowired
    private JwtDecoder decoder;

    public WebSocketAuthInterceptor(String issuer, JwtDecoder decoder){
        this.decoder = decoder;
        this.issuer = issuer;
    }


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        OAuth2TokenValidator<Jwt> verifier = JwtValidators.createDefaultWithIssuer(this.issuer);
        NativeMessageHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, NativeMessageHeaderAccessor.class);
        String authorizationHeaderContent = accessor.getFirstNativeHeader("Authorization");
        authorizationHeaderContent = authorizationHeaderContent.replace("Bearer", "");
        verifier.validate(decoder.decode(authorizationHeaderContent));
        return message;
    }
}
