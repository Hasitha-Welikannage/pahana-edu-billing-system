/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.DatabaseException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface CustomerDAOInterface {

    public List<Customer> findAll() throws DatabaseException;

    public Customer findById(int id) throws DatabaseException;

    public Customer create(Customer customer) throws DatabaseException;

    public Customer update(int id, Customer customer) throws DatabaseException;

    public void delete(int id) throws DatabaseException;

    public boolean exists(int customerId) throws DatabaseException;

}
