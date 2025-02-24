package pl.woelke.configurator.offer;

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
public class OfferController {
    @Autowired
    private OfferService offerService;
    @Autowired
    private BundleService bundleService;

    @PostMapping
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
    public ResponseEntity<Offer> cloneOffer(@PathVariable Long offerId) {
        Offer clonedOffer = offerService.cloneOffer(offerId);
        return ResponseEntity.ok(clonedOffer);
    }
}
