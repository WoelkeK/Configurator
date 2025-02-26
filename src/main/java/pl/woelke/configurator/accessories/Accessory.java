package pl.woelke.configurator.accessories;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString()
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, max = 150)
    @NotBlank
    private String name;

    @NotNull
    @Digits(integer = 10, fraction = 2, message = "Price must be a number with 2 digits after the decimal point")
    private BigDecimal price;

}
