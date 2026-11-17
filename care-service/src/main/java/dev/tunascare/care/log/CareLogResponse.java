package dev.tunascare.care.log;

import java.time.OffsetDateTime;

public record CareLogResponse(
        Long id,
        Long plantId,
        CareType type,
        String notes,
        OffsetDateTime caredAt
) {

    public static CareLogResponse from(CareLog log) {
        return new CareLogResponse(
                log.getId(),
                log.getPlantId(),
                log.getCareType(),
                log.getNotes(),
                log.getCaredAt()
        );
    }
}
