package ewm.client;

import ewm.CreateEndpointHitDto;
import ewm.EndpointStatDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.MaxAttemptsRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatsClient {
    private final DiscoveryClient discoveryClient;

    private final String statsServiceId;

    private final RetryTemplate retryTemplate;

    public StatsClient(DiscoveryClient discoveryClient,
                       @Value("${discovery.services.stats-server-id}") String statsServiceId) {
        this.discoveryClient = discoveryClient;
        this.statsServiceId = statsServiceId;

        this.retryTemplate = new RetryTemplate();

        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(3000L);
        this.retryTemplate.setBackOffPolicy(backOffPolicy);

        MaxAttemptsRetryPolicy retryPolicy = new MaxAttemptsRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        this.retryTemplate.setRetryPolicy(retryPolicy);
    }

    public ResponseEntity<Void> sendHit(CreateEndpointHitDto hitDto) {
        URI uri = makeUri("/hit");

        RestClient client = RestClient.builder()
                .baseUrl(uri.resolve("/").toString()) // Use service root URL
                .build();

        return client.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(hitDto)
                .retrieve()
                .toEntity(Void.class);
    }

    public ResponseEntity<List<EndpointStatDto>> getStats(LocalDateTime start, LocalDateTime end,
                                                          List<String> uris, boolean unique) {
        URI uri = makeUri("/stats");

        RestClient client = RestClient.builder()
                .baseUrl(uri.resolve("/").toString())
                .build();

        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .queryParam("end", end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {
                });
    }

    private URI makeUri(String path) {
        ServiceInstance instance = retryTemplate.execute(context -> getInstance(statsServiceId));
        return URI.create("http://" + instance.getHost() + ":" + instance.getPort() + path);
    }

    private ServiceInstance getInstance(String serviceId) {
        return discoveryClient.getInstances(serviceId).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Сервис статистики не найден: " + serviceId));
    }
}
