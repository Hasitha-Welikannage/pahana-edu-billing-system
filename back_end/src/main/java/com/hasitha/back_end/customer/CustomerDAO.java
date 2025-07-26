/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class CustomerDAO implements CustomerDAOInterface {

    @Override
    public List<Customer> findAll() throws DatabaseException {
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
            throw new DatabaseException("Error fetching customers", e);
        }
    }

    @Override
    public Customer findById(int id) throws DatabaseException {
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
            throw new DatabaseException("Error fetching customer", e);
        }
    }

    @Override
    public Customer create(Customer customer) throws DatabaseException {
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
                throw new DatabaseException("Creating customer failed, no ID returned.");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error creating customer", e);
        }
    }

    @Override
    public Customer update(int id, Customer customer) throws DatabaseException {
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
            throw new DatabaseException("Error updating customer", e);
        }
    }

    @Override
    public void delete(int id) throws DatabaseException {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (
                Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting customer", e);
        }
    }

    @Override
    public boolean exists(int customerId) throws DatabaseException {
        String sql = "SELECT 1 FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new DatabaseException("Customer exists check error", e);
        }
    }

}
