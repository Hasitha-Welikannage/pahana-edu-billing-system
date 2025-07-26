/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.DatabaseException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface ItemDAOInterface {

    public List<Item> findAll() throws DatabaseException;

    public Item findById(int id) throws DatabaseException;

    public Item create(Item item) throws DatabaseException;

    public Item update(int id, Item item) throws DatabaseException;

    public void delete(int id) throws DatabaseException;

}
