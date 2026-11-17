package dev.tunascare.catalog.plant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlantRequest(
        @NotBlank @Size(max = 80) String name,
        @Size(max = 80) String species,
        @Size(max = 120) String location
) {
}
