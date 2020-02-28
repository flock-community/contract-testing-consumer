package community.flock.contracttestingconsumer.zoo;

import community.flock.contracttestingconsumer.zoo.response.ZooResponse;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class ZoosResponse {
    final @NonNull String context;
    final @NonNull List<ZooResponse> zoos;
}
