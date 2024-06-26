package mandepaulo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mandepaulo.wallet.models.Beneficiary;
import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, String> {

    @Query("SELECT b FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId = ?1 AND b.beneficiaryName = ?2")
    Beneficiary findByNameWallet(Integer walletId, String beneficiaryName);

    @Query("SELECT b FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId = ?1 AND b.beneficiaryPhoneNumber = ?2")
    Beneficiary findByMobileWallet(Integer walletId, String beneficiaryPhoneNumber);

    @Query("SELECT b FROM Beneficiary b INNER JOIN b.wallet w WHERE w.walletId = ?1")
    List<Beneficiary> findByWallet(Integer walletId);
}
