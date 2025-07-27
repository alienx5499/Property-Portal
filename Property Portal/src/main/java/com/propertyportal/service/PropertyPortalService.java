package com.propertyportal.service;

import com.propertyportal.dao.AgencyDAO;
import com.propertyportal.dao.PropertyDAO;
import com.propertyportal.model.Agency;
import com.propertyportal.model.Property;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Main service class for Property Portal business logic
 */
public class PropertyPortalService {
    
    private final AgencyDAO agencyDAO;
    private final PropertyDAO propertyDAO;
    
    public PropertyPortalService() {
        this.agencyDAO = new AgencyDAO();
        this.propertyDAO = new PropertyDAO();
    }
    
    // ==================== AGENCY MANAGEMENT ====================
    
    /**
     * Register a new agency
     * @param name Agency name
     * @param address Agency address
     * @param phone Agency phone
     * @return Created agency
     */
    public Optional<Agency> registerAgency(String name, String address, String phone) {
        Agency agency = new Agency(name, address, phone);
        return agencyDAO.create(agency);
    }
    
    /**
     * Get all agencies
     * @return List of all agencies
     */
    public List<Agency> getAllAgencies() {
        return agencyDAO.findAll();
    }
    
    /**
     * Get agency by ID
     * @param id Agency ID
     * @return Agency if found
     */
    public Optional<Agency> getAgencyById(int id) {
        return agencyDAO.findById(id);
    }
    
    /**
     * Search agencies by name
     * @param name Partial name to search
     * @return List of matching agencies
     */
    public List<Agency> searchAgenciesByName(String name) {
        return agencyDAO.searchByName(name);
    }
    
    /**
     * Update agency information
     * @param agency Agency to update
     * @return true if update successful
     */
    public boolean updateAgency(Agency agency) {
        return agencyDAO.update(agency);
    }
    
    /**
     * Delete agency
     * @param id Agency ID
     * @return true if deletion successful
     */
    public boolean deleteAgency(int id) {
        return agencyDAO.delete(id);
    }
    
    // ==================== PROPERTY MANAGEMENT ====================
    
    /**
     * List a new property
     * @param title Property title
     * @param description Property description
     * @param address Property address
     * @param neighborhood Property neighborhood
     * @param region Property region
     * @param propertyType Property type
     * @param currentPrice Property price
     * @return Created property
     */
    public Optional<Property> listProperty(String title, String description, String address, 
                                         String neighborhood, String region, Property.PropertyType propertyType, 
                                         long currentPrice) {
        Property property = new Property(title, description, address, neighborhood, region, propertyType, currentPrice);
        return propertyDAO.create(property);
    }
    
    /**
     * Get all properties
     * @return List of all properties
     */
    public List<Property> getAllProperties() {
        return propertyDAO.findAll();
    }
    
    /**
     * Get active listings
     * @return List of available properties
     */
    public List<Property> getActiveListings() {
        return propertyDAO.findActiveListings();
    }
    
    /**
     * Get property by ID
     * @param id Property ID
     * @return Property if found
     */
    public Optional<Property> getPropertyById(int id) {
        return propertyDAO.findById(id);
    }
    
    /**
     * Find properties by neighborhood
     * @param neighborhood Neighborhood to search
     * @return List of properties in neighborhood
     */
    public List<Property> getPropertiesByNeighborhood(String neighborhood) {
        return propertyDAO.findByNeighborhood(neighborhood);
    }
    
    /**
     * Find properties by property type
     * @param propertyType Property type to search
     * @return List of properties of specified type
     */
    public List<Property> getPropertiesByType(Property.PropertyType propertyType) {
        return propertyDAO.findByPropertyType(propertyType);
    }
    
    /**
     * Search properties by text (full-text search)
     * @param searchText Text to search for
     * @return List of matching properties
     */
    public List<Property> searchProperties(String searchText) {
        return propertyDAO.searchByText(searchText);
    }
    
    /**
     * Find properties by price range
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of properties in price range
     */
    public List<Property> getPropertiesByPriceRange(long minPrice, long maxPrice) {
        return propertyDAO.findByPriceRange(minPrice, maxPrice);
    }
    
    /**
     * Update property information
     * @param property Property to update
     * @return true if update successful
     */
    public boolean updateProperty(Property property) {
        return propertyDAO.update(property);
    }
    
    /**
     * Update property status
     * @param propertyId Property ID
     * @param status New status
     * @return true if update successful
     */
    public boolean updatePropertyStatus(int propertyId, Property.PropertyStatus status) {
        return propertyDAO.updateStatus(propertyId, status);
    }
    
    /**
     * Update property price
     * @param propertyId Property ID
     * @param newPrice New price
     * @return true if update successful
     */
    public boolean updatePropertyPrice(int propertyId, long newPrice) {
        return propertyDAO.updatePrice(propertyId, newPrice);
    }
    
    /**
     * Mark property as sold
     * @param propertyId Property ID
     * @return true if update successful
     */
    public boolean markPropertyAsSold(int propertyId) {
        return propertyDAO.updateStatus(propertyId, Property.PropertyStatus.SOLD);
    }
    
