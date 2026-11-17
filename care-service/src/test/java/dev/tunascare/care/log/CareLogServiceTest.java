package dev.tunascare.care.log;

import dev.tunascare.care.plant.PlantClient;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CareLogServiceTest {

    @Test
    void summarizeRejectsRangeWhereEndIsNotAfterStart() {
        CareLogService service = new CareLogService(mock(CareLogRepository.class), mock(PlantClient.class));
        OffsetDateTime from = OffsetDateTime.parse("2026-11-17T00:00:00Z");
        OffsetDateTime to = from;

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.summarize(from, to)
        );

        assertEquals(400, exception.getStatusCode().value());
    }
}
