package org.smartinventory.model;

import java.time.LocalDateTime;

public abstract class BaseEntity {
    protected int id;
    protected LocalDateTime createdDate;

    public BaseEntity(int id) {
        this.id = id;
        this.createdDate = LocalDateTime.now();
    }

    public int getId() { return id; }
    public LocalDateTime getCreatedDate() { return createdDate; }
}
