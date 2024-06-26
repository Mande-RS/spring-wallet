package mandepaulo.wallet.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession implements Serializable {
    @Id
    @Column(unique = true)
    private Integer userId;
    private String uuid;
    private LocalDateTime localDateTime;

}
