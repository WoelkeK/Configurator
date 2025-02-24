package pl.woelke.configurator.accessories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryResponse read(Long id) {
        return accessoryRepository.findById(id)
                .map(AccessoryResponse::from)
                .orElseThrow();
    }

    public AccessoryResponse create(AccessoryRequest request) {
        Accessory accessory = Accessory.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();

        return AccessoryResponse.from(accessoryRepository.save(accessory));
    }
    @Transactional
    public AccessoryResponse update(Long id, AccessoryRequest request) {
        Accessory editedAccessory = accessoryRepository.findById(id).orElseThrow();
        editedAccessory.setName(request.getName());
        editedAccessory.setPrice(request.getPrice());
        return AccessoryResponse.from(accessoryRepository.save(editedAccessory));
    }
    @Transactional
    public void delete(Long id) {
        accessoryRepository.findById(id).orElseThrow();
        accessoryRepository.deleteById(id);
    }
}
