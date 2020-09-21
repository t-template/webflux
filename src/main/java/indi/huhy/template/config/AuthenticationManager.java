package indi.huhy.template.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/5/11 11:08
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String identification = JWTUtil.getIdentification(authToken);
        if (StringUtils.isBlank(identification)) {
            return Mono.empty();
        }

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                // TODO 角色
                authToken, authToken, Collections.singletonList(new SimpleGrantedAuthority("admin"))
        );
        return Mono.just(auth);
    }
}
