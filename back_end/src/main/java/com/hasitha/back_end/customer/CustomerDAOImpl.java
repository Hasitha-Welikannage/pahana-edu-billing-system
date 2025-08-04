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

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
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
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching all customers. " + ex.getMessage());
        }
    }

    @Override
    public Customer findById(int id) {
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
        } catch (SQLException ex) {
            throw new DatabaseException("Error fetching customer with ID " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public Customer create(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Creating customer failed, no rows affected");
            }
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                customer.setId(generatedKeys.getInt(1));
                return customer;
            } else {
                throw new DatabaseException("Creating customer failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error creating customer. " + ex.getMessage());
        }
    }

    @Override
    public Customer update(int id, Customer customer) {
        String sql = "UPDATE customers SET first_name=?, last_name=?, address=?, phone=? WHERE id=?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhoneNumber());
            ps.setInt(5, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Updating customer failed, no rows affected for ID: " + id);
            }
            return findById(id);
        } catch (SQLException ex) {
            throw new DatabaseException("Error updating customer with ID " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DatabaseException("Deleting customer failed, no rows affected for ID: " + id);
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error deleting customer with ID " + id + ". " + ex.getMessage());
        }
    }

    @Override
    public boolean exists(int id) {
        String sql = "SELECT 1 FROM customers WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeQuery().next();
        } catch (SQLException ex) {
            throw new DatabaseException("Customer exists check error. " + ex.getMessage());
        }
    }

}
