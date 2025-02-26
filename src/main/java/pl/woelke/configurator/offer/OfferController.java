package pl.woelke.configurator.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.woelke.configurator.bundle.Bundle;
import pl.woelke.configurator.bundle.BundleResponse;
import pl.woelke.configurator.bundle.BundleService;
import pl.woelke.configurator.bundle.BundleRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
@Tag(name = "Offer Controller", description = "Endpointy do tworzenia ofert EBS")
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/draft")
    public ResponseEntity<Offer> createOffer() {
        Offer draftOffer = offerService.createDraftOffer();
        return ResponseEntity.ok(draftOffer);
    }

    @PostMapping("/addBundle")
    public ResponseEntity<Offer> addBundleIdToOffer(
            @RequestParam(value = "offerId") Long offerId,
            @RequestParam(value = "bundleId") Long bundleId) {
        Offer updatedOffer = offerService.addBundleIdToOffer(offerId, bundleId);
        return ResponseEntity.ok(updatedOffer);
    }

    @PutMapping("/finalize")
    public ResponseEntity<Offer> finalizeOffer(@RequestParam Long offerId) {
        Offer finalizedOffer = offerService.finalizeOffer(offerId);
        return ResponseEntity.ok(finalizedOffer);
    }

    @PostMapping("/clone/{offerId}")
    @Operation(
            summary = "kopiowanie oferty",
            description = "Ten endpoint służy do powielania oferty, wcześniej utworzonej"
    )
    public ResponseEntity<Offer> cloneOffer(@PathVariable Long offerId) {
        Offer clonedOffer = offerService.cloneOffer(offerId);
        return ResponseEntity.ok(clonedOffer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable(value = "id") Long id) {
        OfferResponse response = offerService.findById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        offerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers() {
        List<OfferResponse> offers = offerService.findAllOffers();
        return ResponseEntity.ok(offers);
    }
}
