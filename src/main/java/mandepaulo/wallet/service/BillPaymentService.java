package mandepaulo.wallet.service;

import java.time.LocalDate;

import mandepaulo.wallet.exceptions.BillPaymentException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;

public interface BillPaymentService {
    public String addBillPayment(String targetMobile, String Name, double amount, String BillType,
            LocalDate paymentDate, Integer walletId, String key)
            throws BillPaymentException, WalletException, CustomerException, TransactionException;

}
