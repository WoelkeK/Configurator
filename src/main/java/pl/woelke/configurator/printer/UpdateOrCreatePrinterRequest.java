package pl.woelke.configurator.printer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class UpdateOrCreatePrinterRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    String name;

    @NotBlank
    BigDecimal price;

    public static Printer fromRequestToEntity(UpdateOrCreatePrinterRequest printerRequest) {
        return Printer.builder()
                .name(printerRequest.getName())
                .price(printerRequest.getPrice())
                .build();
    }
}

