package pl.woelke.configurator.printer;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/printers")
@Tag(name = "Printer Controller", description = "Endpointy do zarządzania danymi o drukarkach")
@AllArgsConstructor
public class PrinterController {

    private final PrinterService printerService;

    @GetMapping("/{id}")
    public ResponseEntity<ReadPrinterResponse> read(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(printerService.read(id), HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<ReadPrinterResponse> create(@RequestBody UpdateOrCreatePrinterRequest request) {
        return new ResponseEntity<>(printerService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReadPrinterResponse> update(@RequestBody UpdateOrCreatePrinterRequest request,
                                                      @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(printerService.update(request, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ReadPrinterResponse> delete(@RequestParam(value = "printerId") Long id) {
        printerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ReadPrinterResponse>> getAllPrinters() {
    return new ResponseEntity<>(printerService.findAllPrinters(), HttpStatus.OK);
    }
}
