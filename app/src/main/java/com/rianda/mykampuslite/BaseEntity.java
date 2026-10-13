package com.rianda.mykampuslite;

import androidx.room.PrimaryKey;

public abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String createdAt;
    private String updatedAt;

    public BaseEntity() {
        this.createdAt = String.valueOf(System.currentTimeMillis());
        this.updatedAt = String.valueOf(System.currentTimeMillis());
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}