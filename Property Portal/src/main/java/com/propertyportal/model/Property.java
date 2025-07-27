package com.propertyportal.model;

import java.time.LocalDateTime;

/**
 * Property entity class
 */
public class Property {
    private int id;
    private String title;
    private String description;
    private String address;
    private String neighborhood;
    private String region;
    private PropertyType propertyType;
    private LocalDateTime listingDate;
    private long currentPrice;
    private PropertyStatus status;
    private LocalDateTime soldDate;
    private int daysOnMarket;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Enums
    public enum PropertyType {
        APARTMENT("apartment"),
        HOUSE("house"),
        CONDO("condo"),
        TOWNHOUSE("townhouse"),
        LAND("land"),
        COMMERCIAL("commercial");
        
        private final String value;
        
        PropertyType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static PropertyType fromString(String text) {
            for (PropertyType type : PropertyType.values()) {
                if (type.value.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    public enum PropertyStatus {
        AVAILABLE("available"),
        UNDER_OFFER("under_offer"),
        SOLD("sold");
        
        private final String value;
        
        PropertyStatus(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static PropertyStatus fromString(String text) {
            for (PropertyStatus status : PropertyStatus.values()) {
                if (status.value.equalsIgnoreCase(text)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    // Constructors
    public Property() {}
    
    public Property(String title, String description, String address, String neighborhood, 
                   String region, PropertyType propertyType, long currentPrice) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.neighborhood = neighborhood;
        this.region = region;
        this.propertyType = propertyType;
        this.currentPrice = currentPrice;
        this.status = PropertyStatus.AVAILABLE;
        this.listingDate = LocalDateTime.now();
    }
    
    public Property(int id, String title, String description, String address, String neighborhood,
                   String region, PropertyType propertyType, LocalDateTime listingDate, long currentPrice,
                   PropertyStatus status, LocalDateTime soldDate, int daysOnMarket, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.neighborhood = neighborhood;
        this.region = region;
        this.propertyType = propertyType;
        this.listingDate = listingDate;
        this.currentPrice = currentPrice;
        this.status = status;
        this.soldDate = soldDate;
        this.daysOnMarket = daysOnMarket;
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getNeighborhood() {
        return neighborhood;
    }
    
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public PropertyType getPropertyType() {
        return propertyType;
    }
    
    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
    
    public LocalDateTime getListingDate() {
        return listingDate;
    }
    
    public void setListingDate(LocalDateTime listingDate) {
        this.listingDate = listingDate;
    }
    
    public long getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public PropertyStatus getStatus() {
        return status;
    }
    
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getSoldDate() {
        return soldDate;
    }
    
    public void setSoldDate(LocalDateTime soldDate) {
        this.soldDate = soldDate;
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
    
    public int getDaysOnMarket() {
        return daysOnMarket;
    }
    
    public void setDaysOnMarket(int daysOnMarket) {
        this.daysOnMarket = daysOnMarket;
    }
    
    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", region='" + region + '\'' +
                ", propertyType=" + propertyType +
                ", listingDate=" + listingDate +
                ", currentPrice=" + currentPrice +
                ", status=" + status +
                ", soldDate=" + soldDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 