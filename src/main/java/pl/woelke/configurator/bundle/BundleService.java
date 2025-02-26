package pl.woelke.configurator.bundle;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.woelke.configurator.accessories.Accessory;
import pl.woelke.configurator.accessories.AccessoryRepository;
import pl.woelke.configurator.printer.Printer;
import pl.woelke.configurator.printer.PrinterRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BundleService {

    private final BundleRepository bundleRepository;

    private final PrinterRepository printerRepository;

    private final AccessoryRepository accessoryRepository;

    public BundleResponse createBundle(BundleRequest request) {

        Long printerId = request.getPrinterId();
        List<Long> accessoryIds = request.getAccessoryIds();
        Printer printer = printerRepository.findById(printerId)
                .orElseThrow(() -> new EntityNotFoundException("Printer not found"));

        List<Accessory> accessories = accessoryRepository.findByIds(accessoryIds);
        Map<Long, Accessory> accessoryMap = accessories.stream().collect(Collectors.toMap(Accessory::getId, Function.identity()));

        List<Accessory> result = new ArrayList<>();
        for(Long id :accessoryIds){
            Accessory accessory = accessoryMap.get(id);
            if(accessory != null){
                result.add(accessory);
            }
        }

        Bundle bundle = new Bundle();
        bundle.setPrinter(printer);
        bundle.setAccessories(result);
        bundle.setTotalPrice(calculateTotalPrice(printer, result));
        return BundleResponse.from(bundleRepository.save(bundle));
    }


    private BigDecimal calculateTotalPrice(Printer printer, List<Accessory> accessories) {
        BigDecimal totalPrice = printer.getPrice();
        for (Accessory accessory : accessories) {
            totalPrice = totalPrice.add(accessory.getPrice());
        }
        return totalPrice;
    }

    public BundleResponse findById(Long id) {
        Bundle bundle = bundleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bundle not found"));
        return BundleResponse.from(bundle);

    }

    @Transactional
    public BundleResponse update(Long id, BundleRequest request) {
        Bundle editedBundle = bundleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bundle not found"));
        editedBundle.setPrinter(printerRepository.findById(request.getPrinterId()).orElseThrow());
        editedBundle.setAccessories(accessoryRepository.findAllById(request.getAccessoryIds()));
        bundleRepository.save(editedBundle);
        return BundleResponse.from(editedBundle);
    }
    @Transactional
    public void delete(Long id) {
        Bundle toDeleteBundle = bundleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bundle not found"));
        bundleRepository.delete(toDeleteBundle);
    }

    public List<BundleResponse> findAll() {
        List<Bundle> bundles = bundleRepository.findAll();
        return BundleResponse.mapBundlesToResponses(bundles);
    }

    public BigDecimal calculateBundlePrice(Bundle bundle) {
        // Pobierz cenę urządzenia
        BigDecimal devicePrice = bundle.getPrinter().getPrice();

        // Oblicz sumę cen akcesoriów
        BigDecimal accessoriesPrice = bundle.getAccessories().stream()
                .map(Accessory::getPrice) // Pobierz cenę każdego akcesorium
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumuj ceny

        // Dodaj cenę urządzenia do sumy cen akcesoriów
        return devicePrice.add(accessoriesPrice);
    }
}
