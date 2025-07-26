/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.authentication;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.user.UserDAO;
import com.hasitha.back_end.user.UserDAOInterface;

/**
 *
 * @author hasithawelikannage
 */
public class AuthService {

    private final UserDAOInterface userDao = new UserDAO();

    public User login(LoginRequest request) {

        if (request.getUsername() == null || request.getUsername().equalsIgnoreCase("")) {
            throw new ValidationException("Username can not be empty");
        }

        if (request.getPassword() == null || request.getPassword().equalsIgnoreCase("")) {
            throw new ValidationException("Password can not be empty");
        }

        return userDao.findByUsernameAndPassword(request.getUsername(), request.getPassword());

    }
}
