package mandepaulo.wallet.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.models.BankAccount;
import mandepaulo.wallet.models.CurrentUserSession;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.models.dto.BankAccountDTO;
import mandepaulo.wallet.repository.BankAccountRepository;
import mandepaulo.wallet.repository.CurrentSessionRepository;
import mandepaulo.wallet.repository.WalletRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private CurrentSessionRepository currentSessionRepo;

    @Autowired
    private WalletRepository walletRepo;

    /*---------------------------------------   Add Customer Bank Account  -------------------------------------------*/
    @Override
    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO)
            throws BankAccountException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNumber());
        if (optional.isEmpty()) {

            Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

            BankAccount createBankAccount = new BankAccount(bankAccountDTO.getAccountNumber(), bankAccountDTO.getIban(),
                    bankAccountDTO.getBankName(), bankAccountDTO.getBalance());
            createBankAccount.setWallet(wallet);

            bankAccountRepo.save(createBankAccount);

            return wallet;
        }
        throw new BankAccountException(
                "Já existe uma conta bancária com determinado número de conta... Tente com outra conta");

    }

    /*---------------------------------------   Remove Customer Bank Account  ----------------------------------------*/
    @Override
    public Wallet removeBankAccount(String key, BankAccountDTO bankAccountDTO)
            throws BankAccountException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<BankAccount> optional = bankAccountRepo.findById(bankAccountDTO.getAccountNumber());
        if (optional.isPresent()) {

            bankAccountRepo.delete(optional.get());
            Wallet wallet = optional.get().getWallet();

            return wallet;

        }
        throw new BankAccountException("No Bank Account exist");

    }

    /*---------------------------------------   View Customer Bank Account  ------------------------------------------*/
    @Override
    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo)
            throws BankAccountException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<BankAccount> bankAccount = bankAccountRepo.findById(accountNo);
        if (bankAccount == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccount;
    }

    /*-------------------------------------   View Customers All Bank Account  ---------------------------------------*/
    @Override
    public List<BankAccount> viewAllBankAccounts(String key) throws BankAccountException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        List<BankAccount> bankAccounts = bankAccountRepo
                .findAllByWallet(walletRepo.showCustomerWalletDetails(currUserSession.getUserId()).getWalletId());
        if (bankAccounts == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccounts;
    }

}
