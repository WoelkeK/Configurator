package pl.woelke.configurator.offer;

import lombok.Builder;
import lombok.Value;
import pl.woelke.configurator.bundle.BundleResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder( toBuilder = true)
public class OfferResponse {
    Long id;
    List<BundleResponse> bundles;
    LocalDate creationDate;
    BigDecimal totalPrice;


    public static OfferResponse from(Offer offer) {

        return OfferResponse.builder()
                .id(offer.getId())
                .creationDate(offer.getCreationDate())
                .totalPrice(offer.getTotalPrice())
                .bundles(getBundleList(offer))
                .build();
    }

    private static List<BundleResponse> getBundleList(Offer offer) {
        return offer.getBundles().stream().map(BundleResponse::from).toList();
    }

    public static List<OfferResponse> mapOffersToResponses(List<Offer> offers) {
        return offers.stream()
                .map(OfferResponse::from)
                .toList();
    }
}
