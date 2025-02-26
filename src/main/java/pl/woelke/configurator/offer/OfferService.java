package pl.woelke.configurator.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.woelke.configurator.bundle.Bundle;
import pl.woelke.configurator.bundle.BundleResponse;
import pl.woelke.configurator.bundle.BundleService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    private final BundleService bundleService;


    public Offer createOffer(List<BundleResponse> bundlesList) {

        List<Bundle> bundles = BundleResponse.mapBundlesResponseToBundles(bundlesList);
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

    public OfferResponse findById(Long id) {
        Offer offerDetails = offerRepository.findById(id).orElseThrow(() -> new RuntimeException("Offer not found"));
        return OfferResponse.from(offerDetails);
    }

    public Offer createDraftOffer() {
        Offer offer = new Offer();
        offer.setCreationDate(LocalDate.now());
        offer.setStatus(OfferStatus.DRAFT); // Ustaw status na DRAFT
        offer.setTotalPrice(BigDecimal.valueOf(0.0)); // Początkowa cena to 0
        return offerRepository.save(offer);
    }

    public Offer addBundleToOffer(Long offerId, Bundle bundle) {
        // Pobierz ofertę
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        // Ustaw relację między Bundle a Offer
        bundle.setOffer(offer);

        // Dodaj Bundle do listy w Offer
        offer.getBundles().add(bundle);

        // Oblicz nową cenę końcową oferty
        BigDecimal newFinalPrice = offer.getBundles().stream()
                .map(bundleService::calculateBundlePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        offer.setTotalPrice(newFinalPrice);

        // Zapisz zmiany
        return offerRepository.save(offer);
    }

    public Offer finalizeOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        offer.setStatus(OfferStatus.FINAL);
        return offerRepository.save(offer);
    }

    public Offer addBundleIdToOffer(Long offerId, Long bundleId) {
        Offer editedOffer = offerRepository.findById(offerId).orElseThrow(() -> new RuntimeException("Offer not found"));
        BundleResponse bundleResponse = bundleService.findById(bundleId);
        Bundle bundle = Bundle.builder()
                .printer(bundleResponse.getPrinter())
                .accessories(bundleResponse.getAccessories())
                .totalPrice(bundleResponse.getTotalPrice())
                .offer(editedOffer)
                .id(bundleId)
                .build();

        List<Bundle> bundles = editedOffer.getBundles();
        bundles.add(bundle);
        editedOffer.setBundles(bundles);
        return offerRepository.save(editedOffer);
    }

    public void delete(Long id) {
        offerRepository.deleteById(id);
    }

    public List<OfferResponse> findAllOffers() {
        List<Offer> allOffers = offerRepository.findAll();
        return OfferResponse.mapOffersToResponses(allOffers);
    }
}


