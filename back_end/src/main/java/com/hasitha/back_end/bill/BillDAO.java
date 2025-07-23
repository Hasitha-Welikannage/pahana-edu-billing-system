/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillDAO implements BillDAOInterface {

    @Override
    public Bill create(Bill bill) throws AppException {
        String sql = "INSERT INTO bills (customer_id, user_id, bill_date, total) VALUES (?, ?, ?, ?)";
        try (
                Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, bill.getCustomerId());
            ps.setInt(2, bill.getUserId());
            ps.setTimestamp(3, new Timestamp(bill.getDate().getTime()));
            ps.setDouble(4, bill.getTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                bill.setId(rs.getInt(1));
                return bill;
            } else {
                throw new AppException("Failed to get generated bill ID");
            }

        } catch (SQLException e) {
            throw new AppException("Error creating bill", e);
        }
    }

    @Override
    public List<Bill> findAll() throws AppException {
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";
        List<Bill> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("bill_date"),
                        rs.getDouble("total")
                ));
            }
            return list;
        } catch (SQLException e) {
            throw new AppException("Error fetching bills", e);
        }

    }

    @Override
    public Bill findById(int id) throws AppException {
        String sql = "SELECT * FROM bills WHERE id = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bill(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("user_id"),
                        rs.getTimestamp("bill_date"),
                        rs.getDouble("total")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new AppException("Error fetching bill by id", e);
        }
    }

}
