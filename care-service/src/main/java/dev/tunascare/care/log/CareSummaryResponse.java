package dev.tunascare.care.log;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record CareSummaryResponse(
        Long plantId,
        Long totalEvents,
        Long wateringEvents,
        Long fertilizingEvents,
        OffsetDateTime lastWateredAt,
        OffsetDateTime lastFertilizedAt
) {

    public static CareSummaryResponse from(CareSummaryProjection projection) {
        return new CareSummaryResponse(
                projection.getPlantId(),
                projection.getTotalEvents(),
                projection.getWateringEvents(),
                projection.getFertilizingEvents(),
                toUtcOffsetDateTime(projection.getLastWateredAt()),
                toUtcOffsetDateTime(projection.getLastFertilizedAt())
        );
    }

    private static OffsetDateTime toUtcOffsetDateTime(Instant instant) {
        return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
    }
}
