/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.utils.DBConnection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author hasithawelikannage
 */
public class BillItemDAO implements BillItemDAOInterface {

    @Override
    public void saveItems( List<BillItem> items) throws AppException {
        String sql = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            for (BillItem item : items) {
                ps.setInt(1, item.getBillId());
                ps.setInt(2, item.getItemId());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getTotalPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new AppException("Error saving bill items", e);
        }
    }

    @Override
    public List<BillItem> findByBillId(int billId) throws AppException {
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
            throw new AppException("Error retrieving bill items", e);
        }
    }

}
