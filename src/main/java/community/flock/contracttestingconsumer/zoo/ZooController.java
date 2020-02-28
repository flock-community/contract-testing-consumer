package community.flock.contracttestingconsumer.zoo;

import community.flock.contracttestingconsumer.zoo.domain.Zoo;
import community.flock.contracttestingconsumer.zoo.domain.Zoos;
import community.flock.contracttestingconsumer.zoo.response.ZooResponse;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/zoos")
public class ZooController {

    private final @NonNull ZooService zooService;

    @GetMapping
    public ZoosResponse getInfoAll() throws IOException, InterruptedException {
        Zoos zoos = zooService.getZoos();
        return toResponse(zoos);
    }

    @GetMapping("/{id}")
    public ZooResponse getInfoSingle(
            @PathVariable("id") String id
    ) throws IOException, InterruptedException {
        Zoo zoo = zooService.getZoo(id);
        ZooResponse zooResponse = toResponse(zoo);
        return zooResponse;
    }

    private ZooResponse toResponse(Zoo zoo) {
        return new ZooResponse(
                zoo.getId(),
                "This Zoo is a wonderful zoo, full of Mammals",
                zoo.getCountry(),
                zoo.getStreetName()
        );
    }

    private ZoosResponse toResponse(Zoos zoos) {
        List<ZooResponse> zooResponses = zoos.getZoos()
                .stream()
                .map(this::toResponse)
                .collect(toList());

        return new ZoosResponse("All known Zoos", zooResponses);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Throwable.class})
    ErrorInfo unhandledExceptions(Throwable t) {
        return new ErrorInfo(Instant.now(), "Something unexpected happened. Please try again later", new DebugErrorInfo(t.getMessage(),t));
    }
}

@Data
class ErrorInfo {
    final @NonNull Instant timestamp;
    final @NonNull String message;
    /* @JsonIgnore */ final DebugErrorInfo debug; // only for debugging purposes (DO NOT USE on PRO)
}

@Data
class DebugErrorInfo{
    final @NonNull String message;
    final @NonNull Throwable exception;
}
