package indi.huhy.template.controller;

import indi.huhy.template.entity.LoginToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        UsernamePasswordToken token = new UsernamePasswordToken(loginToken.getUsername(), loginToken.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

    @PostMapping(value = "/doLogout")
    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }
}
