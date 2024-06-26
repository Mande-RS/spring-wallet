package mandepaulo.wallet.service;

import java.util.Optional;
import java.util.List;

import mandepaulo.wallet.exceptions.BankAccountException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.models.BankAccount;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.models.dto.BankAccountDTO;

public interface BankAccountService {

    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO)
            throws BankAccountException, CustomerException;

    public Wallet removeBankAccount(String key, BankAccountDTO bankAccountDTO)
            throws BankAccountException, CustomerException;

    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo)
            throws BankAccountException, CustomerException;

    public List<BankAccount> viewAllBankAccounts(String key) throws BankAccountException, CustomerException;

}
