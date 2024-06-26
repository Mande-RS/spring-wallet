package mandepaulo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mandepaulo.wallet.models.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("FROM Wallet w INNER JOIN w.customer c WHERE c.customerId=?1")
    public Wallet showCustomerWalletDetails(Integer customerId);
}