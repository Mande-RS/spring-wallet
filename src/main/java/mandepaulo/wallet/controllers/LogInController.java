package mandepaulo.wallet.controllers;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import mandepaulo.wallet.models.UserLogin;
import mandepaulo.wallet.service.LoginService;

@Controller
/// @RestController
@RequestMapping("/user")
public class LogInController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> loginMapping(@RequestBody UserLogin userLogin,
            HttpSession session) throws LoginException {

        String output = loginService.login(userLogin, session);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signinMapping(UserLogin userLogin, HttpSession session) throws LoginException {

        String output = loginService.login(userLogin, session);

        return "redirect:/home";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutMapping(@RequestParam String key, HttpSession session) throws LoginException {

        session.invalidate();
        String output = loginService.logout(key);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }
}
