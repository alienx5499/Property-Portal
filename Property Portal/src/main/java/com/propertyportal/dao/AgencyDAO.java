package com.propertyportal.dao;

import com.propertyportal.DatabaseConnection;
import com.propertyportal.model.Agency;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Data Access Object for Agency entity
 */
public class AgencyDAO {
    
    /**
     * Create a new agency
     * @param agency Agency to create
     * @return Created agency with ID
     */
    public Optional<Agency> create(Agency agency) {
        String sql = "INSERT INTO Agency (name, address, phone, createdAt, updatedAt) VALUES (?, ?, ?, NOW(), NOW())";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, agency.getName());
            stmt.setString(2, agency.getAddress());
            stmt.setString(3, agency.getPhone());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        agency.setId(rs.getInt(1));
                        connection.commit();
                        return Optional.of(agency);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating agency: " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Find agency by ID
     * @param id Agency ID
     * @return Optional containing agency if found
     */
    public Optional<Agency> findById(int id) {
        String sql = "SELECT * FROM Agency WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Agency agency = mapResultSetToAgency(rs);
                    return Optional.of(agency);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding agency by ID " + id + ": " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Find all agencies
     * @return List of all agencies
     */
    public List<Agency> findAll() {
        String sql = "SELECT * FROM Agency ORDER BY name";
        List<Agency> agencies = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                agencies.add(mapResultSetToAgency(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding all agencies: " + e.getMessage());
        }
        return agencies;
    }
    
    /**
     * Update agency
     * @param agency Agency to update
     * @return true if update successful
     */
    public boolean update(Agency agency) {
        String sql = "UPDATE Agency SET name = ?, address = ?, phone = ?, updatedAt = NOW() WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, agency.getName());
            stmt.setString(2, agency.getAddress());
            stmt.setString(3, agency.getPhone());
            stmt.setInt(4, agency.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating agency " + agency.getId() + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Delete agency by ID
     * @param id Agency ID
     * @return true if deletion successful
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM Agency WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting agency " + id + ": " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Find agency by name
     * @param name Agency name
     * @return Optional containing agency if found
     */
    public Optional<Agency> findByName(String name) {
        String sql = "SELECT * FROM Agency WHERE name = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Agency agency = mapResultSetToAgency(rs);
                    return Optional.of(agency);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding agency by name '" + name + "': " + e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Search agencies by partial name
     * @param name Partial name to search
     * @return List of matching agencies
     */
    public List<Agency> searchByName(String name) {
        String sql = "SELECT * FROM Agency WHERE name LIKE ? ORDER BY name";
        List<Agency> agencies = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    agencies.add(mapResultSetToAgency(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching agencies by name '" + name + "': " + e.getMessage());
        }
        return agencies;
    }
    
    /**
     * Get agency statistics
     * @return Agency statistics
     */
    public AgencyStatistics getStatistics() {
        String sql = "SELECT " +
                    "COUNT(*) as totalAgencies, " +
                    "COUNT(DISTINCT a.id) as totalAgents, " +
                    "COUNT(DISTINCT p.id) as totalProperties, " +
                    "COUNT(DISTINCT CASE WHEN p.status = 'sold' THEN p.id END) as soldProperties " +
                    "FROM Agency ag " +
                    "LEFT JOIN Agent a ON ag.id = a.agencyId " +
                    "LEFT JOIN propertyAgent pa ON a.id = pa.agentId " +
                    "LEFT JOIN Property p ON pa.propertyId = p.id";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return new AgencyStatistics(
                    rs.getInt("totalAgencies"),
                    rs.getInt("totalAgents"),
                    rs.getInt("totalProperties"),
                    rs.getInt("soldProperties")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting agency statistics: " + e.getMessage());
        }
        return new AgencyStatistics(0, 0, 0, 0);
    }
    
    /**
     * Map ResultSet to Agency object
     * @param rs ResultSet
     * @return Agency object
     * @throws SQLException if mapping fails
     */
    private Agency mapResultSetToAgency(ResultSet rs) throws SQLException {
        Agency agency = new Agency();
        agency.setId(rs.getInt("id"));
        agency.setName(rs.getString("name"));
        agency.setAddress(rs.getString("address"));
        agency.setPhone(rs.getString("phone"));
        
        Timestamp createdAt = rs.getTimestamp("createdAt");
        if (createdAt != null) {
            agency.setCreatedAt(createdAt.toLocalDateTime());
        }
        
        Timestamp updatedAt = rs.getTimestamp("updatedAt");
        if (updatedAt != null) {
            agency.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        
        return agency;
    }
    
    /**
     * Agency statistics class
     */
    public static class AgencyStatistics {
        private final int totalAgencies;
        private final int totalAgents;
        private final int totalProperties;
        private final int soldProperties;
        
        public AgencyStatistics(int totalAgencies, int totalAgents, int totalProperties, int soldProperties) {
            this.totalAgencies = totalAgencies;
            this.totalAgents = totalAgents;
            this.totalProperties = totalProperties;
            this.soldProperties = soldProperties;
        }
        
        public int getTotalAgencies() { return totalAgencies; }
        public int getTotalAgents() { return totalAgents; }
        public int getTotalProperties() { return totalProperties; }
        public int getSoldProperties() { return soldProperties; }
        
        @Override
        public String toString() {
            return "AgencyStatistics{" +
                    "totalAgencies=" + totalAgencies +
                    ", totalAgents=" + totalAgents +
                    ", totalProperties=" + totalProperties +
                    ", soldProperties=" + soldProperties +
                    '}';
        }
    }
} 