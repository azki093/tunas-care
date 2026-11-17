package dev.tunascare.care.log;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CareLogController {

    private final CareLogService careLogService;

    public CareLogController(CareLogService careLogService) {
        this.careLogService = careLogService;
    }

    @PostMapping("/care-logs")
    @ResponseStatus(HttpStatus.CREATED)
    public CareLogResponse create(@Valid @RequestBody CareLogRequest request) {
        return careLogService.create(request);
    }

    @GetMapping("/plants/{plantId}/care-logs")
    public List<CareLogResponse> findByPlantId(@PathVariable Long plantId) {
        return careLogService.findByPlantId(plantId);
    }

    @GetMapping("/care-logs/summary")
    public List<CareSummaryResponse> summarize(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to
    ) {
        return careLogService.summarize(from, to);
    }
}
