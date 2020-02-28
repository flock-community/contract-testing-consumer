package community.flock.contracttestingconsumer.zoo.adapter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class AddressDto {
    final @NonNull String streetName;
    final @NonNull String country;
}
