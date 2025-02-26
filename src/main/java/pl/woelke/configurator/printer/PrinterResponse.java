package pl.woelke.configurator.printer;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class PrinterResponse {

    String name;
    BigDecimal price;

    public static PrinterResponse from(Printer printer) {

        return new PrinterResponse(
                printer.getName(),
                printer.getPrice());
    }

    public static List<PrinterResponse> mapPrintersToReadResponses(List<Printer> printers) {
        return printers.stream()
                .map(PrinterResponse::from)
                .toList();
    }
}
