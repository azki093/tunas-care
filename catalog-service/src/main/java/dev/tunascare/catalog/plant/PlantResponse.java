package dev.tunascare.catalog.plant;

import java.time.OffsetDateTime;

public record PlantResponse(
        Long id,
        String name,
        String species,
        String location,
        OffsetDateTime createdAt
) {

    public static PlantResponse from(Plant plant) {
        return new PlantResponse(
                plant.getId(),
                plant.getName(),
                plant.getSpecies(),
                plant.getLocation(),
                plant.getCreatedAt()
        );
    }
}
