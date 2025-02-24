package pl.woelke.configurator.printer.accessories;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.woelke.configurator.printer.model.Printer;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString()
@EqualsAndHashCode()
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Accessories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    private String itemName;

    @NotNull
    private BigDecimal price;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Printer printer;

    public Accessories(String itemName, BigDecimal price, Printer printer) {
        this.itemName = itemName;
        this.price = price;
        this.printer = printer;
    }
}
