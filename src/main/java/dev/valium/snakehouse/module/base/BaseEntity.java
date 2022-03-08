package dev.valium.snakehouse.module.base;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    private ZonedDateTime createdDate;
    private ZonedDateTime lastModifiedDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = ZonedDateTime.now();
        this.lastModifiedDate = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDate = ZonedDateTime.now();
    }
}