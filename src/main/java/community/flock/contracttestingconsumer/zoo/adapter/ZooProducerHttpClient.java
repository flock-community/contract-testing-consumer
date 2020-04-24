package community.flock.contracttestingconsumer.zoo.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import community.flock.contracttestingconsumer.ResourceNotFoundException;
import community.flock.contracttestingconsumer.zoo.adapter.dto.MammalRequestDTO;
import community.flock.contracttestingconsumer.zoo.adapter.dto.ZooDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RequiredArgsConstructor
@Component
public class ZooProducerHttpClient implements DisposableBean {

    private final @NonNull ObjectMapper objectMapper;

    @Value("${httpClients.ZOO.baseUrl}")
    private String baseUrl;

    private HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(2))
            .build();


    @Override
    public void destroy() throws Exception {

    }

    public List<ZooDto> getZooInfo() throws IOException, InterruptedException {
        var uri = fromUriString(baseUrl + "/zoo").build().toUri();

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(4))
                .header("Accept", "application/json")
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        TypeReference<List<ZooDto>> typeReference = new TypeReference<>() {};
        var zoo = objectMapper.readValue(response.body(), typeReference);
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(zoo);

        return zoo;
    }

    public ZooDto getZooInfo(String zooId) throws IOException, InterruptedException {
        var uri = fromUriString(baseUrl + "/zoos/{zooId}").buildAndExpand(zooId).toUri();

        var request = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        var zoo = objectMapper.readValue(response.body(), ZooDto.class);
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(zoo);

        return zoo;
    }

    public String addMammalToZoo(String zooId, MammalRequestDTO mammalRequestDTO ){
        var uri = fromUriString(baseUrl + "/zoos/{zooId}").buildAndExpand(zooId).toUri();


        try {
            String requestBody = objectMapper.writeValueAsString(mammalRequestDTO);

            var request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            throwOnHttpError(send);

            return send.body();

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException("Coul not complete call to ZooProducer");
        }


    }

    private void throwOnHttpError(HttpResponse<String> send) {
        int statusCode = send.statusCode();

        if (statusCode == 404){
            throw new ResourceNotFoundException("Resource not found");
        } else if ( 400 <= statusCode && statusCode <= 499){
            throw new HttpClientErrorException(HttpStatus.valueOf(statusCode), "Client error occured: "  +send.body());
        } else if  (500 <= statusCode && statusCode <= 599) {
            throw new HttpServerErrorException(HttpStatus.valueOf(statusCode), "Server error: " );
        }

    }
}
