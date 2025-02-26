package pl.woelke.configurator.offer;

import lombok.Value;
import pl.woelke.configurator.bundle.Bundle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class OfferRequest {

    List<Bundle> bundles = new ArrayList<>();
    LocalDate creationDate;
    BigDecimal totalPrice;
}
