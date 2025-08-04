package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the BillItemDAO interface. Handles database operations
 * related to bill_items table.
 */
public class BillItemDAOImpl implements BillItemDAO {

    /**
     * Saves a list of bill items for a specific bill ID into the database. Uses
     * batch insert for performance efficiency.
     *
     * @param billId the ID of the bill to associate the items with
     * @param items the list of BillItem objects to save
     */
    @Override
    public void saveItems(int billId, List<BillItem> items) {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (BillItem item : items) {
                ps.setInt(1, billId);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getSubTotal());
                ps.addBatch(); // add this insert to the batch
            }
            ps.executeBatch(); // execute all batched inserts at once
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to save bill items for bill ID " + billId + ". Database error: " + e.getMessage(), e
            );
        }
    }

    /**
     * Retrieves a list of bill items associated with a specific bill ID.
     *
     * @param billId the ID of the bill to look up
     * @return list of BillItem records found in the database
     */
    @Override
    public List<BillItem> findByBillId(int billId) {
        String sql = "SELECT * FROM bill_items WHERE bill_id = ?";
        List<BillItem> items = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(new BillItem(
                        rs.getInt("id"),
                        rs.getInt("bill_id"),
                        rs.getInt("item_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }
            return items;
        } catch (SQLException e) {
            throw new DatabaseException(
                    "Failed to retrieve bill items for bill ID " + billId + ". Database error: " + e.getMessage(), e
            );
        }
    }
}
