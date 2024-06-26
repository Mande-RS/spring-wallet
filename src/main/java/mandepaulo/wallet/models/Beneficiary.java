package mandepaulo.wallet.models;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Beneficiary implements Serializable {

    @Id
    @NotNull
    @Size(min = 9, max = 12, message = "Número de telefone inválido [Deve ter apenas 10 dígitos]")
    private String beneficiaryPhoneNumber;
    @NotNull
    @Size(min = 3, message = "Nome de Cliente Inválido [deve ter ao menos 3 caracteres]")
    private String beneficiaryName;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "walletId", referencedColumnName = "walletId")
    private Wallet wallet;

    public Beneficiary(String beneficiaryPhoneNumber, String beneficiaryName) {
        this.beneficiaryPhoneNumber = beneficiaryPhoneNumber;
        this.beneficiaryName = beneficiaryName;
    }

}
