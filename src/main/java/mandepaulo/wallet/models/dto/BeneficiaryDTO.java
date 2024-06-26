package mandepaulo.wallet.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BeneficiaryDTO {

    @NotNull
    @Size(min = 9, max = 12, message = "Número de telefone inválido [Deve ter apenas 10 dígitos]")
    private String beneficiaryPhoneNumber;
    @NotNull
    @Size(min = 3, message = "Nome de Cliente Inválido [deve ter ao menos 3 caracteres]")
    private String beneficiaryName;

}
