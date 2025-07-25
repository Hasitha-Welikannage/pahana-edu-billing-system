/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.DatabaseException;
import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class ItemService {

    ItemDAOInterface itemDao = new ItemDAO();

    public List<Item> findAll() throws DatabaseException, NotFoundException {

        List<Item> itemList = itemDao.findAll();

        if (itemList == null || itemList.isEmpty()) {
            throw new NotFoundException("items can not be found");
        }

        return itemList;
    }

    public Item findById(int id) throws DatabaseException, NotFoundException {

        Item item = itemDao.findById(id);

        if (item == null) {
            throw new NotFoundException("item can not be found");
        }

        return item;

    }

    public Item create(Item item) throws DatabaseException, ValidationException {

        return null;

    }

    public Item update(int id, Item item) throws DatabaseException, ValidationException, NotFoundException {

        return null;

    }

    public void delete(int id) throws DatabaseException, NotFoundException {

    }

    public double getPriceById(int itemId) throws DatabaseException, NotFoundException {

        return 0;

    }

    public boolean exists(int itemId) throws DatabaseException, NotFoundException {

        return false;

    }

}
