package com.propertyportal;

/**
 * Simple Property Portal Application
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Property Portal Database System ===");
        
        // Test database connection
        if (!DatabaseConnection.testConnection()) {
            System.err.println("Database connection failed. Check your database configuration.");
            return;
        }
        
        // Initialize database schema
        if (!DatabaseConnection.initializeSchema()) {
            System.err.println("Failed to initialize database schema.");
            return;
        }
        
        System.out.println("Database schema initialized successfully.");
        System.out.println("Property Portal system is ready for use.");
        System.out.println("=== System Ready ===");
    }
} 