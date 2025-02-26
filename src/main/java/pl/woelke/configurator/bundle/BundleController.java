package pl.woelke.configurator.bundle;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bundles")
@AllArgsConstructor
public class BundleController {

    private final BundleService bundleService;

    @PostMapping("/create")
    public ResponseEntity<BundleResponse> createBundle(@RequestBody BundleRequest request) {
        BundleResponse response = bundleService.createBundle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BundleResponse> read(@PathVariable(value = "id") Long id) {
        BundleResponse response = bundleService.findById(id);
        return ResponseEntity.ok(response);
    }

    //do poprawki duplikaty, dodać ilosć??
    @PutMapping("/update/{id}")
    public ResponseEntity<BundleResponse> update(@PathVariable(value = "id") Long id, @RequestBody BundleRequest request) {
        BundleResponse response = bundleService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        bundleService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<BundleResponse>> getAllBundles() {
        List<BundleResponse> response = bundleService.findAll();
        return ResponseEntity.ok(response);
    }
}
