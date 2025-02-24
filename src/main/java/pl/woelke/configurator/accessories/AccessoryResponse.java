package pl.woelke.configurator.accessories;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

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
}
