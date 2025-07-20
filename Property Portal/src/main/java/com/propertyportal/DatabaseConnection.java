package com.propertyportal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Database connection utility class for Property Portal
 */
public class DatabaseConnection {
    private static final String CONFIG_FILE = "database.properties";
    
    private static String url;
    private static String username;
    private static String password;
    
    static {
        loadDatabaseConfig();
    }
    
    /**
     * Load database configuration from properties file
     */
    private static void loadDatabaseConfig() {
        Properties props = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("database.properties not found, using default configuration");
                // Default configuration
                url = "jdbc:mysql://localhost:3306/propertyPortal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
                username = "root";
                password = "";
            } else {
                props.load(input);
                url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/propertyPortal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
                username = props.getProperty("db.username", "root");
                password = props.getProperty("db.password", "");
            }
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            // Fallback to default configuration
            url = "jdbc:mysql://localhost:3306/propertyPortal?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            username = "root";
            password = "";
        }
    }
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false); // Enable transaction management
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection and create database if needed
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        // First try to connect to MySQL server without specifying database
        String serverUrl = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        try (Connection connection = DriverManager.getConnection(serverUrl, username, password)) {
            // Create database if it doesn't exist
            try (var stmt = connection.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS propertyPortal");
            }
            return connection.isValid(5);
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Initialize database schema
     * @return true if schema creation successful, false otherwise
     */
    public static boolean initializeSchema() {
        try (Connection connection = getConnection()) {
            // Read and execute the SQL schema file
            String schemaSQL = readSchemaFile();
            if (schemaSQL != null) {
                String[] statements = schemaSQL.split(";");
                for (String statement : statements) {
                    statement = statement.trim();
                    if (!statement.isEmpty()) {
                        try (var stmt = connection.createStatement()) {
                            stmt.execute(statement);
                        }
                    }
                }
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Failed to initialize database schema: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Read schema file from resources
     * @return SQL schema content
     */
    private static String readSchemaFile() {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("PropertyPortal.sql")) {
            if (input == null) {
                System.err.println("PropertyPortal.sql not found in resources");
                return null;
            }
            return new String(input.readAllBytes());
        } catch (IOException e) {
            System.err.println("Error reading schema file: " + e.getMessage());
            return null;
        }
    }
} 