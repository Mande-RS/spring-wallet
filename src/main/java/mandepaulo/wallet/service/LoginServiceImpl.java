package mandepaulo.wallet.service;

import java.util.Optional;

import javax.security.auth.login.LoginException;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import mandepaulo.wallet.models.CurrentUserSession;
import mandepaulo.wallet.models.Customer;
import mandepaulo.wallet.models.UserLogin;
import mandepaulo.wallet.repository.CurrentSessionRepository;
import mandepaulo.wallet.repository.CustomerRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CurrentSessionRepository currentSessionRepo;

    /*-------------------------------------------   Login   --------------------------------------------------*/

    @Override
    public String login(UserLogin userLogin, HttpSession session) throws LoginException {

        List<Customer> customer = customerRepo.findByCustomerMobile(userLogin.getPhoneNumber());

        Customer existingCustomer = customer.get(0);

        if (existingCustomer == null) {
            throw new LoginException("Número de telefone inválido!");
        }

        Optional<CurrentUserSession> optional = currentSessionRepo.findByUserId(existingCustomer.getCustomerId());

        if (optional.isPresent()) {

            throw new LoginException("O utilizador já existe no sistema.");

        }

        if (existingCustomer.getPassword().equals(userLogin.getPassword())) {

            String key = RandomString.make(8);

            CurrentUserSession currentUserSession = new CurrentUserSession(existingCustomer.getCustomerId(), key,
                    LocalDateTime.now());

            currentSessionRepo.save(currentUserSession);
            session.setAttribute("Auth", key);
            return currentUserSession.toString();
        }

        throw new LoginException("Senha incorreta");

    }

    /*-------------------------------------   Logout   ----------------------------------------*/

    @Override
    public String logout(String key) throws LoginException {

        CurrentUserSession currentUserSession = currentSessionRepo.findByUuid(key);

        if (currentUserSession == null) {
            throw new LoginException("Id exclusivo de  inválido (chave de sessão).");

        }

        currentSessionRepo.delete(currentUserSession);

        return "Desconectado com sucesso!";
    }
}
