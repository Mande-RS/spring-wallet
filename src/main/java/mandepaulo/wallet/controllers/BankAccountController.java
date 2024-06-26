package mandepaulo.wallet.controllers;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.models.BankAccount;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.models.dto.BankAccountDTO;
import mandepaulo.wallet.service.BankAccountService;

@RestController
@Controller
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @RequestMapping("/contas-bancarias")
    public String index() {
        return "BankAccounts/index";
    }

    /*---------------- API ---------------------------------- */

    /*---------------------------------------   Add Bank Account Mapping -------------------------------------------*/
    @PostMapping("/contas-bancarias-add")
    public ResponseEntity<String> addAccountMapping(@RequestParam String key,
            @Valid @RequestBody BankAccountDTO bankAccountDTO) throws BankAccountException, CustomerException {

        bankAccountService.addBankAccount(key, bankAccountDTO);

        return new ResponseEntity<String>("Conta bancária adicionada com sucesso", HttpStatus.CREATED);

    }

    /*---------------------------------------   Delete Bank Account Mapping -------------------------------------------*/
    @DeleteMapping("/contas-bancarias-delete")
    public ResponseEntity<Wallet> removeAccountMapping(@RequestParam String key,
            @Valid @RequestBody BankAccountDTO bankAccount) throws BankAccountException, CustomerException {

        return new ResponseEntity<>(bankAccountService.removeBankAccount(key, bankAccount), HttpStatus.OK);
    }

    /*---------------------------------------   View Bank Account Mapping -------------------------------------------*/
    @GetMapping("/contas-bancarias-details")
    public ResponseEntity<Optional<BankAccount>> getBankAccountDetailsMapping(@RequestParam String key,
            @RequestParam Integer accountNo) throws BankAccountException, CustomerException {

        return new ResponseEntity<Optional<BankAccount>>(bankAccountService.viewBankAccount(key, accountNo),
                HttpStatus.OK);

    }

    /*---------------------------------------   View All Bank Account Mapping -------------------------------------------*/
    @GetMapping("/contas-bancarias-all")
    public ResponseEntity<List<BankAccount>> getAllBankAccountMapping(@RequestParam String key)
            throws BankAccountException, CustomerException {

        return new ResponseEntity<List<BankAccount>>(bankAccountService.viewAllBankAccounts(key), HttpStatus.FOUND);

    }
}
