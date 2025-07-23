/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class ItemDAO implements ItemDAOInterface {

    @Override
    public List<Item> findAll() throws AppException {
        String sql = "SELECT * FROM items";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Item> list = new ArrayList<>();
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
            throw new AppException("Error fetching items", e);
        }
    }

    @Override
    public Item findById(int id) throws AppException {
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
        } catch (SQLException e) {
            throw new AppException("Error fetching item", e);
        }
    }

    @Override
    public Item create(Item item) throws AppException {
        String sql = "INSERT INTO items (name, price, stock) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getUnitPrice());
            ps.setInt(3, item.getStock());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                item.setId(keys.getInt(1));
                return item;
            }
            throw new AppException("Item creation failed: no ID returned.");
        } catch (SQLException e) {
            throw new AppException("Error creating item", e);
        }
    }

    @Override
    public Item update(int id, Item item) throws AppException {
        String sql = "UPDATE items SET name=?, price=?, stock=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

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
            throw new AppException("Error updating item", e);
        }
    }

    @Override
    public void delete(int id) throws AppException {
        String sql = "DELETE FROM items WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AppException("Error deleting item", e);
        }
    }

    @Override
    public double getPriceById(int itemId) throws AppException {
        String sql = "SELECT price FROM items WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
            throw new AppException("Item not found: " + itemId);
        } catch (SQLException e) {
            throw new AppException("Error fetching item price", e);
        }
    }

    @Override
    public boolean exists(int itemId) throws AppException {
        String sql = "SELECT 1 FROM items WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new AppException("Item exists check error", e);
        }
    }
}
