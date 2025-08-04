package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ItemDAO} for managing item records in the
 * database.This DAO handles basic CRUD operations for items using JDBC.
 *
 * It relies on a utility class (DBConnection) to establish database
 * connections.
 *
 * All SQL exceptions are wrapped in a custom {@link DatabaseException}.
 */
public class ItemDAOImpl implements ItemDAO {

    /**
     * Retrieves all items from the database.
     *
     * @return a list of all items
     * @throws DatabaseException if any SQL error occurs
     */
    @Override
    public List<Item> findAll() {
        String sql = "SELECT * FROM items";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();
            List<Item> list = new ArrayList<>();
            // Map each row in result set to an Item object
            while (rs.next()) {
                list.add(new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
            return list;
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching all items. " + ex.getMessage());
        }
    }

    /**
     * Retrieves a single item by its ID.
     *
     * @param id the item ID
     * @return the matching item, or null if not found
     * @throws DatabaseException if a SQL error occurs
     */
    @Override
    public Item findById(int id) {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching item with ID " + id + ". " + ex.getMessage());
        }
    }

    /**
     * Inserts a new item into the database.
     *
     * @param item the item to insert (without an ID)
     * @return the inserted item with its generated ID
     * @throws DatabaseException if the insert fails or ID is not returned
     */
    @Override
    public Item create(Item item) {
        String sql = "INSERT INTO items (name, price, stock) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getStock());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Creating item failed, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                item.setId(generatedKeys.getInt(1));
                return item;
            } else {
                throw new DatabaseException("Creating item failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error creating item. " + ex.getMessage());
        }
    }

    /**
     * Updates an existing item by its ID.
     *
     * @param id the ID of the item to update
     * @param item the updated item data
     * @return the updated item, or null if no affectedRows were affected
     * @throws DatabaseException if a SQL error occurs
     */
    @Override
    public Item update(int id, Item item) {
        String sql = "UPDATE items SET name=?, price=?, stock=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getStock());
            ps.setInt(4, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Updating item failed, no rows affected for ID: " + id);
            }
            return findById(id);
        } catch (SQLException ex) {
            throw new DatabaseException("Error updating item with ID " + id + ". " + ex.getMessage());
        }
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to delete
     * @throws DatabaseException if a SQL error occurs
     */
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM items WHERE id=?";

        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Deleting item failed, no rows affected for ID: " + id);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting item with ID " + id + ". " + ex.getMessage());
        }
    }
}
