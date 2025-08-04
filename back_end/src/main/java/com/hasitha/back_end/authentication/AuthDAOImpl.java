package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAOImpl implements AuthDAO {

    @Override
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
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
            throw new DatabaseException("An error occurred during user authentication. Please try again later. " + ex.getMessage());
        }
    }
}
