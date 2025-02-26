package pl.woelke.configurator.printer;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static pl.woelke.configurator.printer.PrinterResponse.mapPrintersToReadResponses;
import static pl.woelke.configurator.printer.UpdateOrCreatePrinterRequest.fromRequestToEntity;

@Service
@RequiredArgsConstructor
public class PrinterService {

    private final PrinterRepository printerRepository;

    public PrinterResponse read(Long id) {
        return printerRepository.findById(id)
                .map(PrinterResponse::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    public PrinterResponse create(UpdateOrCreatePrinterRequest printerRequest) {
        Printer printer = fromRequestToEntity(printerRequest);
        return PrinterResponse.from(printerRepository.save(printer));
    }


    @Transactional
    public PrinterResponse update(UpdateOrCreatePrinterRequest printerRequest, Long id) {
        Printer printer = printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Printer editedPrinter = Printer.builder()
                .id(printer.getId())
                .name(printerRequest.getName())
                .price(printerRequest.getPrice())
                .build();
        return PrinterResponse.from(printerRepository.save(editedPrinter));
    }

    @Transactional
    public void delete(Long id) {
        printerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        printerRepository.deleteById(id);
    }


    public List<PrinterResponse> findAllPrinters() {
        List<Printer> printers = (List<Printer>) printerRepository.findAll();
        return mapPrintersToReadResponses(printers);
    }
}
