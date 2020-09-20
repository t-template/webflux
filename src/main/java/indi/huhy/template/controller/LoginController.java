package indi.huhy.template.controller;

import indi.huhy.template.config.JWTUtil;
import indi.huhy.template.entity.LoginToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huhy
 * @since 2019-09-08
 */
@RestController
public class LoginController {

    @PostMapping(value = "/doLogin")
    public void doLogin(@RequestBody LoginToken loginToken) {
        String token = JWTUtil.sign(loginToken.getUsername());
    }
}
