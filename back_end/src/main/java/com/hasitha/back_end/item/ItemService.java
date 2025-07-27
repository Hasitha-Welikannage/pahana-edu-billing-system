/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class ItemService {

    ItemDAOInterface itemDao = new ItemDAO();

    public List<Item> findAll() {

        List<Item> itemList = itemDao.findAll();

        if (itemList == null || itemList.isEmpty()) {
            throw new NotFoundException("items can not be found");
        }

        return itemList;
    }

    public Item findById(int id) {

        Item item = itemDao.findById(id);

        if (item == null) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        return item;

    }

    public Item create(Item item) {

        validateItem(item);

        return itemDao.create(item);

    }

    public Item update(int id, Item item) {

        if (!exists(id)) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        validateItem(item);
        item.setId(id); // Ensure the ID is consistent

        return itemDao.update(id, item);

    }

    public void delete(int id) {

        if (!exists(id)) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        itemDao.delete(id);
    }

    public double getPriceById(int id) {

        Item item = itemDao.findById(id);

        if (item == null) {
            throw new NotFoundException("Item with ID " + id + " not found.");
        }

        return item.getUnitPrice(); // Assumes `getPrice()` exists in Item

    }

    public boolean exists(int id) {

        Item item = itemDao.findById(id);
        return item != null;

    }

    private void validateItem(Item item) {
        if (item == null) {
            throw new ValidationException("Item cannot be null.");
        }

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new ValidationException("Item name is required.");
        }

        if (item.getUnitPrice() == 0) {
            throw new ValidationException("Item price cannot be zero.");
        }

        if (item.getUnitPrice() < 0) {
            throw new ValidationException("Item price cannot be negative.");
        }
    }

}
