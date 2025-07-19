/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.dao;

import com.hasitha.back_end.exceptions.DaoException;
import com.hasitha.back_end.model.User;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hasithawelikannage
 */
public class UserDAO implements UserDAOInterface {

    private DBConnection dbc = new DBConnection();

    /**
     *
     * @return @throws DaoException
     * @throws com.hasitha.back_end.exceptions.DaoException
     */
    @Override
    public List<User> findAll() throws DaoException {
        String sql = "SELECT * FROM users";
        try (Connection c = dbc.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) {
                list.add(
                        new User(
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("role")
                        )
                );
            }
            return list;
        } catch (SQLException ex) {
            //Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Database error while fetching users", ex);
        }
    }

    @Override
    public User findById(int id) throws DaoException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection c = dbc.getConnection()) {

            PreparedStatement statement = c.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("role")
                );

            } else {
                return null;
            }

        } catch (SQLException ex) {
            //Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DaoException("Database error while fetching users", ex);
        }
    }
}
