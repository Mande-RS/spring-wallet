package mandepaulo.wallet.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer billId;
    private Double amount;
    private String billType;
    private LocalDate paymentDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public BillPayment(Double amount, String billType, LocalDate paymentDate) {
        this.amount = amount;
        this.billType = billType;
        this.paymentDate = paymentDate;
    }

}
