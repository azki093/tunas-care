package dev.tunascare.care.log;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CareSummaryResponseTest {

    @Test
    void mapsNativeQueryInstantsToUtcTimestamps() {
        CareSummaryProjection projection = mock(CareSummaryProjection.class);
        Instant wateredAt = Instant.parse("2026-11-17T13:30:36.827Z");
        Instant fertilizedAt = Instant.parse("2026-11-18T09:00:00Z");
        when(projection.getPlantId()).thenReturn(1L);
        when(projection.getTotalEvents()).thenReturn(2L);
        when(projection.getWateringEvents()).thenReturn(1L);
        when(projection.getFertilizingEvents()).thenReturn(1L);
        when(projection.getLastWateredAt()).thenReturn(wateredAt);
        when(projection.getLastFertilizedAt()).thenReturn(fertilizedAt);

        CareSummaryResponse response = CareSummaryResponse.from(projection);

        assertEquals(OffsetDateTime.ofInstant(wateredAt, ZoneOffset.UTC), response.lastWateredAt());
        assertEquals(OffsetDateTime.ofInstant(fertilizedAt, ZoneOffset.UTC), response.lastFertilizedAt());
    }
}
