package mandepaulo.wallet.service;

import javax.security.auth.login.LoginException;

import jakarta.servlet.http.HttpSession;
import mandepaulo.wallet.models.UserLogin;

public interface LoginService {
    public String login(UserLogin userLogin, HttpSession session) throws LoginException;

    public String logout(String Key) throws LoginException;

}
