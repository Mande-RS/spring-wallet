package mandepaulo.wallet.service;

import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.TransactionException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.*;
import mandepaulo.wallet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CurrentSessionRepository currentSessionRepo;

    @Autowired
    private BeneficiaryRepository beneficiaryRepo;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountRepository bankAccountRepo;

    /*-----------------------------------------   Create Wallet Account  ---------------------------------------------*/
    @Override
    public Customer createCustomerAccount(Customer customer) throws CustomerException {

        List<Customer> customers = customerRepo.findByCustomerMobile(customer.getPhoneNumber());

        if (customers.isEmpty()) {

            Wallet wallet = new Wallet();
            wallet.setBalance(BigDecimal.valueOf(0));

            wallet.setCustomer(customer);
            walletRepo.save(wallet);

            return customerRepo.save(customer);
        }
        throw new CustomerException("Número de telefone duplicado [já registado com cliente diferente]");

    }

    /*------------------------------------------   Show Wallet Balance  ----------------------------------------------*/
    @Override
    public BigDecimal showBalance(String mobile, String key) throws CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Integer userId = currUserSession.getUserId();
        Wallet wallet = walletRepo.showCustomerWalletDetails(userId);

        return wallet.getBalance();

    }

    /*---------------------------------------------   Fund Transfer  -------------------------------------------------*/
    @Override
    public String fundTransfer(String name, String targetMobileNumber, BigDecimal amount, String key)
            throws WalletException, CustomerException, TransactionException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Integer userId = currUserSession.getUserId();
        Wallet wallet = walletRepo.showCustomerWalletDetails(userId);

        Beneficiary beneficiary = new Beneficiary(targetMobileNumber, name);

        List<Beneficiary> beneficiaries = beneficiaryRepo.findByWallet(wallet.getWalletId());

        if (!beneficiaries.contains(beneficiary))
            beneficiaryRepo.save(beneficiary);

        List<Customer> customers = customerRepo.findByCustomerMobile(targetMobileNumber);

        if (customers.isEmpty()) {
            throw new CustomerException("Cliente com número de telefone " + targetMobileNumber + " não existe");
        }

        Wallet targetWallet = walletRepo.showCustomerWalletDetails(customers.get(0).getCustomerId());

        if (wallet.getBalance().compareTo(amount) < 0)
            throw new WalletException("Adicione mais quantia na carteira para transação");

        targetWallet.setBalance(targetWallet.getBalance().add(amount));
        walletRepo.save(targetWallet);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepo.save(wallet);

        Transaction transaction = new Transaction("Transferência bancária", LocalDate.now(), amount.doubleValue(),
                amount + " transferido para " + targetMobileNumber);
        transaction.setWallet(wallet);

        transactionService.addTransaction(transaction);

        return "Fundo transferido com sucesso";
    }

    /*---------------------------------------------   Deposit Amount  ------------------------------------------------*/
    @Override
    public String depositAmount(BigDecimal amount, Integer accountNo, String key)
            throws BankAccountException, CustomerException, WalletException, TransactionException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Integer userId = currUserSession.getUserId();
        Wallet wallet = walletRepo.showCustomerWalletDetails(userId);

        List<BankAccount> accounts = bankAccountRepo.findAllByWallet(wallet.getWalletId());

        if (accounts.isEmpty()) {
            throw new BankAccountException("Adicionar conta bancária para transação");
        }

        BankAccount bankAccount = null;

        for (BankAccount b : accounts) {
            if ((b.getAccountNumber().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if (bankAccount == null) {
            throw new BankAccountException("O número da conta bancária não corresponde aos dados das contas salvas");
        }

        if (bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Saldo insuficiente na conta");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepo.save(bankAccount);

        double value = amount.doubleValue();
        Transaction transaction = new Transaction("Transferência bancária", LocalDate.now(), value,
                "transferido do banco " + bankAccount.getBankName() + " para carteira");
        transaction.setWallet(wallet);

        transactionService.addTransaction(transaction);

        return "Sua conta bancária nº " + accountNo + " debitou  " + amount + " Kzs";

    }

    /*---------------------------------------------   Update Account  ------------------------------------------------*/
    @Override
    public Customer updateAccount(Customer customer, String key) throws CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<Customer> customer1 = customerRepo.findById(currUserSession.getUserId());

        if (!customer1.isPresent()) {
            throw new CustomerException("O cliente informado não existe");
        }

        customer.setCustomerId(currUserSession.getUserId());

        return customerRepo.save(customer);
    }

    /*------------------------------------------   Add Money To Wallet  ----------------------------------------------*/
    @Override
    public String addMoney(Wallet wallet, Integer accountNo, BigDecimal amount, String key)
            throws WalletException, BankAccountException, CustomerException, TransactionException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Integer userId = currUserSession.getUserId();
        wallet = walletRepo.showCustomerWalletDetails(userId);

        List<BankAccount> accounts = bankAccountRepo.findAllByWallet(wallet.getWalletId());

        if (accounts.isEmpty()) {
            throw new BankAccountException("Adicione uma conta bancária para transação");
        }

        BankAccount bankAccount = null;

        for (BankAccount b : accounts) {
            if ((b.getAccountNumber().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if (bankAccount == null) {
            throw new BankAccountException("O número da conta bancária não corresponde ao nosso servidor");
        }

        if (bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Saldo insuficiente na conta");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepo.save(bankAccount);

        double value = amount.doubleValue();
        Transaction transaction = new Transaction("Transferência bancária", LocalDate.now(), value,
                "transferido do banco " + bankAccount.getBankName() + " para carteira");
        transaction.setWallet(wallet);

        transactionService.addTransaction(transaction);

        return "Sua conta bancária não " + accountNo + " debitado por " + amount + " Kzs";

    }

}
