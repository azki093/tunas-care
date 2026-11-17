package dev.tunascare.care.log;

import java.time.Instant;

public interface CareSummaryProjection {

    Long getPlantId();

    Long getTotalEvents();

    Long getWateringEvents();

    Long getFertilizingEvents();

    Instant getLastWateredAt();

    Instant getLastFertilizedAt();
}
