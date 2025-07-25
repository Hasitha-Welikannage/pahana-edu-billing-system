/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DatabaseException;
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
    public List<User> findAll() throws DatabaseException, ValidationException, NotFoundException {

        List<User> userList = userDao.findAll();

        if (userList == null || userList.isEmpty()) {
            throw new NotFoundException("users not found");
        }

        return userList;
    }

    // ----------- GET USER BY ID -----------
    public User findById(int id) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        return user;
    }

    // ----------- CREATE USER -----------
    public User create(User user) throws DatabaseException, ValidationException {

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

        User createdUser = userDao.create(user);

        return createdUser;
    }

    // ----------- UPDATE USER BY ID -----------
    public User update(int id, User userUpdate) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        if (userUpdate.getFirstName() == null || userUpdate.getFirstName().equalsIgnoreCase("")) {
            throw new ValidationException("first name can not be empty");
        }

        if (userUpdate.getLastName() == null || userUpdate.getLastName().equalsIgnoreCase("")) {
            throw new ValidationException("last name can not be empty");
        }

        if (userUpdate.getUserName() == null || userUpdate.getUserName().equalsIgnoreCase("")) {
            throw new ValidationException("user name can not be empty");
        }

        if (userUpdate.getPassword() == null || userUpdate.getPassword().equalsIgnoreCase("")) {
            throw new ValidationException("password can not be empty");
        }

        if (userUpdate.getRole() == null || userUpdate.getRole().equalsIgnoreCase("")) {
            throw new ValidationException("user role can not be empty");
        }

        User updatedUser = userDao.update(id, userUpdate);

        return updatedUser;
    }

    // ----------- DELETE USER BY ID -----------
    public void delete(int id) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("user not found");
        }

        userDao.delete(id);
    }
}
