package pl.woelke.configurator.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.woelke.configurator.bundle.Bundle;
import pl.woelke.configurator.bundle.BundleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BundleService bundleService;

    public Offer createOffer(List<Bundle> bundles) {
        Offer offer = new Offer();
        offer.setBundles(bundles);
        offer.setCreationDate(LocalDate.now()); // Ustawienie daty utworzenia
        offer.setTotalPrice(calculateTotalPrice(bundles));

        return offerRepository.save(offer);
    }

    public Offer cloneOffer(Long offerId) {
        Offer originalOffer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        Offer clonedOffer = new Offer();
        clonedOffer.setCreationDate(LocalDate.now());

        List<Bundle> clonedBundles = new ArrayList<>();
        for (Bundle bundle : originalOffer.getBundles()) {
            Bundle clonedBundle = new Bundle();
            clonedBundle.setPrinter(bundle.getPrinter());
            clonedBundle.setAccessories(new ArrayList<>(bundle.getAccessories()));
            clonedBundle.setTotalPrice(bundle.getTotalPrice());
            clonedBundles.add(clonedBundle);
        }

        clonedOffer.setBundles(clonedBundles);
        clonedOffer.setTotalPrice(calculateTotalPrice(clonedBundles));

        return offerRepository.save(clonedOffer);
    }

    private BigDecimal calculateTotalPrice(List<Bundle> bundles) {
        BigDecimal totalPrice = new BigDecimal("0.00");
        for (Bundle bundle : bundles) {
            totalPrice = totalPrice.add(bundle.getTotalPrice());
        }
        return totalPrice;
    }
}
