/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.AppException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface CustomerDAOInterface {

    public List<Customer> findAll() throws AppException;

    public Customer findById(int id) throws AppException;

    public Customer create(Customer customer) throws AppException;

    public Customer update(int id, Customer customer) throws AppException;

    public void delete(int id) throws AppException;
}
