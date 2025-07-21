/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DaoException;
import com.hasitha.back_end.user.User;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface UserDAOInterface {

    public List<User> findAll() throws DaoException;

    public User findById(int id) throws DaoException;

    public User create(User user) throws DaoException;

    public User update(int id, User user) throws DaoException;

    public void delete(int id) throws DaoException;

    public User findByUsernameAndPassword(String username, String password) throws DaoException;

}
