package pl.woelke.configurator.printer;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ReadPrinterResponse {

    String name;
    BigDecimal price;

    public static ReadPrinterResponse from(Printer printer) {

        return new ReadPrinterResponse(
                printer.getName(),
                printer.getPrice());
    }

    public static List<ReadPrinterResponse> mapPrintersToReadResponses(List<Printer> printers) {
        return printers.stream()
                .map(ReadPrinterResponse::from)
                .toList();
    }
}
