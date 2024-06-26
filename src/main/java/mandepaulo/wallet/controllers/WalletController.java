package mandepaulo.wallet.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Customer;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.repository.WalletRepository;
import mandepaulo.wallet.service.WalletService;

@Controller
public class WalletController {
    @Autowired
    private WalletRepository customerRepository;

    @RequestMapping(value = "/carteiras", method = RequestMethod.GET)
    public String index(Model model) {
        List<Wallet> clientes = (List<Wallet>) customerRepository.findAll();
        model.addAttribute("clientes", clientes);

        return "Wallets/index";
    }

    @Autowired
    public WalletService walletService;

    /*--------------------------------------------   Create Account  ------------------------------------------------*/
    @PostMapping("/createaccount")
    public ResponseEntity<Customer> createAccount(@Valid @RequestBody Customer customer) throws CustomerException {

        return new ResponseEntity<Customer>(walletService.createCustomerAccount(customer), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.POST)

    public String createWallet(Customer customer) throws CustomerException {
        Customer savedCustomer = walletService.createCustomerAccount(customer);

        return "redirect:/clientes";
    }

    /*--------------------------------------------  view Balance  ------------------------------------------------*/
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> showBalance(@RequestParam String key, @RequestParam String mobileNumber)
            throws CustomerException {

        return new ResponseEntity<BigDecimal>(walletService.showBalance(mobileNumber, key), HttpStatus.ACCEPTED);
    }

    /*--------------------------------------------   Update Account  ------------------------------------------------*/
    @PostMapping("/updateaccount")
    public ResponseEntity<Customer> updateCustomerDetails(@Valid @RequestBody Customer customer,
            @RequestParam String key) throws CustomerException {

        return new ResponseEntity<Customer>(walletService.updateAccount(customer, key), HttpStatus.ACCEPTED);
    }

    /*--------------------------------------------   Deposit Money to Wallet  ------------------------------------------------*/
    @PostMapping("/deposit/wallet")
    public ResponseEntity<String> depositToWallet(@RequestParam Integer accountNo, @RequestParam BigDecimal amount,
            @RequestParam String key)
            throws BankAccountException, CustomerException, TransactionException, WalletException {

        return new ResponseEntity<String>(walletService.depositAmount(amount, accountNo, key), HttpStatus.OK);
    }

    /*--------------------------------------------   Fund Transfer  ------------------------------------------------*/
    @PostMapping("/transfer/mobile")
    public ResponseEntity<String> fundTransfer(@RequestParam String mobile, @RequestParam String name,
            @RequestParam BigDecimal amount, @RequestParam String key)
            throws WalletException, CustomerException, TransactionException {

        return new ResponseEntity<String>(walletService.fundTransfer(name, mobile, amount, key), HttpStatus.OK);
    }
}
