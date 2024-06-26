package mandepaulo.wallet.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mandepaulo.wallet.exceptions.BillPaymentException;
import mandepaulo.wallet.models.BillPayment;
import mandepaulo.wallet.repository.BillPaymentRepository;

@Service
public class BillPaymentServiceImpl implements BillPaymentService {

    /*
     * @Autowired
     * private WalletService walletService;
     */
    @Autowired
    private BillPaymentRepository billPaymentRepo;

    /*--------------------------------------------   Add Bill Payment -------------------------------------------------*/
    @Override
    public String addBillPayment(String targetMobile, String Name, double amount, String billType,
            LocalDate paymentDate, Integer walletId, String key) throws BillPaymentException {

        // BigDecimal value = BigDecimal.valueOf(amount);

        // String str = walletService.fundTransfer(targetMobile, Name, value, key);

        BillPayment billPayment = new BillPayment(amount, billType, LocalDate.now());

        billPaymentRepo.save(billPayment);

        return "";
    }
}
