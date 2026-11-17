package dev.tunascare.care.log;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "care_logs")
public class CareLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long plantId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CareType careType;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false)
    private OffsetDateTime caredAt;

    protected CareLog() {
    }

    public CareLog(Long plantId, CareType careType, String notes, OffsetDateTime caredAt) {
        this.plantId = plantId;
        this.careType = careType;
        this.notes = notes;
        this.caredAt = caredAt;
    }

    @PrePersist
    void setCaredAtIfMissing() {
        if (caredAt == null) {
            caredAt = OffsetDateTime.now(ZoneOffset.UTC);
        }
    }

    public Long getId() {
        return id;
    }

    public Long getPlantId() {
        return plantId;
    }

    public CareType getCareType() {
        return careType;
    }

    public String getNotes() {
        return notes;
    }

    public OffsetDateTime getCaredAt() {
        return caredAt;
    }
}
