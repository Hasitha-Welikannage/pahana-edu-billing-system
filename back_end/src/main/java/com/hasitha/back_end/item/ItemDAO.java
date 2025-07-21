/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.DaoException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class ItemDAO implements ItemDAOInterface {

    @Override
    public List<Item> findAll() throws DaoException {
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
            throw new DaoException("Error fetching items", e);
        }
    }

    @Override
    public Item findById(int id) throws DaoException {
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
            throw new DaoException("Error fetching item", e);
        }
    }

    @Override
    public Item create(Item item) throws DaoException {
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
            throw new DaoException("Item creation failed: no ID returned.");
        } catch (SQLException e) {
            throw new DaoException("Error creating item", e);
        }
    }

    @Override
    public Item update(int id, Item item) throws DaoException {
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
            throw new DaoException("Error updating item", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM items WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error deleting item", e);
        }
    }
}
