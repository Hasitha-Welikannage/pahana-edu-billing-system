/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

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
public class CustomerDAO implements CustomerDAOInterface {

    @Override
    public List<Customer> findAll() throws DaoException {
        String sql = "SELECT * FROM customers";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            List<Customer> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("Error fetching customers", e);
        }
    }

    @Override
    public Customer findById(int id) throws DaoException {
        String sql = "SELECT * FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("Error fetching customer", e);
        }
    }

    @Override
    public Customer create(Customer customer) throws DaoException {
        String sql = "INSERT INTO customers (first_name, last_name, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());

            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                customer.setId(keys.getInt(1));
                return customer;
            } else {
                throw new DaoException("Creating customer failed, no ID returned.");
            }
        } catch (SQLException e) {
            throw new DaoException("Error creating customer", e);
        }
    }

    @Override
    public Customer update(int id, Customer customer) throws DaoException {
        String sql = "UPDATE customers SET first_name=?, last_name=?, address=?, phone=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                customer.setId(id);
                return customer;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DaoException("Error updating customer", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error deleting customer", e);
        }
    }

}
