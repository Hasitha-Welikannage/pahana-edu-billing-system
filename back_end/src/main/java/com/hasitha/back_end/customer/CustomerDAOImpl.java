package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the CustomerDAO interface. Provides concrete logic to
 * perform CRUD operations on the Customer entity using JDBC.
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * Fetches all customers from the database.
     *
     * @return a list of all customers
     * @throws DatabaseException if there is a database error
     */
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

    /**
     * Fetches a customer by ID from the database.
     *
     * @param id the ID of the customer
     * @return the Customer object if found, otherwise null
     * @throws DatabaseException if there is a database error
     */
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

    /**
     * Creates a new customer in the database.
     *
     * @param customer the customer to be created
     * @return the created customer with the generated ID
     * @throws DatabaseException if creation fails
     */
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
                throw new DatabaseException("Creating customer failed, no rows affected.");
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

    /**
     * Updates an existing customer in the database.
     *
     * @param id the ID of the customer to update
     * @param customer the updated customer data
     * @return the updated customer
     * @throws DatabaseException if update fails
     */
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

            return findById(id); // Return the updated customer from DB

        } catch (SQLException ex) {
            throw new DatabaseException("Error updating customer with ID " + id + ". " + ex.getMessage());
        }
    }

    /**
     * Deletes a customer by ID from the database.
     *
     * @param id the ID of the customer to delete
     * @throws DatabaseException if delete fails
     */
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

    /**
     * Checks if a customer with the given ID exists in the database.
     *
     * @param id the customer ID
     * @return true if exists, false otherwise
     * @throws DatabaseException if query fails
     */
    @Override
    public boolean exists(int id) {
        String sql = "SELECT 1 FROM customers WHERE id = ?";

        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeQuery().next(); // true if result exists

        } catch (SQLException ex) {
            throw new DatabaseException("Customer exists check error. " + ex.getMessage());
        }
    }
}
