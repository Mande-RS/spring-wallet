package mandepaulo.wallet.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mandepaulo.wallet.exceptions.BillPaymentException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.service.BillPaymentService;

@RestController
@Controller
public class BillPaymentController {
    @RequestMapping("/pagamentos")
    public String index() {
        return "BillPayments/index";
    }

    @Autowired
    private BillPaymentService billPayService;

    /*--------------------------------------------   Add Bill Payment Mapping -------------------------------------------------*/
    @PostMapping("/addBillPayment")
    public ResponseEntity<String> addBillPayment(@RequestParam("targetMobile") String targetMobile,
            @RequestParam("Name") String Name, @RequestParam("amount") double amount,
            @RequestParam("BillType") String BillType, @RequestParam("key") String key)
            throws BillPaymentException, WalletException, CustomerException, TransactionException {

        LocalDate date = LocalDate.now();
        String output = billPayService.addBillPayment(targetMobile, Name, amount, BillType, date, 0, key);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }
}
