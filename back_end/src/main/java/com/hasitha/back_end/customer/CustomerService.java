/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.MessageConstants;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class CustomerService {

    CustomerDAOInterface customerDao = new CustomerDAO();

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

        validateUser(user, false);

        User updatedUser = userDao.update(id, userUpdate);

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
