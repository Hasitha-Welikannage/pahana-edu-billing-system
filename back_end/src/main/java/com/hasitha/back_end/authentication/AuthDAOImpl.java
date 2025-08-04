package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the {@link AuthDAO} interface for handling
 * authentication-related database operations.
 */
public class AuthDAOImpl implements AuthDAO {

    /**
     * Authenticates a user using the provided username and password.
     *
     * This method performs a database query to match the given credentials
     * against the records in the 'users' table.
     *
     * @param username the username provided by the user
     * @param password the plain-text password provided by the user
     * @return a {@link User} object if authentication is successful, or
     * {@code null} if credentials are invalid
     * @throws DatabaseException if a SQL or connection error occurs
     */
    @Override
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (
                // Establish connection and prepare SQL statement
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username); // Set username parameter
            ps.setString(2, password); // Set password parameter

            // Execute query and check if a matching user exists
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Map result to User object
                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("role")
                );
            }

            // No matching user found
            return null;

        } catch (SQLException ex) {
            // Wrap and throw custom exception for database errors
            throw new DatabaseException("An error occurred during user authentication. Please try again later. " + ex.getMessage());
        }
    }
}
