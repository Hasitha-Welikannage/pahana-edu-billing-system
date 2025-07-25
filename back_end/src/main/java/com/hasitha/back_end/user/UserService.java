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
            throw new NotFoundException("Users not found");
        }

        return userList;
    }

    // ----------- GET USER BY ID -----------
    public User findById(int id) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    // ----------- CREATE USER -----------
    public User create(User user) throws DatabaseException, ValidationException, NotFoundException {

        User createdUser = userDao.create(user);

        return createdUser;
    }

    // ----------- UPDATE USER BY ID -----------
    public User update(int id, User userUpdate) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        User updatedUser = userDao.update(id, userUpdate);

        return updatedUser;
    }

    // ----------- DELETE USER BY ID -----------
    public void delete(int id) throws DatabaseException, ValidationException, NotFoundException {

        User user = userDao.findById(id);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        userDao.delete(id);
    }
}
