package mandepaulo.wallet.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mandepaulo.wallet.exceptions.BeneficiaryException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Beneficiary;
import mandepaulo.wallet.models.CurrentUserSession;
import mandepaulo.wallet.models.Customer;
import mandepaulo.wallet.models.Wallet;
import mandepaulo.wallet.models.dto.BeneficiaryDTO;
import mandepaulo.wallet.repository.BeneficiaryRepository;
import mandepaulo.wallet.repository.CurrentSessionRepository;
import mandepaulo.wallet.repository.CustomerRepository;
import mandepaulo.wallet.repository.WalletRepository;

@Service
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepo;

    @Autowired
    private CurrentSessionRepository currentSessionRepo;

    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private CustomerRepository customerRepo;

    /*--------------------------------------------   Add Beneficiary -------------------------------------------------*/
    @Override
    public Beneficiary addBeneficiary(Beneficiary beneficiary, String key)
            throws BeneficiaryException, CustomerException, WalletException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Optional<Customer> customer = customerRepo.findById(currUserSession.getUserId());
        Optional<Wallet> wallet = walletRepo
                .findById(walletRepo.showCustomerWalletDetails(currUserSession.getUserId()).getWalletId());

        if (!customer.isPresent()) {
            throw new CustomerException("O beneficiário não está cadastrado no sistema.");
        }

        if (!wallet.isPresent()) {
            throw new WalletException("Utilizador Inválido.");
        }

        Optional<Beneficiary> optional = beneficiaryRepo.findById(beneficiary.getBeneficiaryPhoneNumber());

        if (optional.isEmpty()) {
            return beneficiaryRepo.save(beneficiary);
        }
        throw new BeneficiaryException("Detalhes duplicados [o beneficiário já existe]");

    }

    /*--------------------------------------------   Find Beneficiary -------------------------------------------------*/
    @Override
    public List<Beneficiary> findAllByWallet(Integer walletId) throws BeneficiaryException {

        List<Beneficiary> beneficiaries = beneficiaryRepo.findByWallet(walletId);

        if (beneficiaries.isEmpty()) {
            throw new BeneficiaryException("Beneficiário Inexistente");
        }
        return beneficiaries;

    }

    /*--------------------------------------------   View Beneficiary -------------------------------------------------*/
    @Override
    public Beneficiary viewBeneficiary(String beneficiaryName, String key)
            throws BeneficiaryException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepo.findByNameWallet(wallet.getWalletId(), beneficiaryName);

        if (beneficiaries == null) {
            throw new BeneficiaryException("Beneficiário Inexistente");
        }
        return beneficiaries;

    }

    /*--------------------------------------------   View All Beneficiary -------------------------------------------------*/
    @Override
    public List<Beneficiary> viewAllBeneficiary(String key) throws BeneficiaryException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        List<Beneficiary> beneficiaries = beneficiaryRepo.findByWallet(wallet.getWalletId());

        if (beneficiaries.isEmpty()) {
            throw new BeneficiaryException("Não existe beneficiário");
        }
        return beneficiaries;
    }

    /*--------------------------------------------   Delete Beneficiary -------------------------------------------------*/
    @Override
    public Beneficiary deleteBeneficiary(String key, BeneficiaryDTO beneficiaryDTO)
            throws BeneficiaryException, CustomerException {

        CurrentUserSession currUserSession = currentSessionRepo.findByUuid(key);
        if (currUserSession == null) {
            throw new CustomerException("Nenhum cliente conectado");
        }

        Wallet wallet = walletRepo.showCustomerWalletDetails(currUserSession.getUserId());

        Beneficiary beneficiaries = beneficiaryRepo.findByMobileWallet(wallet.getWalletId(),
                beneficiaryDTO.getBeneficiaryPhoneNumber());

        if (beneficiaries == null) {
            throw new BeneficiaryException("Nenhum beneficiário encontrado");
        }

        beneficiaryRepo.delete(beneficiaries);

        return beneficiaries;
    }

}
