package dev.tunascare.care.log;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record CareLogRequest(
        @NotNull Long plantId,
        @NotNull CareType type,
        @Size(max = 500) String notes,
        @PastOrPresent OffsetDateTime caredAt
) {
}
