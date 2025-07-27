/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.customer;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface CustomerDAOInterface {

    public List<Customer> findAll();

    public Customer findById(int id);

    public Customer create(Customer customer);

    public Customer update(int id, Customer customer);

    public void delete(int id);

    public boolean exists(int customerId);

}
