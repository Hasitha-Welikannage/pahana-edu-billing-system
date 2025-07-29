/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class UserService {

    UserDAOInterface userDao = new UserDAO();

    // ----------- GET ALL USERS -----------
    public List<User> findAll() {

        List<User> userList = userDao.findAll();

        if (userList == null || userList.isEmpty()) {
            throw new NotFoundException("users not found");
        }

        return userList;
    }

    // ----------- GET USER BY ID -----------
    public User findById(int id) {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    // ----------- CREATE USER -----------
    public User create(User user) {

        validateUser(user, true);

        User createdUser = userDao.create(user);

        return createdUser;
    }

    // ----------- UPDATE USER BY ID -----------
    public User update(int id, User userUpdate) {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        if (userUpdate.getPassword() == null || userUpdate.getPassword().equalsIgnoreCase("")) {
            userUpdate.setPassword(user.getPassword());
        }

        validateUser(userUpdate, false);

        User updatedUser = userDao.update(id, userUpdate);
        
        updatedUser.setPassword("");
        
        return updatedUser;
    }

    // ----------- DELETE USER BY ID -----------
    public void delete(int id) {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        userDao.delete(id);
    }

    // ----------- CHECK CUSTOMER EXISTS BY ID -----------
    public boolean exists(int id) {

        User user = userDao.findById(id);
        return user != null;

    }

    // ----------- VALIDATION METHOD -----------
    private void validateUser(User user, boolean isCreate) {
        if (user.getFirstName() == null || user.getFirstName().equalsIgnoreCase("")) {
            throw new ValidationException("first name can not be empty");
        }

        if (user.getLastName() == null || user.getLastName().equalsIgnoreCase("")) {
            throw new ValidationException("last name can not be empty");
        }

        if (user.getUserName() == null || user.getUserName().equalsIgnoreCase("")) {
            throw new ValidationException("user name can not be empty");
        }

        if (user.getPassword() == null || user.getPassword().equalsIgnoreCase("")) {
            throw new ValidationException("password can not be empty");
        }

        if (user.getRole() == null || user.getRole().equalsIgnoreCase("")) {
            throw new ValidationException("user role can not be empty");
        }

        if (isCreate && userDao.findByUsername(user.getUserName()) != null) {
            throw new ValidationException(MessageConstants.USERNAME_EXISTS);
        }

        // Check if the new username is used by another user
        User userWithSameUsername = userDao.findByUsername(user.getUserName());

        if (!isCreate && userWithSameUsername != null && userWithSameUsername.getId() != user.getId()) {
            throw new ValidationException(MessageConstants.USERNAME_EXISTS);
        }

    }
}
