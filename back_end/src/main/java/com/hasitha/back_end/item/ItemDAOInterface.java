/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.item;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface ItemDAOInterface {

    public List<Item> findAll();

    public Item findById(int id);

    public Item create(Item item);

    public Item update(int id, Item item);

    public void delete(int id);

}
