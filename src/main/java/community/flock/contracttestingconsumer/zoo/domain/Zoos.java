package community.flock.contracttestingconsumer.zoo.domain;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Zoos {
    final @NonNull List<Zoo> zoos;
}
