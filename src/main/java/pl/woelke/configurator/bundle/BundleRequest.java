package pl.woelke.configurator.bundle;

import lombok.Value;

import java.util.List;

@Value
public class BundleRequest {

    Long printerId;
    List<Long> accessoryIds;
}
