package pl.woelke.configurator.printer;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ReadPrinterResponse {

    String name;
    BigDecimal price;

    public static ReadPrinterResponse from(Printer printer) {

        return new ReadPrinterResponse(
                printer.getName(),
                printer.getPrice());
    }
}
