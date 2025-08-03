package com.hasitha.back_end.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections.
 *
 * This class loads database credentials from a properties file and provides a
 * method to get a JDBC Connection object.
 *
 * The expected database.properties file should include:
 * db.url=jdbc:mysql://localhost:3306/your_database db.user=your_username
 * db.password=your_password
 *
 * Usage example: try (Connection conn = DBConnection.getConnection()) { // Use
 * the connection } catch (SQLException e) { e.printStackTrace(); }
 *
 * This class is not meant to be instantiated.
 */
public class DBConnection {

    // Properties loaded from the configuration file
    private static final Properties properties = new Properties();

    // Database connection details
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    // Static block runs once when the class is first loaded
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Load configuration from properties file
            loadProperties();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    /**
     * Loads database configuration values from the database.properties file.
     *
     * The file must be located in the classpath. Required keys are: db.url
     * db.user db.password
     *
     * Throws RuntimeException if the file is missing or cannot be read.
     */
    private static void loadProperties() {
        try (InputStream input = DBConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("Unable to find database.properties");
            }

            // Load key-value pairs into the Properties object
            properties.load(input);

            // Extract properties into variables
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties", e);
        }
    }

    /**
     * Returns a new database connection using the loaded properties.
     *
     * @return a JDBC Connection to the configured database
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
