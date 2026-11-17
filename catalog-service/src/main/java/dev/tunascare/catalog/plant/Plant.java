package dev.tunascare.catalog.plant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(length = 80)
    private String species;

    @Column(length = 120)
    private String location;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    protected Plant() {
    }

    public Plant(String name, String species, String location) {
        this.name = name;
        this.species = species;
        this.location = location;
    }

    @PrePersist
    void setCreatedAt() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getLocation() {
        return location;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
