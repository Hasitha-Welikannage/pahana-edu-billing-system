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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class UserDAO implements UserDAOInterface {

    /**
     *
     * @return @throws DaoException
     * @throws com.hasitha.back_end.exceptions.DaoException
     */
    @Override
    public List<User> findAll() throws DaoException {
        String sql = "SELECT * FROM users";
        try (Connection c = DBConnection.getConnection()) {

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
                                rs.getString("role")
                        )
                );
            }
            return list;
        } catch (SQLException ex) {

            throw new DaoException("Database error while fetching users", ex);
        }
    }

    @Override
    public User findById(int id) throws DaoException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection()) {

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

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

            throw new DaoException("Database error while fetching users", ex);
        }
    }

    @Override
    public User create(User user) throws DaoException {
        String sql = "INSERT INTO users (first_name, last_name, username, password, role) VALUES (?, ?, ?,?, ?)";

        try (Connection c = DBConnection.getConnection();) {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new DaoException("Creating user failed, no rows affected");
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            } else {
                throw new DaoException("Creating user failed, no ID obtained.");
            }

            return new User(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName(), user.getRole());
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new DaoException("Database error while creating user", ex);
        }
    }

    @Override
    public User update(int id, User user) throws DaoException {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, role = ? WHERE id = ?";
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getRole());
            ps.setInt(5, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Updating user failed, no rows affected.");
            }

            return findById(id); // return updated user
        } catch (SQLException ex) {
            throw new DaoException("Database error while updating user", ex);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection c = DBConnection.getConnection()) {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException("Deleting user failed, no rows affected.");
            }
        } catch (SQLException ex) {
            throw new DaoException("Database error while deleting user", ex);
        }
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws DaoException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // plain text for assignment only

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("role")
                );
            }
            return null;
        } catch (SQLException ex) {
            throw new DaoException("Error authenticating user", ex);
        }
    }
}
