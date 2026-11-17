package dev.tunascare.care.log;

import dev.tunascare.care.plant.PlantClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Transactional
public class CareLogService {

    private final CareLogRepository careLogRepository;
    private final PlantClient plantClient;

    public CareLogService(CareLogRepository careLogRepository, PlantClient plantClient) {
        this.careLogRepository = careLogRepository;
        this.plantClient = plantClient;
    }

    public CareLogResponse create(CareLogRequest request) {
        plantClient.verifyPlantExists(request.plantId());
        CareLog log = new CareLog(
                request.plantId(),
                request.type(),
                request.notes(),
                request.caredAt()
        );
        return CareLogResponse.from(careLogRepository.save(log));
    }

    @Transactional(readOnly = true)
    public List<CareLogResponse> findByPlantId(Long plantId) {
        return careLogRepository.findAllByPlantIdOrderByCaredAtDesc(plantId)
                .stream()
                .map(CareLogResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CareSummaryResponse> summarize(OffsetDateTime from, OffsetDateTime to) {
        if (!to.isAfter(from)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "'to' must be later than 'from'");
        }
        return careLogRepository.summarize(from, to)
                .stream()
                .map(CareSummaryResponse::from)
                .toList();
    }
}
