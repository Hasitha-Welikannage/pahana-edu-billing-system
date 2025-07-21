/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface ItemDAOInterface {

    List<Item> findAll() throws DaoException;

    Item findById(int id) throws DaoException;

    Item create(Item item) throws DaoException;

    Item update(int id, Item item) throws DaoException;

    void delete(int id) throws DaoException;
}