    /**
     * Delete property
     * @param id Property ID
     * @return true if deletion successful
     */
    public boolean deleteProperty(int id) {
        return propertyDAO.delete(id);
    }
    
    // ==================== BUSINESS INTELLIGENCE ====================
    
    /**
     * Get active listings by neighborhood and property type
     * @param neighborhood Neighborhood filter (optional)
     * @param propertyType Property type filter (optional)
     * @return List of matching properties
     */
    public List<Property> getActiveListingsByFilters(String neighborhood, Property.PropertyType propertyType) {
        List<Property> activeListings = propertyDAO.findActiveListings();
        
        // Apply neighborhood filter
        if (neighborhood != null && !neighborhood.trim().isEmpty()) {
            activeListings = activeListings.stream()
                    .filter(p -> p.getNeighborhood().equalsIgnoreCase(neighborhood))
                    .toList();
        }
        
        // Apply property type filter
        if (propertyType != null) {
            activeListings = activeListings.stream()
                    .filter(p -> p.getPropertyType() == propertyType)
                    .toList();
        }
        
        return activeListings;
    }
    
    /**
     * Get agent performance statistics
     * @return Agency statistics
     */
    public AgencyDAO.AgencyStatistics getAgentPerformance() {
        return agencyDAO.getStatistics();
    }
    
    /**
     * Get average time on market per listing
     * @return Average days on market
     */
    public double getAverageTimeOnMarket() {
        List<Property> soldProperties = propertyDAO.findAll().stream()
                .filter(p -> p.getStatus() == Property.PropertyStatus.SOLD && p.getSoldDate() != null)
                .toList();
        
        if (soldProperties.isEmpty()) {
            return 0.0;
        }
        
        long totalDays = soldProperties.stream()
                .mapToLong(p -> java.time.Duration.between(p.getListingDate(), p.getSoldDate()).toDays())
                .sum();
        
        return (double) totalDays / soldProperties.size();
    }
    
    /**
     * Get price trend analysis for a region
     * @param region Region to analyze
     * @return Price statistics for region
     */
    public PropertyDAO.PropertyStatistics getPriceTrendAnalysis(String region) {
        List<Property> regionProperties = propertyDAO.findAll().stream()
                .filter(p -> p.getRegion().equalsIgnoreCase(region))
                .toList();
        
        if (regionProperties.isEmpty()) {
            return new PropertyDAO.PropertyStatistics(0, 0, 0, 0, 0, 0, 0);
        }
        
        long totalPrice = regionProperties.stream().mapToLong(Property::getCurrentPrice).sum();
        long avgPrice = totalPrice / regionProperties.size();
        long minPrice = regionProperties.stream().mapToLong(Property::getCurrentPrice).min().orElse(0);
        long maxPrice = regionProperties.stream().mapToLong(Property::getCurrentPrice).max().orElse(0);
        
        int available = (int) regionProperties.stream().filter(p -> p.getStatus() == Property.PropertyStatus.AVAILABLE).count();
        int underOffer = (int) regionProperties.stream().filter(p -> p.getStatus() == Property.PropertyStatus.UNDER_OFFER).count();
        int sold = (int) regionProperties.stream().filter(p -> p.getStatus() == Property.PropertyStatus.SOLD).count();
        
        return new PropertyDAO.PropertyStatistics(regionProperties.size(), available, underOffer, sold, avgPrice, minPrice, maxPrice);
    }
    
    /**
     * Get property statistics
     * @return Property statistics
     */
    public PropertyDAO.PropertyStatistics getPropertyStatistics() {
        return propertyDAO.getStatistics();
    }
    
    // ==================== UTILITY METHODS ====================
    
    /**
     * Get all property types
     * @return Array of property types
     */
    public Property.PropertyType[] getAllPropertyTypes() {
        return Property.PropertyType.values();
    }
    
    /**
     * Get all property statuses
     * @return Array of property statuses
     */
    public Property.PropertyStatus[] getAllPropertyStatuses() {
        return Property.PropertyStatus.values();
    }
    
    /**
     * Validate property data
     * @param property Property to validate
     * @return true if valid
     */
    public boolean validateProperty(Property property) {
        if (property.getTitle() == null || property.getTitle().trim().isEmpty()) {
            return false;
        }
        
        if (property.getAddress() == null || property.getAddress().trim().isEmpty()) {
            return false;
        }
        
        if (property.getCurrentPrice() <= 0) {
            return false;
        }
        
        if (property.getPropertyType() == null) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate agency data
     * @param agency Agency to validate
     * @return true if valid
     */
    public boolean validateAgency(Agency agency) {
        if (agency.getName() == null || agency.getName().trim().isEmpty()) {
            return false;
        }
        
        if (agency.getAddress() == null || agency.getAddress().trim().isEmpty()) {
            return false;
        }
        
        if (agency.getPhone() == null || agency.getPhone().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }
} 