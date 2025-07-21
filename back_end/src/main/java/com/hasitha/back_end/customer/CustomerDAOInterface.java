/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface CustomerDAOInterface {

    public List<Customer> findAll() throws DaoException;

    public Customer findById(int id) throws DaoException;

    public Customer create(Customer customer) throws DaoException;

    public Customer update(int id, Customer customer) throws DaoException;

    public void delete(int id) throws DaoException;
}
