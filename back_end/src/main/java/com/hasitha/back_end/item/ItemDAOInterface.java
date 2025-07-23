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

    public List<Item> findAll() throws AppException;

    public Item findById(int id) throws AppException;

    public Item create(Item item) throws AppException;

    public Item update(int id, Item item) throws AppException;

    public void delete(int id) throws AppException;

    public double getPriceById(int itemId) throws AppException;

    public boolean exists(int itemId) throws AppException;

}
