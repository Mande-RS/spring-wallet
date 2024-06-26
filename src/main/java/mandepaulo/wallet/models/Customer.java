package mandepaulo.wallet.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer customerId;
    @NotNull
    @Size(min = 3, message = "Nome de Cliente Inválido [deve ter ao menos 3 caracteres]")
    private String customerName;
    @NotNull
    @Size(min = 9, max = 12, message = "Número de telefone inválido [Deve ter 9-12 dígitos]")
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate born;
    @NotNull
    @Size(min = 6, max = 12, message = "Senha Inválida [deve ser 6 a 8 caracteres]")
    private String password;

}
