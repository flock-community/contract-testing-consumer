package community.flock.contracttestingconsumer.zoo;

import community.flock.contracttestingconsumer.zoo.adapter.ZooProducerHttpClient;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZooDto;
import community.flock.contracttestingconsumer.zoo.domain.Zoo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ZooService {

    private final @NonNull ZooProducerHttpClient zooProducerHttpClient;

    List<Zoo> getZoos() throws IOException, InterruptedException {
        List<ZooDto> zoosDto = zooProducerHttpClient.getZooInfo();
        return toDomain(zoosDto);
    }

    private List<Zoo> toDomain(List<ZooDto> zooDtos) {
        return zooDtos.stream()
                .map(zooDto -> new Zoo(zooDto.getId(), zooDto.getAddress().getCountry(), zooDto.getAddress().getStreetName()))
                .collect(toList());
    }

}
