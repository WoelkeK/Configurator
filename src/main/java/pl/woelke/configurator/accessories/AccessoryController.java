package pl.woelke.configurator.accessories;


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
import org.springframework.web.bind.annotation.RestController;
import pl.woelke.configurator.error.DatabaseConstraintViolation;

import java.util.List;

@RestController
@RequestMapping("/api/accessories")
@Tag(name = "Accessory Controller", description = "Endpointy do zarządzania danymi o akcesoriach")
@AllArgsConstructor
public class AccessoryController {

    private final AccessoryService accessoryService;

    @GetMapping("/{id}")
    public ResponseEntity<AccessoryResponse> read(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(accessoryService.read(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccessoryResponse> update(@PathVariable(value = "id") Long id, @RequestBody AccessoryRequest request) {
        return new ResponseEntity<>(accessoryService.update(id, request), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<AccessoryResponse> create(@RequestBody AccessoryRequest request) {
        return new ResponseEntity<>(accessoryService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws DatabaseConstraintViolation {
        try {
            accessoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            throw new DatabaseConstraintViolation("Błąd naruszenia integralności danych.", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<AccessoryResponse>> getAllAccessories() {
        List<AccessoryResponse> accessories = accessoryService.findAllAccessories();
        return new ResponseEntity<>(accessories, HttpStatus.OK);
    }
}
