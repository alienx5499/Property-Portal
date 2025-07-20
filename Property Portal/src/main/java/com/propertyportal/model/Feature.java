package com.propertyportal.model;

import java.time.LocalDateTime;

/**
 * Feature entity class
 */
public class Feature {
    private int id;
    private String name;
    private FeatureCategory category;
    private String description;
    private LocalDateTime createdAt;
    
    // Enums
    public enum FeatureCategory {
        INDOOR("Indoor"),
        OUTDOOR("Outdoor"),
        AMENITY("Amenity");
        
        private final String value;
        
        FeatureCategory(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static FeatureCategory fromString(String text) {
            for (FeatureCategory category : FeatureCategory.values()) {
                if (category.value.equalsIgnoreCase(text)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }
    
    // Constructors
    public Feature() {}
    
    public Feature(String name, FeatureCategory category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }
    
    public Feature(int id, String name, FeatureCategory category, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public FeatureCategory getCategory() {
        return category;
    }
    
    public void setCategory(FeatureCategory category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Feature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
} 