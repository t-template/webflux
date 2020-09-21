package indi.huhy.template.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/5/14 10:43
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<String> handlerException(Exception e) {
        log.error("系统异常！", e);
        return Mono.just("系统异常！" + e.toString());
    }
}
