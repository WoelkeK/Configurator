package pl.woelke.configurator.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.woelke.configurator.bundle.Bundle;
import pl.woelke.configurator.bundle.BundleService;
import pl.woelke.configurator.bundle.BundleRequest;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
@Tag(name = "Offer Controller", description = "Endpointy do tworzenia ofert EBS")
@AllArgsConstructor
public class OfferController {

    private final OfferService offerService;

    private final BundleService bundleService;

    @PostMapping
    @Operation(
            summary = "Utwórz ofertę",
            description = "Ten endpoint służy do tworzenia ofert na podstawie kompletacji"
    )
    public ResponseEntity<Offer> createOffer(@RequestBody List<BundleRequest> bundleRequests) {
        List<Bundle> bundles = new ArrayList<>();
        for (BundleRequest request : bundleRequests) {
            Bundle bundle = bundleService.createBundle(request.getPrinterId(), request.getAccessoryIds());
            bundles.add(bundle);
        }

        Offer offer = offerService.createOffer(bundles);
        return ResponseEntity.ok(offer);
    }

    @PostMapping("/clone/{offerId}")
    @Operation(
            summary = "kopiowanie oferty",
            description = "Ten endpoint służy do powielania oferty, wcześniej stworzonej"
    )
    public ResponseEntity<Offer> cloneOffer(@PathVariable Long offerId) {
        Offer clonedOffer = offerService.cloneOffer(offerId);
        return ResponseEntity.ok(clonedOffer);
    }
}
