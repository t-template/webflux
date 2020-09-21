package indi.huhy.template.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/5/11 11:06
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                        Mono.defer(() -> Mono.just(swe.getResponse())).flatMap(response -> {
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            // TODO 身份校验
                            DataBuffer buffer = response.bufferFactory().wrap("校验失败".getBytes());
                            return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
                        })).accessDeniedHandler((swe, e) ->
                        Mono.defer(() -> Mono.just(swe.getResponse())).flatMap(response -> {
                            response.setStatusCode(HttpStatus.FORBIDDEN);
                            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            // TODO 权限校验
                            DataBuffer buffer = response.bufferFactory().wrap("没有权限".getBytes());
                            return response.writeWith(Mono.just(buffer)).doOnError(error -> DataBufferUtils.release(buffer));
                        })).and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers("/doLogin").permitAll() // 登录
                .anyExchange().authenticated()
                .and().build();
    }

}
