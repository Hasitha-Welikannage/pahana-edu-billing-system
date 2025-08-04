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
 * It relies on a
 utility class (DBConnection) to establish database connections.

 All SQL exceptions are wrapped in a custom {@link DatabaseException}.
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

        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching items", e);
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

        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching item", e);
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

        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getUnitPrice());
            ps.setInt(3, item.getStock());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                item.setId(keys.getInt(1));
                return item;
            }

            throw new DatabaseException("Item creation failed: no ID returned.");
        } catch (SQLException e) {
            throw new DatabaseException("Error creating item", e);
        }
    }

    /**
     * Updates an existing item by its ID.
     *
     * @param id the ID of the item to update
     * @param item the updated item data
     * @return the updated item, or null if no rows were affected
     * @throws DatabaseException if a SQL error occurs
     */
    @Override
    public Item update(int id, Item item) {
        String sql = "UPDATE items SET name=?, price=?, stock=? WHERE id=?";

        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getUnitPrice());
            ps.setInt(3, item.getStock());
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                item.setId(id);
                return item;
            }

            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error updating item", e);
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

        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting item", e);
        }
    }
}
