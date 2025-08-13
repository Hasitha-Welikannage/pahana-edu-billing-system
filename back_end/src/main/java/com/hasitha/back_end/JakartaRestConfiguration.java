package com.hasitha.back_end;

import com.hasitha.back_end.utils.DBConnection;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Configures Jakarta RESTful Web Services for the application.
 *
 * @author Juneau
 */
@ApplicationPath("api/v1")
public class JakartaRestConfiguration extends Application {

    public JakartaRestConfiguration() {
        System.out.println("🔄 Application starting – checking/creating database tables...");
        initDatabaseTables();
    }

    private void initDatabaseTables() {
        try (Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100) NOT NULL,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    role VARCHAR(50) NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS customers (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    first_name VARCHAR(100) NOT NULL,
                    last_name VARCHAR(100) NOT NULL,
                    phone VARCHAR(15) UNIQUE NOT NULL,
                    address VARCHAR(255)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS items (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    stock INT NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS bills (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    customer_id INT NOT NULL,
                    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    total DECIMAL(10,2) NOT NULL,                               
                    FOREIGN KEY (customer_id) REFERENCES customers(id),
                    FOREIGN KEY (user_id) REFERENCES users(id)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS bill_items (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    bill_id INT NOT NULL,
                    item_id INT NOT NULL,
                    quantity INT NOT NULL,
                    price DECIMAL(10,2) NOT NULL,
                    FOREIGN KEY (bill_id) REFERENCES bills(id),
                    FOREIGN KEY (item_id) REFERENCES items(id)
                )
            """);

            System.out.println("✅ Database tables ready.");

        } catch (SQLException e) {
            System.err.println("❌ Failed to initialize tables: " + e.getMessage());
        }
    }
}
