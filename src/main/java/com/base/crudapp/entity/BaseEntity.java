package com.base.crudapp.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private ID id;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "created_by")
    @NotNull(message = "Created by cannot be null")
    private Long createdBy;

    @Column(name = "created_on")
    @NotNull(message = "Created on cannot be null")
    private LocalDateTime createdOn;

    @Column(name = "last_updated_by")
    @NotNull(message = "Last updated cannot be null")
    private Long lastUpdatedBy;

    @Column(name = "last_updated_on")
    @NotNull(message = "Last updated cannot be null")
    private LocalDateTime lastUpdatedOn;

    @Column(name = "is_active")
    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
