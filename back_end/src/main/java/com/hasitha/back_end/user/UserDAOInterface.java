/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.user;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface UserDAOInterface {

    public List<User> findAll();

    public User findById(int id);

    public User create(User user);

    public User update(int id, User user);

    public void delete(int id);

    public User findByUsername(String username);

    public User findByUsernameAndPassword(String username, String password);

}
