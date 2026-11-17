package dev.tunascare.catalog.plant;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public PlantResponse create(PlantRequest request) {
        Plant plant = new Plant(request.name().trim(), request.species(), request.location());
        return PlantResponse.from(plantRepository.save(plant));
    }

    @Transactional(readOnly = true)
    public List<PlantResponse> findAll() {
        return plantRepository.findAllByOrderByNameAsc()
                .stream()
                .map(PlantResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlantResponse findById(Long id) {
        return plantRepository.findById(id)
                .map(PlantResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found"));
    }
}
