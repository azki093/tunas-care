package dev.tunascare.care.plant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Component
public class PlantClient {

    private final RestClient restClient;

    public PlantClient(RestClient.Builder builder,
                       @Value("${services.catalog.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public void verifyPlantExists(Long plantId) {
        try {
            restClient.get()
                    .uri("/api/plants/{id}", plantId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found", exception);
        } catch (ResourceAccessException exception) {
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Catalog service is unavailable",
                    exception
            );
        }
    }
}
