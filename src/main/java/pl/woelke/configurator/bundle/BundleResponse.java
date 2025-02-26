package pl.woelke.configurator.bundle;

import lombok.Builder;
import lombok.Value;
import pl.woelke.configurator.accessories.Accessory;
import pl.woelke.configurator.offer.Offer;
import pl.woelke.configurator.printer.Printer;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class BundleResponse {

    Offer offer;
    Printer printer;
    List<Accessory> accessories;
    BigDecimal totalPrice;

    public static BundleResponse from(Bundle bundle) {
        return BundleResponse.builder()
                .offer(bundle.getOffer())
                .printer(bundle.getPrinter())
                .totalPrice(bundle.getTotalPrice())
                .accessories(bundle.getAccessories())
                .build();
    }

    public static Bundle from(BundleResponse bundleResponse) {
        return Bundle.builder()
                .offer(bundleResponse.getOffer())
                .accessories(bundleResponse.getAccessories())
                .totalPrice(bundleResponse.getTotalPrice())
                .printer(bundleResponse.getPrinter())
                .build();
    }

    public static List<BundleResponse> mapBundlesToResponses(List<Bundle> bundles) {
        return bundles.stream()
                .map(BundleResponse::from)
                .toList();
    }

    public static List<Bundle> mapBundlesResponseToBundles(List<BundleResponse> bundlesResponse) {
        return bundlesResponse.stream()
                .map(BundleResponse::from)
                .toList();
    }
}
