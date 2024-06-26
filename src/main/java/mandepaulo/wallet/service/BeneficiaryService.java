package mandepaulo.wallet.service;

import mandepaulo.wallet.exceptions.BeneficiaryException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Beneficiary;
import mandepaulo.wallet.models.dto.BeneficiaryDTO;

import java.util.List;

public interface BeneficiaryService {

    public Beneficiary addBeneficiary(Beneficiary beneficiary, String key)
            throws BeneficiaryException, CustomerException, WalletException;

    public List<Beneficiary> findAllByWallet(Integer walletId) throws BeneficiaryException;

    public Beneficiary viewBeneficiary(String beneficiaryName, String key)
            throws BeneficiaryException, CustomerException;

    public List<Beneficiary> viewAllBeneficiary(String key) throws BeneficiaryException, CustomerException;

    public Beneficiary deleteBeneficiary(String key, BeneficiaryDTO beneficiaryDTO)
            throws BeneficiaryException, CustomerException;

}
