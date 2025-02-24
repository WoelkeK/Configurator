package pl.woelke.configurator.bundle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.woelke.configurator.accessories.Accessory;
import pl.woelke.configurator.offer.Offer;
import pl.woelke.configurator.printer.Printer;

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
public class Bundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable = false) // Relacja wiele-do-jednego z Offer
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "printer_id", nullable = false)
    private Printer printer;

    @ManyToMany
    @JoinTable(
            name = "bundle_accessories",
            joinColumns = @JoinColumn(name = "bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "accessory_id")
    )
    private List<Accessory> accessories = new ArrayList<>();

    @NotBlank
    private BigDecimal totalPrice; // Suma cen drukarki i akcesoriów
}
