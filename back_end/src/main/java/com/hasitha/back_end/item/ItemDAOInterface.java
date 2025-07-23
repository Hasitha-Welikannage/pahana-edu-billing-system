/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.AppException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface ItemDAOInterface {

    List<Item> findAll() throws AppException;

    Item findById(int id) throws AppException;

    Item create(Item item) throws AppException;

    Item update(int id, Item item) throws AppException;

    void delete(int id) throws AppException;

    double getPriceById(int itemId) throws AppException;
}
