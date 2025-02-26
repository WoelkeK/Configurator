package pl.woelke.configurator.accessories;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class AccessoryResponse {

    String name;
    BigDecimal price;

    public static AccessoryResponse from(Accessory accessory) {
        return AccessoryResponse.builder()
                .name(accessory.getName())
                .price(accessory.getPrice())
                .build();
    }

    public static List<AccessoryResponse> mapAccessoriesToResponses(List<Accessory> accessories) {
        return accessories.stream()
                .map(AccessoryResponse::from)
                .toList();
    }
}
