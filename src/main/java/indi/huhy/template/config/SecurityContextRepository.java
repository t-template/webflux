package indi.huhy.template.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/5/11 11:29
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authHeaderStr = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        HttpCookie authCookie = request.getCookies().getFirst(HttpHeaders.AUTHORIZATION);
        String authCookieStr = Objects.isNull(authCookie) ? null : authCookie.getValue();

        String token = StringUtils.isNotBlank(authHeaderStr) ? authHeaderStr : authCookieStr;
        if (StringUtils.isBlank(token)) {
            return Mono.empty();
        }
        if (token.startsWith("Bearer ")) {
            String authToken = token.substring(7);
            if (JWTUtil.verify(authToken)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
                return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
            }
        }
        return Mono.empty();
    }

}
