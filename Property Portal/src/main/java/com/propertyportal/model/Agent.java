package com.propertyportal.model;

import java.time.LocalDateTime;

/**
 * Agent entity class
 */
public class Agent {
    private int id;
    private int agencyId;
    private String name;
    private String email;
    private String phone;
    private boolean isActive;
    private int totalDealsClosed;
    private int avgResponseTimeMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public Agent() {}
    
    public Agent(int agencyId, String name, String email, String phone) {
        this.agencyId = agencyId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = true;
    }
    
    public Agent(int id, int agencyId, String name, String email, String phone, boolean isActive, 
                 int totalDealsClosed, int avgResponseTimeMinutes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.agencyId = agencyId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.totalDealsClosed = totalDealsClosed;
        this.avgResponseTimeMinutes = avgResponseTimeMinutes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getAgencyId() {
        return agencyId;
    }
    
    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public int getTotalDealsClosed() {
        return totalDealsClosed;
    }
    
    public void setTotalDealsClosed(int totalDealsClosed) {
        this.totalDealsClosed = totalDealsClosed;
    }
    
    public int getAvgResponseTimeMinutes() {
        return avgResponseTimeMinutes;
    }
    
    public void setAvgResponseTimeMinutes(int avgResponseTimeMinutes) {
        this.avgResponseTimeMinutes = avgResponseTimeMinutes;
    }
    
    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", agencyId=" + agencyId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                ", totalDealsClosed=" + totalDealsClosed +
                ", avgResponseTimeMinutes=" + avgResponseTimeMinutes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 