package com.propertyportal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Agent Performance entity class
 */
public class AgentPerformance {
    private int id;
    private int agentId;
    private LocalDate monthYear;
    private int totalInquiries;
    private int respondedInquiries;
    private int avgResponseTimeMinutes;
    private int closedDeals;
    private long totalRevenue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public AgentPerformance() {}
    
    public AgentPerformance(int agentId, LocalDate monthYear) {
        this.agentId = agentId;
        this.monthYear = monthYear;
        this.totalInquiries = 0;
        this.respondedInquiries = 0;
        this.avgResponseTimeMinutes = 0;
        this.closedDeals = 0;
        this.totalRevenue = 0;
    }
    
    public AgentPerformance(int id, int agentId, LocalDate monthYear, int totalInquiries, 
                           int respondedInquiries, int avgResponseTimeMinutes, int closedDeals, 
                           long totalRevenue, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.agentId = agentId;
        this.monthYear = monthYear;
        this.totalInquiries = totalInquiries;
        this.respondedInquiries = respondedInquiries;
        this.avgResponseTimeMinutes = avgResponseTimeMinutes;
        this.closedDeals = closedDeals;
        this.totalRevenue = totalRevenue;
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
    
    public LocalDate getMonthYear() {
        return monthYear;
    }
    
    public void setMonthYear(LocalDate monthYear) {
        this.monthYear = monthYear;
    }
    
    public int getTotalInquiries() {
        return totalInquiries;
    }
    
    public void setTotalInquiries(int totalInquiries) {
        this.totalInquiries = totalInquiries;
    }
    
    public int getRespondedInquiries() {
        return respondedInquiries;
    }
    
    public void setRespondedInquiries(int respondedInquiries) {
        this.respondedInquiries = respondedInquiries;
    }
    
    public int getAvgResponseTimeMinutes() {
        return avgResponseTimeMinutes;
    }
    
    public void setAvgResponseTimeMinutes(int avgResponseTimeMinutes) {
        this.avgResponseTimeMinutes = avgResponseTimeMinutes;
    }
    
    public int getClosedDeals() {
        return closedDeals;
    }
    
    public void setClosedDeals(int closedDeals) {
        this.closedDeals = closedDeals;
    }
    
    public long getTotalRevenue() {
        return totalRevenue;
    }
    
    public void setTotalRevenue(long totalRevenue) {
        this.totalRevenue = totalRevenue;
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
        return "AgentPerformance{" +
                "id=" + id +
                ", agentId=" + agentId +
                ", monthYear=" + monthYear +
                ", totalInquiries=" + totalInquiries +
                ", respondedInquiries=" + respondedInquiries +
                ", avgResponseTimeMinutes=" + avgResponseTimeMinutes +
                ", closedDeals=" + closedDeals +
                ", totalRevenue=" + totalRevenue +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 