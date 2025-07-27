/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class CustomerService {

    CustomerDAOInterface customerDao = new CustomerDAO();

    // ----------- GET ALL CUSTOMER -----------
    public List<Customer> findAll() {

        List<Customer> customerList = customerDao.findAll();

        if (customerList == null || customerList.isEmpty()) {
            throw new NotFoundException("customers not found");
        }

        return customerList;
    }

    // ----------- GET CUSTOMER BY ID -----------
    public Customer findById(int id) {

        Customer customer = customerDao.findById(id);

        if (customer == null) {
            throw new NotFoundException("customer not found");
        }

        return customer;
    }

    // ----------- CREATE CUSTOMER -----------
    public Customer create(Customer customer) {

        validateCustomer(customer);

        Customer createdCustomer = customerDao.create(customer);

        return createdCustomer;
    }

    // ----------- UPDATE CUSTOMER BY ID -----------
    public Customer update(int id, Customer CustomerUpdate) {

        Customer customer = customerDao.findById(id);

        if (customer == null) {
            throw new NotFoundException("customer not found");
        }

        validateCustomer(CustomerUpdate);

        Customer updatedCustomer = customerDao.update(id, CustomerUpdate);

        return updatedCustomer;
    }

    // ----------- DELETE CUSTOMER BY ID -----------
    public void delete(int id) {

        Customer customer = customerDao.findById(id);

        if (customer == null) {
            throw new NotFoundException("customer not found");
        }

        customerDao.delete(id);
    }
    
    // ----------- CHECK CUSTOMER EXISTS BY ID -----------
    public boolean exists(int id) {

        Customer customer = customerDao.findById(id);
        return customer != null;

    }

    // ----------- VALIDATION METHOD -----------
    private void validateCustomer(Customer customer) {

        if (customer.getFirstName() == null || customer.getFirstName().equalsIgnoreCase("")) {
            throw new ValidationException("first name can not be empty");
        }

        if (customer.getLastName() == null || customer.getLastName().equalsIgnoreCase("")) {
            throw new ValidationException("last name can not be empty");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().equalsIgnoreCase("")) {
            throw new ValidationException("phone number can not be empty");
        }

        if (customer.getAddress() == null || customer.getAddress().equalsIgnoreCase("")) {
            throw new ValidationException("address can not be empty");
        }

    }

}
