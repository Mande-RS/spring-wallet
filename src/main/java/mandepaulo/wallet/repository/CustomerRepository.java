package mandepaulo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mandepaulo.wallet.models.Customer;
import java.util.List;

@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("FROM Customer c WHERE c.phoneNumber=?1")
    List<Customer> findByCustomerMobile(String phoneNumber);

}
