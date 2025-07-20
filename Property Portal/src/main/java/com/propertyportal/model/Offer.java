package com.propertyportal.model;

import java.time.LocalDateTime;

/**
 * Offer entity class
 */
public class Offer {
    private int id;
    private int agentId;
    private int buyerId;
    private int propertyId;
    private long offerAmount;
    private LocalDateTime offerDate;
    private OfferStatus status;
    private LocalDateTime responseDate;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Enums
    public enum OfferStatus {
        PENDING("pending"),
        ACCEPTED("accepted"),
        REJECTED("rejected"),
        WITHDRAWN("withdrawn");
        
        private final String value;
        
        OfferStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static OfferStatus fromString(String text) {
            for (OfferStatus status : OfferStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    // Constructors
    public Offer() {}
    
    public Offer(int agentId, int buyerId, int propertyId, long offerAmount) {
        this.agentId = agentId;
        this.buyerId = buyerId;
        this.propertyId = propertyId;
        this.offerAmount = offerAmount;
        this.status = OfferStatus.PENDING;
        this.offerDate = LocalDateTime.now();
    }
    
    public Offer(int id, int agentId, int buyerId, int propertyId, long offerAmount, 
                 LocalDateTime offerDate, OfferStatus status, LocalDateTime responseDate, 
                 String notes, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.agentId = agentId;
        this.buyerId = buyerId;
        this.propertyId = propertyId;
        this.offerAmount = offerAmount;
        this.offerDate = offerDate;
        this.status = status;
        this.responseDate = responseDate;
        this.notes = notes;
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
    
    public int getAgentId() {
        return agentId;
    }
    
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
    
    public int getBuyerId() {
        return buyerId;
    }
    
    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }
    
    public int getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }
    
    public long getOfferAmount() {
        return offerAmount;
    }
    
    public void setOfferAmount(long offerAmount) {
        this.offerAmount = offerAmount;
    }
    
    public LocalDateTime getOfferDate() {
        return offerDate;
    }
    
    public void setOfferDate(LocalDateTime offerDate) {
        this.offerDate = offerDate;
    }
    
    public OfferStatus getStatus() {
        return status;
    }
    
    public void setStatus(OfferStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getResponseDate() {
        return responseDate;
    }
    
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
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
    
    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", agentId=" + agentId +
                ", buyerId=" + buyerId +
                ", propertyId=" + propertyId +
                ", offerAmount=" + offerAmount +
                ", offerDate=" + offerDate +
                ", status=" + status +
                ", responseDate=" + responseDate +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 