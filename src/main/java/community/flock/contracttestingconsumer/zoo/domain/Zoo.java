package community.flock.contracttestingconsumer.zoo.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class Zoo {
    final @NonNull String id;
    final @NonNull String country;
    final @NonNull String streetName;

}
