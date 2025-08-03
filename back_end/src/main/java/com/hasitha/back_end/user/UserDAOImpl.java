package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object implementation for User entities. Handles all database
 * operations related to user management. Uses JDBC for direct database
 * interaction.
 *
 * Throws {@link DatabaseException} for any database access related errors.
 */
public class UserDAOImpl implements UserDAO {

    /**
     * Retrieves all users from the database.
     *
     * @return List of all users
     * @throws DatabaseException if a database access error occurs
     */
    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("role")));
            }
            return list;
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching all users. " + ex);
        }
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param id the user ID to search for
     * @return User if found, or null if no user with the given ID exists
     * @throws DatabaseException if a database access error occurs
     */
    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("role"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching user with ID " + id + ". " + ex);
        }
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the User object to create (must not be null)
     * @return the created User object including generated ID
     * @throws DatabaseException if creation fails or a database access error
     * occurs
     * @throws IllegalArgumentException if the user parameter is null or invalid
     */
    @Override
    public User create(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DatabaseException("Creating user failed, no rows affected");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new DatabaseException("Creating user failed, no ID obtained.");
            }

            // Return user without password for security
            return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getRole());
        } catch (SQLException ex) {
            throw new DatabaseException("Error creating user. " + ex);
        }
    }

    /**
     * Updates an existing user in the database by their ID.
     *
     * @param id the ID of the user to update
     * @param user the User object containing updated data
     * @return the updated User object retrieved from the database
     * @throws DatabaseException if update fails or a database access error
     * occurs
     */
    @Override
    public User update(int id, User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setInt(6, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Updating user failed, no rows affected for ID: " + id);
            }

            return findById(id); // Return the updated user
        } catch (SQLException ex) {
            throw new DatabaseException("Error updating user with ID " + id + ". " + ex);
        }
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id the ID of the user to delete
     * @throws DatabaseException if deletion fails or a database access error
     * occurs
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Deleting user failed, no rows affected for ID: " + id);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting user with ID " + id + ". " + ex);
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return User if found, or null if no user with the given username exists
     * @throws DatabaseException if a database access error occurs
     */
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username); // plain text password (for assignment only)

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("role"));
            }
            return null;
        } catch (SQLException ex) {
            throw new DatabaseException("Error finding user by username: " + username + ". " + ex);
        }
    }

    /**
     * Retrieves only the password of the user by their ID.
     *
     * @param id the user ID
     * @return the password string or null if user not found
     * @throws DatabaseException if a database error occurs
     */
    @Override
    public String findPasswordById(int id) {
        String sql = "SELECT password FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
                return null; // user not found
            }

        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching password for user ID " + id + ". " + ex);
        }
    }

    @Override
    public void updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DatabaseException("Password update failed, no rows affected for user ID: " + userId);
            }

        } catch (SQLException ex) {
            throw new DatabaseException("Error updating password for user ID " + userId + ". " + ex);
        }
    }

}
