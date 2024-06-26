package mandepaulo.wallet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.CurrentUserSession;
import mandepaulo.wallet.models.Transaction;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.repository.CurrentSessionRepository;
import mandepaulo.wallet.repository.TransactionRepository;
import mandepaulo.wallet.repository.WalletRepository;

public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CurrentSessionRepository currentSessionRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    /*--------------------------------------------   Add Transaction  ------------------------------------------------*/
    @Override
    public Transaction addTransaction(Transaction tran) throws TransactionException, WalletException {
        Optional<Wallet> wallet = walletRepository.findById(tran.getWallet().getWalletId());
        if (!wallet.isPresent())
            throw new WalletException("Informe a referencia da carteira.");
        if (transactionRepository.save(tran) != null)
            return tran;
        throw new TransactionException("Os dados são nulos");
    }

    /*----------------------------------------   Find Transaction - wallet  ------------------------------------------*/
    @Override
    public List<Transaction> findByWallet(String key) throws TransactionException, WalletException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currUserSession.getUserId());

        Optional<Wallet> optional = walletRepository.findById(wallet.getWalletId());
        if (!optional.isPresent()) {
            throw new WalletException("ID de carteira inválido");
        }

        List<Transaction> transactions = transactionRepository.findByWallet(wallet.getWalletId());
        if (transactions.isEmpty()) {
            throw new TransactionException("Nenhuma transação para mostrar");
        }
        return transactions;
    }

    /*-----------------------------------------   Find Transaction - tId ---------------------------------------------*/
    @Override
    public Transaction findByTransactionId(String key, Integer transactionId)
            throws TransactionException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (!transaction.isPresent()) {
            throw new TransactionException("ID de transação inválido");
        }
        return transaction.get();

    }

    /*----------------------------------------   Find Transaction - Type  --------------------------------------------*/
    @Override
    public List<Transaction> findByTransactionType(String key, String transactionType)
            throws TransactionException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionType(transactionType);
        if (transactions.isEmpty()) {
            throw new TransactionException("Nenhuma transação para mostrar");
        }
        return transactions;
    }

    /*------------------------------------   View Transaction - Between 2 date ---------------------------------------*/
    @Override
    public List<Transaction> viewTransactionBetweenDate(String key, LocalDate startDate, LocalDate endDate)
            throws TransactionException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        LocalDate localDate = LocalDate.now();
        if (startDate.isAfter(localDate)) {
            throw new TransactionException("Data de início inválida [ Data Futura ]");
        }
        if (endDate.isAfter(localDate)) {
            throw new TransactionException("Data de término inválida [ Data Futura ]");
        }
        if (startDate.isAfter(endDate)) {
            throw new TransactionException("Data de início inválida ");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        if (transactions.isEmpty()) {
            throw new TransactionException("Nenhuma transação para mostrar");
        }
        return transactions;
    }

    /*-----------------------------------------   View All Transaction  ----------------------------------------------*/
    @Override
    public List<Transaction> viewAllTransaction() throws TransactionException {

        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty()) {
            throw new TransactionException("Nenhuma transação para mostrar");
        }
        return transactions;
    }

}
