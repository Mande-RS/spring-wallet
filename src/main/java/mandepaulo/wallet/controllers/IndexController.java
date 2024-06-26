package mandepaulo.wallet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(HttpSession session, Model model) {
        String Auth = (String) session.getAttribute("Auth");
        model.addAttribute("Auth", Auth);
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

}
