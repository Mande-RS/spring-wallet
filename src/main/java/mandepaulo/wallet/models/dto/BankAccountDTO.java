package mandepaulo.wallet.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {

    @NotNull
    private Integer accountNumber;
    @NotNull
    @Size(min = 21, max = 27, message = "IBAN inválido [Deve ter no mínimo 21 caracteres]")
    private String iban;
    @NotNull
    @Size(min = 3, max = 15, message = "Nome do Banco inválido [Deve ter 3-15 caracteres apenas]")
    private String bankName;
    @NotNull
    private Double balance;

}
