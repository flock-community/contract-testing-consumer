package community.flock.contracttestingconsumer.zoo.adapter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class MammalRequestDTO {
    final @NonNull String name;
    final @NonNull String species;
}
