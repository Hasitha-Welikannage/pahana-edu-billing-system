/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.user;

import com.hasitha.back_end.exceptions.DatabaseException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface UserDAOInterface {

    public List<User> findAll() throws DatabaseException;

    public User findById(int id) throws DatabaseException;

    public User create(User user) throws DatabaseException;

    public User update(int id, User user) throws DatabaseException;

    public void delete(int id) throws DatabaseException;

    public User findByUsernameAndPassword(String username, String password) throws DatabaseException;

}
