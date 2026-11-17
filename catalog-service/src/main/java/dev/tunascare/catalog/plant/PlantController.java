package dev.tunascare.catalog.plant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantResponse create(@Valid @RequestBody PlantRequest request) {
        return plantService.create(request);
    }

    @GetMapping
    public List<PlantResponse> findAll() {
        return plantService.findAll();
    }

    @GetMapping("/{id}")
    public PlantResponse findById(@PathVariable Long id) {
        return plantService.findById(id);
    }
}
