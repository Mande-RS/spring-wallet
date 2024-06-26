package mandepaulo.wallet.service;

import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Customer;
import mandepaulo.wallet.models.Wallet;

import java.math.BigDecimal;

public interface WalletService {

        public Customer createCustomerAccount(Customer customer) throws CustomerException;

        public BigDecimal showBalance(String mobile, String key) throws CustomerException;

        public String fundTransfer(String targetMobileNumber, String name, BigDecimal amount, String key)
                        throws WalletException, TransactionException, CustomerException;

        public String depositAmount(BigDecimal amount, Integer accountNo, String key)
                        throws BankAccountException, WalletException, CustomerException, TransactionException;

        public Customer updateAccount(Customer customer, String key) throws CustomerException;

        public String addMoney(Wallet wallet, Integer accountNo, BigDecimal amount, String key)
                        throws WalletException, BankAccountException, CustomerException, TransactionException;

}