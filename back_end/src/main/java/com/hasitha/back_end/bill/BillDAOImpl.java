package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BillDAO for performing database operations on bills.
 */
public class BillDAOImpl implements BillDAO {

    /**
     * Creates a new bill record in the database.
     *
     * @param bill the bill object containing customerId, userId, date, and
     * total
     * @return the bill object with the generated ID
     * @throws DatabaseException if insertion fails or generated ID cannot be
     * retrieved
     */
    @Override
    public Bill create(Bill bill) {
        String sql = "INSERT INTO bills (customer_id, user_id, bill_date, total) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, bill.getCustomerId());
            ps.setInt(2, bill.getUserId());
            ps.setTimestamp(3, new Timestamp(bill.getDate().getTime()));
            ps.setDouble(4, bill.getTotal());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Creating bill failed: No rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    bill.setId(rs.getInt(1));
                    return bill;
                } else {
                    throw new DatabaseException("Creating bill failed: Unable to retrieve generated ID.");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database error while creating bill: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all bills from the database, ordered by date (most recent
     * first).
     *
     * @return list of all bills
     * @throws DatabaseException if a database access error occurs
     */
    @Override
    public List<Bill> findAll() {
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";
        List<Bill> bills = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("bill_date"),
                        rs.getDouble("total")
                );
                bills.add(bill);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching all bills: " + e.getMessage(), e);
        }

        return bills;
    }

    /**
     * Retrieves a bill by its ID.
     *
     * @param id the ID of the bill to find
     * @return the matching bill or null if not found
     * @throws DatabaseException if a database access error occurs
     */
    @Override
    public Bill findById(int id) {
        String sql = "SELECT * FROM bills WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bill(
                            rs.getInt("id"),
                            rs.getInt("customer_id"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("bill_date"),
                            rs.getDouble("total")
                    );
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database error while fetching bill by ID (" + id + "): " + e.getMessage(), e);
        }

        return null; // If not found
    }
}
