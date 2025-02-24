package pl.woelke.configurator.printer.response;

import lombok.Value;
import pl.woelke.configurator.printer.accessories.Accessories;
import pl.woelke.configurator.printer.model.Printer;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class ReadPrinterResponse {

    Long id;
    String name;
    List<PrinterAccessoriesResponse> items;

    public static ReadPrinterResponse from(Printer printer) {

        return new ReadPrinterResponse(
                printer.getId(),
                printer.getName(),
                printer.getItems().stream()
                        .map(PrinterAccessoriesResponse::from)
                        .collect(Collectors.toList()));
    }

    @Value
    public static class PrinterAccessoriesResponse {

        Long id;
        String itemName;
        BigDecimal price;

        public static PrinterAccessoriesResponse from(Accessories accessories) {

            return new PrinterAccessoriesResponse(
                    accessories.getId(),
                    accessories.getItemName(),
                    accessories.getPrice());
        }
    }
}
