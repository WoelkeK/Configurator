package pl.woelke.configurator.printer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static pl.woelke.configurator.printer.UpdateOrCreatePrinterRequest.fromRequestToEntity;

@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterRepository printerRepository;

    public ReadPrinterResponse read(Long id) {
        return printerRepository.findById(id)
                .map(ReadPrinterResponse::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Printer create(UpdateOrCreatePrinterRequest printerRequest) {
        Printer printer = fromRequestToEntity(printerRequest);
        return printerRepository.save(printer);
    }


    @Transactional
    public Printer update(UpdateOrCreatePrinterRequest printerRequest, Long id) {
        Printer printer = printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Printer editedPrinter = Printer.builder()
                .id(printer.getId())
                .name(printerRequest.getName())
                .price(printerRequest.getPrice())
                .build();
        return printerRepository.save(editedPrinter);
    }

    @Transactional
    public void delete(Long id) {
        printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        printerRepository.deleteById(id);
    }
}
