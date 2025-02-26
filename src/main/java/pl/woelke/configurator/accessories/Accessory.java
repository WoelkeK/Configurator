package pl.woelke.configurator.accessories;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
import pl.woelke.configurator.bundle.Bundle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "accessories")
    @ToString.Exclude // Unikaj rekurencji w toString()
    @EqualsAndHashCode.Exclude // Unikaj rekurencji w equals()/hashCode()
    @JsonBackReference // Zapobiega rekurencji w JSON
    private List<Bundle> bundles = new ArrayList<>();

}
