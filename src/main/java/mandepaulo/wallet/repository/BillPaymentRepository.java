package mandepaulo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mandepaulo.wallet.models.BillPayment;

@Repository
public interface BillPaymentRepository extends JpaRepository<BillPayment, Integer> {

}