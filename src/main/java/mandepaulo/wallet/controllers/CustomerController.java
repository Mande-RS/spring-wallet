package mandepaulo.wallet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mandepaulo.wallet.models.Customer;
import mandepaulo.wallet.repository.CustomerRepository;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * @param model
     * @return
     */
    @GetMapping("/clientes")
    public String index(Model model) {
        List<Customer> clientes = (List<Customer>) customerRepository.findAll();
        model.addAttribute("clientes", clientes);

        return "Customers/index";
    }

    @GetMapping("/api/clientes")
    @ResponseBody
    public List<Customer> getClientes() {
        List<Customer> clientes = (List<Customer>) customerRepository.findAll();
        return clientes;
    }

}
