package mandepaulo.wallet.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Transaction;
import mandepaulo.wallet.models.dto.TransactionDTO;
import mandepaulo.wallet.service.TransactionService;

@RestController
@Controller
@RequestMapping("/transacoes")
public class TransactionController {
    @GetMapping("/")
    public String index() {
        return "Transactions/index";
    }

    @Autowired
    private TransactionService transactionService;

    /*----------------------------------------   Find Transaction - wallet  ------------------------------------------*/
    @PostMapping("/wallet")
    public ResponseEntity<List<TransactionDTO>> viewByWallet(@RequestParam String key)
            throws TransactionException, WalletException, CustomerException {

        List<Transaction> transactions = transactionService.findByWallet(key);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (Transaction t : transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(),
                    t.getTransactionDate(), t.getAmount(), t.getDescription());
            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.OK);
    }

    /*-----------------------------------------   Find Transaction - tId ---------------------------------------------*/
    @GetMapping("/transactionId")
    public ResponseEntity<TransactionDTO> findById(@RequestParam String key, @RequestParam Integer transactionId)
            throws TransactionException, CustomerException {

        Transaction t = transactionService.findByTransactionId(key, transactionId);
        TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(),
                t.getTransactionDate(), t.getAmount(), t.getDescription());

        return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.CREATED);
    }

    /*----------------------------------------   Find Transaction - Type  --------------------------------------------*/
    @GetMapping("/type")
    public ResponseEntity<List<TransactionDTO>> viewAllTransacationByType(@RequestParam String key,
            @RequestParam String type) throws TransactionException, CustomerException {

        List<Transaction> transactions = transactionService.findByTransactionType(key, type);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (Transaction t : transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(),
                    t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }

    /*------------------------------------   View Transaction - Between 2 date ---------------------------------------*/
    @GetMapping("/between")
    public ResponseEntity<List<TransactionDTO>> viewByTwoDate(@RequestParam String key, @RequestParam("one") String one,
            @RequestParam("two") String two) throws TransactionException, CustomerException {

        LocalDate firstDate = LocalDate.parse(one);
        LocalDate secondDate = LocalDate.parse(two);
        List<Transaction> transactions = transactionService.viewTransactionBetweenDate(key, firstDate, secondDate);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (Transaction t : transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(),
                    t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }

    /*-----------------------------------------   View All Transaction  ----------------------------------------------*/
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> viewAllTransactionByAdmin() throws TransactionException {

        List<Transaction> transactions = transactionService.viewAllTransaction();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (Transaction t : transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(),
                    t.getTransactionDate(), t.getAmount(), t.getDescription());

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.OK);
    }
}
