package mandepaulo.wallet.service;

import java.time.LocalDate;
import java.util.List;

import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Transaction;

public interface TransactionService {

    public Transaction addTransaction(Transaction transaction) throws TransactionException, WalletException;

    public List<Transaction> findByWallet(String key) throws TransactionException, WalletException, CustomerException;

    public Transaction findByTransactionId(String key, Integer transactionId)
            throws TransactionException, CustomerException;

    public List<Transaction> findByTransactionType(String key, String transactionType)
            throws TransactionException, CustomerException;

    public List<Transaction> viewTransactionBetweenDate(String key, LocalDate startDate, LocalDate endDate)
            throws TransactionException, CustomerException;

    public List<Transaction> viewAllTransaction() throws TransactionException;

}
