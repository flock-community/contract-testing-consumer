package community.flock.contracttestingconsumer.zoo;

import community.flock.contracttestingconsumer.zoo.adapter.ZooProducerHttpClient;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZooDto;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZoosDto;
import community.flock.contracttestingconsumer.zoo.domain.Zoo;
import community.flock.contracttestingconsumer.zoo.domain.Zoos;
import community.flock.contracttestingconsumer.zoo.response.ZooResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController("/zoos")
public class ZooService {

    private final @NonNull ZooProducerHttpClient zooProducerHttpClient;


    Zoos getZoos() throws IOException, InterruptedException {
        ZoosDto zoosDto = zooProducerHttpClient.getZooInfo();
        return toDomain(zoosDto);
    }

    Zoo getZoo(@PathVariable("id") String id) throws IOException, InterruptedException {
        ZooDto zooInfo = zooProducerHttpClient.getZooInfo(id);
        return toDomain(zooInfo);
    }


    private Zoos toDomain(ZoosDto zoosDto) {
        List<Zoo> zoos = zoosDto.getZoos()
                .stream()
                .map(it -> toDomain(it))
                .collect(toList());

        return new Zoos(zoos);
    }
    private Zoo toDomain(ZooDto zooDto) {
        return new Zoo(
                zooDto.getId(),
                zooDto.getAddress().getCountry(),
                zooDto.getAddress().getCountry()
        );
    }

}
