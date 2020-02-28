package community.flock.contracttestingconsumer.zoo.response;

import lombok.Data;
import lombok.NonNull;

@Data
public class ZooResponse {
    final @NonNull String id;
    final @NonNull String description;
    final @NonNull String country;
    final @NonNull String streetName;
}
