package com.example.StoreWide.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @Version
    private long version;
    private Timestamp created;
    private Timestamp updated;
    private Timestamp deleted;
}
