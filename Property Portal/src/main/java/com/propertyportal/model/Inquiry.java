package com.propertyportal.model;

import java.time.LocalDateTime;

/**
 * Inquiry entity class
 */
public class Inquiry {
    private int id;
    private LocalDateTime createdAt;
    private String message;
    private InquiryStatus status;
    private int agentId;
    private int buyerId;
    private int propertyId;
    private LocalDateTime respondedAt;
    private LocalDateTime closedAt;
    private Integer responseTimeMinutes;
    private InquiryType inquiryType;
    private InquiryPriority priority;
    private LocalDateTime updatedAt;
    
    // Enums
    public enum InquiryStatus {
        NEW("new"),
        RESPONDED("responded"),
        CLOSED("closed");
        
        private final String value;
        
        InquiryStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static InquiryStatus fromString(String text) {
            for (InquiryStatus status : InquiryStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    public enum InquiryType {
        GENERAL("general"),
        VIEWING("viewing"),
        OFFER("offer"),
        QUESTION("question");
        
        private final String value;
        
        InquiryType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static InquiryType fromString(String text) {
            for (InquiryType type : InquiryType.values()) {
                if (type.value.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    public enum InquiryPriority {
        LOW("low"),
        MEDIUM("medium"),
        HIGH("high");
        
        private final String value;
        
        InquiryPriority(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static InquiryPriority fromString(String text) {
            for (InquiryPriority priority : InquiryPriority.values()) {
                if (priority.value.equalsIgnoreCase(text)) {
                    return priority;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    // Constructors
    public Inquiry() {}
    
    public Inquiry(String message, int agentId, int buyerId, int propertyId) {
        this.message = message;
        this.agentId = agentId;
        this.buyerId = buyerId;
        this.propertyId = propertyId;
        this.status = InquiryStatus.NEW;
        this.inquiryType = InquiryType.GENERAL;
        this.priority = InquiryPriority.MEDIUM;
        this.createdAt = LocalDateTime.now();
    }
    
    public Inquiry(int id, LocalDateTime createdAt, String message, InquiryStatus status, 
                   int agentId, int buyerId, int propertyId, LocalDateTime respondedAt, 
                   LocalDateTime closedAt, Integer responseTimeMinutes, InquiryType inquiryType, 
                   InquiryPriority priority, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.message = message;
        this.status = status;
        this.agentId = agentId;
        this.buyerId = buyerId;
        this.propertyId = propertyId;
        this.respondedAt = respondedAt;
        this.closedAt = closedAt;
        this.responseTimeMinutes = responseTimeMinutes;
        this.inquiryType = inquiryType;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public InquiryStatus getStatus() {
        return status;
    }
    
    public void setStatus(InquiryStatus status) {
        this.status = status;
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
    
    public LocalDateTime getRespondedAt() {
        return respondedAt;
    }
    
    public void setRespondedAt(LocalDateTime respondedAt) {
        this.respondedAt = respondedAt;
    }
    
    public LocalDateTime getClosedAt() {
        return closedAt;
    }
    
    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }
    
    public Integer getResponseTimeMinutes() {
        return responseTimeMinutes;
    }
    
    public void setResponseTimeMinutes(Integer responseTimeMinutes) {
        this.responseTimeMinutes = responseTimeMinutes;
    }
    
    public InquiryType getInquiryType() {
        return inquiryType;
    }
    
    public void setInquiryType(InquiryType inquiryType) {
        this.inquiryType = inquiryType;
    }
    
    public InquiryPriority getPriority() {
        return priority;
    }
    
    public void setPriority(InquiryPriority priority) {
        this.priority = priority;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Inquiry{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", agentId=" + agentId +
                ", buyerId=" + buyerId +
                ", propertyId=" + propertyId +
                ", respondedAt=" + respondedAt +
                ", closedAt=" + closedAt +
                ", responseTimeMinutes=" + responseTimeMinutes +
                ", inquiryType=" + inquiryType +
                ", priority=" + priority +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 