package community.flock.contracttestingconsumer.zoo.adapter.dto;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class ZoosDto {
    final @NonNull List<ZooDto> zoos;
}
