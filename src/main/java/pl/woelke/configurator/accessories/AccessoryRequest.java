package pl.woelke.configurator.accessories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class AccessoryRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    String name;
    @NotBlank
    BigDecimal price;
}
