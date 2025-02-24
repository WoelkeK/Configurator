package pl.woelke.configurator.printer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UpdatePrinterRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    String name;

}
