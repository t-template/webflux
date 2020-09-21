package indi.huhy.template.controller;

import indi.huhy.template.config.JWTUtil;
import indi.huhy.template.entity.LoginToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huhy
 * @version 1.0
 * @date 2020/5/11 13:14
 */

@RestController
public class LoginController {

    @PostMapping(value = "/doLogin")
    public void login(@RequestBody LoginToken loginToken) throws Exception {
        JWTUtil.sign(loginToken.getUsername());
    }
}
