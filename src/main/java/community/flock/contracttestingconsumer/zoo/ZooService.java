package community.flock.contracttestingconsumer.zoo;

import community.flock.contracttestingconsumer.zoo.adapter.ZooProducerHttpClient;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZooDto;
import community.flock.contracttestingconsumer.zoo.domain.Zoo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ZooService {

    private final @NonNull ZooProducerHttpClient zooProducerHttpClient;

    List<Zoo> getZoos() throws IOException, InterruptedException {
        List<ZooDto> zoosDto = zooProducerHttpClient.getZoos();
        return toDomain(zoosDto);
    }

    Zoo getZoo(String id) throws IOException, InterruptedException {
        return toDomain(Collections.singletonList(zooProducerHttpClient.getZoo(id))).get(0);
    }

    private List<Zoo> toDomain(List<ZooDto> zooDtos) {
        return zooDtos.stream()
                .map(zooDto -> new Zoo(zooDto.getId(), zooDto.getAddress().getCountry(), zooDto.getAddress().getStreetName()))
                .collect(toList());
    }

}
