package com.propertyportal.dao;

import com.propertyportal.DatabaseConnection;
import com.propertyportal.model.Property;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Data Access Object for Property entity
 */
public class PropertyDAO {
    
    /**
     * Create a new property
     * @param property Property to create
     * @return Created property with ID
     */
    public Optional<Property> create(Property property) {
        String sql = "INSERT INTO Property (title, description, address, neighborhood, region, propertyType, " +
                    "listingDate, currentPrice, status, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, property.getTitle());
            stmt.setString(2, property.getDescription());
            stmt.setString(3, property.getAddress());
            stmt.setString(4, property.getNeighborhood());
            stmt.setString(5, property.getRegion());
            stmt.setString(6, property.getPropertyType().getValue());
            stmt.setTimestamp(7, Timestamp.valueOf(property.getListingDate()));
            stmt.setLong(8, property.getCurrentPrice());
            stmt.setString(9, property.getStatus().getValue());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        property.setId(rs.getInt(1));
                        connection.commit();
                        return Optional.of(property);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating property: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Find property by ID
     * @param id Property ID
     * @return Optional containing property if found
     */
    public Optional<Property> findById(int id) {
        String sql = "SELECT * FROM Property WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Property property = mapResultSetToProperty(rs);
                    return Optional.of(property);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding property by ID " + id + ": " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Find all properties
     * @return List of all properties
     */
    public List<Property> findAll() {
        String sql = "SELECT * FROM Property ORDER BY listingDate DESC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                properties.add(mapResultSetToProperty(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all properties: " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Find active listings
     * @return List of available properties
     */
    public List<Property> findActiveListings() {
        String sql = "SELECT * FROM Property WHERE status = 'available' ORDER BY listingDate DESC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                properties.add(mapResultSetToProperty(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding active listings: " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Find properties by neighborhood
     * @param neighborhood Neighborhood to search
     * @return List of properties in neighborhood
     */
    public List<Property> findByNeighborhood(String neighborhood) {
        String sql = "SELECT * FROM Property WHERE neighborhood = ? ORDER BY listingDate DESC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, neighborhood);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    properties.add(mapResultSetToProperty(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding properties by neighborhood '" + neighborhood + "': " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Find properties by property type
     * @param propertyType Property type to search
     * @return List of properties of specified type
     */
    public List<Property> findByPropertyType(Property.PropertyType propertyType) {
        String sql = "SELECT * FROM Property WHERE propertyType = ? ORDER BY listingDate DESC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, propertyType.getValue());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    properties.add(mapResultSetToProperty(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding properties by type '" + propertyType + "': " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Search properties by text (full-text search)
     * @param searchText Text to search for
     * @return List of matching properties
     */
    public List<Property> searchByText(String searchText) {
        String sql = "SELECT * FROM Property WHERE MATCH(title, description, neighborhood, region) AGAINST(? IN BOOLEAN MODE) " +
                    "ORDER BY listingDate DESC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, searchText);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    properties.add(mapResultSetToProperty(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching properties by text '" + searchText + "': " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Find properties by price range
     * @param minPrice Minimum price
     * @param maxPrice Maximum price
     * @return List of properties in price range
     */
    public List<Property> findByPriceRange(long minPrice, long maxPrice) {
        String sql = "SELECT * FROM Property WHERE currentPrice BETWEEN ? AND ? ORDER BY currentPrice ASC";
        List<Property> properties = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setLong(1, minPrice);
            stmt.setLong(2, maxPrice);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    properties.add(mapResultSetToProperty(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding properties by price range " + minPrice + " - " + maxPrice + ": " + e.getMessage());
        }
        return properties;
    }
    
    /**
     * Update property
     * @param property Property to update
     * @return true if update successful
     */
    public boolean update(Property property) {
        String sql = "UPDATE Property SET title = ?, description = ?, address = ?, neighborhood = ?, " +
                    "region = ?, propertyType = ?, currentPrice = ?, status = ?, updatedAt = NOW() " +
                    "WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, property.getTitle());
            stmt.setString(2, property.getDescription());
            stmt.setString(3, property.getAddress());
            stmt.setString(4, property.getNeighborhood());
            stmt.setString(5, property.getRegion());
            stmt.setString(6, property.getPropertyType().getValue());
            stmt.setLong(7, property.getCurrentPrice());
            stmt.setString(8, property.getStatus().getValue());
            stmt.setInt(9, property.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating property " + property.getId() + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update property status
     * @param propertyId Property ID
     * @param status New status
     * @return true if update successful
     */
    public boolean updateStatus(int propertyId, Property.PropertyStatus status) {
        String sql = "UPDATE Property SET status = ?, updatedAt = NOW() WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, status.getValue());
            stmt.setInt(2, propertyId);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating property status " + propertyId + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Update property price
     * @param propertyId Property ID
     * @param newPrice New price
     * @return true if update successful
     */
    public boolean updatePrice(int propertyId, long newPrice) {
        String sql = "UPDATE Property SET currentPrice = ?, updatedAt = NOW() WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setLong(1, newPrice);
            stmt.setInt(2, propertyId);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating property price " + propertyId + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete property by ID
     * @param id Property ID
     * @return true if deletion successful
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Property WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting property " + id + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get property statistics
     * @return Property statistics
     */
    public PropertyStatistics getStatistics() {
        String sql = "SELECT " +
                    "COUNT(*) as totalProperties, " +
                    "COUNT(CASE WHEN status = 'available' THEN 1 END) as availableProperties, " +
                    "COUNT(CASE WHEN status = 'under_offer' THEN 1 END) as underOfferProperties, " +
                    "COUNT(CASE WHEN status = 'sold' THEN 1 END) as soldProperties, " +
                    "AVG(currentPrice) as avgPrice, " +
                    "MIN(currentPrice) as minPrice, " +
                    "MAX(currentPrice) as maxPrice " +
                    "FROM Property";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return new PropertyStatistics(
                    rs.getInt("totalProperties"),
                    rs.getInt("availableProperties"),
                    rs.getInt("underOfferProperties"),
                    rs.getInt("soldProperties"),
                    rs.getLong("avgPrice"),
                    rs.getLong("minPrice"),
                    rs.getLong("maxPrice")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting property statistics: " + e.getMessage());
        }
        return new PropertyStatistics(0, 0, 0, 0, 0, 0, 0);
    }
    
    /**
     * Map ResultSet to Property object
     * @param rs ResultSet
     * @return Property object
     * @throws SQLException if mapping fails
     */
    private Property mapResultSetToProperty(ResultSet rs) throws SQLException {
        Property property = new Property();
        property.setId(rs.getInt("id"));
        property.setTitle(rs.getString("title"));
        property.setDescription(rs.getString("description"));
        property.setAddress(rs.getString("address"));
        property.setNeighborhood(rs.getString("neighborhood"));
        property.setRegion(rs.getString("region"));
        property.setPropertyType(Property.PropertyType.fromString(rs.getString("propertyType")));
        property.setCurrentPrice(rs.getLong("currentPrice"));
        property.setStatus(Property.PropertyStatus.fromString(rs.getString("status")));
        
        Timestamp listingDate = rs.getTimestamp("listingDate");
        if (listingDate != null) {
            property.setListingDate(listingDate.toLocalDateTime());
        }
        
        Timestamp soldDate = rs.getTimestamp("soldDate");
        if (soldDate != null) {
            property.setSoldDate(soldDate.toLocalDateTime());
        }
        
        Timestamp createdAt = rs.getTimestamp("createdAt");
        if (createdAt != null) {
            property.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updatedAt");
        if (updatedAt != null) {
            property.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return property;
    }
    
    /**
     * Property statistics class
     */
    public static class PropertyStatistics {
        private final int totalProperties;
        private final int availableProperties;
        private final int underOfferProperties;
        private final int soldProperties;
        private final long avgPrice;
        private final long minPrice;
        private final long maxPrice;
        
        public PropertyStatistics(int totalProperties, int availableProperties, int underOfferProperties, 
                                int soldProperties, long avgPrice, long minPrice, long maxPrice) {
            this.totalProperties = totalProperties;
            this.availableProperties = availableProperties;
            this.underOfferProperties = underOfferProperties;
            this.soldProperties = soldProperties;
            this.avgPrice = avgPrice;
            this.minPrice = minPrice;
            this.maxPrice = maxPrice;
        }
        
        public int getTotalProperties() { return totalProperties; }
        public int getAvailableProperties() { return availableProperties; }
        public int getUnderOfferProperties() { return underOfferProperties; }
        public int getSoldProperties() { return soldProperties; }
        public long getAvgPrice() { return avgPrice; }
        public long getMinPrice() { return minPrice; }
        public long getMaxPrice() { return maxPrice; }
        
        @Override
        public String toString() {
            return "PropertyStatistics{" +
                    "totalProperties=" + totalProperties +
                    ", availableProperties=" + availableProperties +
                    ", underOfferProperties=" + underOfferProperties +
                    ", soldProperties=" + soldProperties +
                    ", avgPrice=" + avgPrice +
                    ", minPrice=" + minPrice +
                    ", maxPrice=" + maxPrice +
                    '}';
        }
    }
} 