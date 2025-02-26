package pl.woelke.configurator.bundle;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.woelke.configurator.accessories.Accessory;
import pl.woelke.configurator.accessories.AccessoryRepository;
import pl.woelke.configurator.printer.Printer;
import pl.woelke.configurator.printer.PrinterRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BundleService {

    private final BundleRepository bundleRepository;

    private final PrinterRepository printerRepository;

    private final AccessoryRepository accessoryRepository;

    public Bundle createBundle(Long printerId, List<Long> accessoryIds) {
        Printer printer = printerRepository.findById(printerId)
                .orElseThrow(() -> new EntityNotFoundException("Printer not found"));

        List<Accessory> accessories = accessoryRepository.findAllById(accessoryIds);

        Bundle bundle = new Bundle();
        bundle.setPrinter(printer);
        bundle.setAccessories(accessories);
        bundle.setTotalPrice(calculateTotalPrice(printer, accessories));

        return bundleRepository.save(bundle);
    }

    private BigDecimal calculateTotalPrice(Printer printer, List<Accessory> accessories) {
        BigDecimal totalPrice = printer.getPrice();
        for (Accessory accessory : accessories) {
            totalPrice = totalPrice.add(accessory.getPrice());
        }
        return totalPrice;
    }
}
