package mandepaulo.wallet.models;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount implements Serializable {

    @Id
    @NotNull
    private Integer accountNumber;
    @NotNull
    @Size(min = 21, max = 27, message = "IBAN inválido [Deve ter apenas 21 caracteres]")
    private String iban;
    @NotNull
    @Size(min = 3, max = 15, message = "Nome do Banco inválido [Deve ter 3-15 caracteres apenas]")
    private String bankName;
    @NotNull
    private Double balance;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Wallet wallet;

    public BankAccount(@NotNull Integer accountNumber, @NotNull String iban,
            @NotNull @Size(min = 21, max = 21, message = "Nome do Banco Inválido") String bankName,
            @NotNull Double balance) {
        super();
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.bankName = bankName;
        this.balance = balance;

    }

}
