/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.user.User;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface UserDAOInterface {

    public List<User> findAll() throws AppException;

    public User findById(int id) throws AppException;

    public User create(User user) throws AppException;

    public User update(int id, User user) throws AppException;

    public void delete(int id) throws AppException;

    public User findByUsernameAndPassword(String username, String password) throws DatabaseException;

}
