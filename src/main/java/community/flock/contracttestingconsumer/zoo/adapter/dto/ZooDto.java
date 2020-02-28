package community.flock.contracttestingconsumer.zoo.adapter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class ZooDto {
    final @NonNull String id;
    final @NonNull AddressDto address;
}
