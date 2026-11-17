package dev.tunascare.care.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {

    List<CareLog> findAllByPlantIdOrderByCaredAtDesc(Long plantId);

    @Query(value = """
            WITH filtered_logs AS (
                SELECT id, plant_id, care_type, cared_at
                FROM care_logs
                WHERE cared_at >= :from AND cared_at < :to
            ),
            ranked_logs AS (
                SELECT plant_id, care_type, cared_at,
                       ROW_NUMBER() OVER (
                           PARTITION BY plant_id, care_type
                           ORDER BY cared_at DESC, id DESC
                       ) AS recency_rank
                FROM filtered_logs
            )
            SELECT plant_id AS "plantId",
                   COUNT(*) AS "totalEvents",
                   COUNT(*) FILTER (WHERE care_type = 'WATER') AS "wateringEvents",
                   COUNT(*) FILTER (WHERE care_type = 'FERTILIZE') AS "fertilizingEvents",
                   MAX(cared_at) FILTER (
                       WHERE care_type = 'WATER' AND recency_rank = 1
                   ) AS "lastWateredAt",
                   MAX(cared_at) FILTER (
                       WHERE care_type = 'FERTILIZE' AND recency_rank = 1
                   ) AS "lastFertilizedAt"
            FROM ranked_logs
            GROUP BY plant_id
            ORDER BY "totalEvents" DESC, plant_id ASC
            """, nativeQuery = true)
    List<CareSummaryProjection> summarize(
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to
    );
}
