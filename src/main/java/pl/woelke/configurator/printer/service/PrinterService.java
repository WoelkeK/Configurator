package pl.woelke.configurator.printer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.woelke.configurator.printer.accessories.Accessories;
import pl.woelke.configurator.printer.model.Printer;
import pl.woelke.configurator.printer.repository.PrinterRepository;
import pl.woelke.configurator.printer.request.CreatePrinterRequest;
import pl.woelke.configurator.printer.request.UpdatePrinterRequest;
import pl.woelke.configurator.printer.response.ReadPrinterResponse;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterRepository printerRepository;

    public ReadPrinterResponse read(Long id) {
        return printerRepository.findById(id)
                .map(ReadPrinterResponse::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Printer create(CreatePrinterRequest printerRequest) {

        Printer printer = new Printer();
        printer.setName(printerRequest.getName());

        Set<Accessories> accessories = new HashSet<>();

        // dodać implementacje dodawania akcesoriów z listy zewnętrznej
        accessories.add(Accessories.builder().itemName("Tusz XI32001000").price(new java.math.BigDecimal("121.00")).build());
        accessories.add(Accessories.builder().itemName("Fotokomórka standard").price(new java.math.BigDecimal("331.00")).build());
        printer.setItems(accessories);
        return printerRepository.save(printer);
    }

    @Transactional
    public Printer update(UpdatePrinterRequest printerRequest, Long id) {
        Printer printer = printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Printer editedPrinter = new Printer();
        editedPrinter.setName(printerRequest.getName());
        return printerRepository.save(editedPrinter);
    }

    @Transactional
    public void delete(Long id) {
        printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        printerRepository.deleteById(id);
    }
}
