/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDao;

    public CustomerService(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerService() {
        this.customerDao = new CustomerDAOImpl();
    }

    // ----------- GET ALL CUSTOMER -----------
    public List<Customer> findAll() {
        List<Customer> customerList = customerDao.findAll();
        if (customerList == null || customerList.isEmpty()) {
            throw new NotFoundException("No customers found in the system.");
        }
        return customerList;
    }

    // ----------- GET CUSTOMER BY ID -----------
    public Customer findById(int id) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            throw new NotFoundException("Customer with the specified ID " + id + " does not exist.");
        }
        return customer;
    }

    // ----------- CREATE CUSTOMER -----------
    public Customer create(Customer customer) {
        validateCustomer(customer);
        return customerDao.create(customer);
    }

    // ----------- UPDATE CUSTOMER BY ID -----------
    public Customer update(int id, Customer customerUpdate) {
        ensureCustomerExists(id);
        validateCustomer(customerUpdate);
        return customerDao.update(id, customerUpdate);
    }

    // ----------- DELETE CUSTOMER BY ID -----------
    public void delete(int id) {
        ensureCustomerExists(id);
        customerDao.delete(id);
    }

    public void ensureCustomerExists(int id) {
        if (customerDao.findById(id) == null) {
            throw new NotFoundException("Customer with the specified ID " + id + " does not exist.");
        }
    }

    // ----------- CHECK CUSTOMER EXISTS BY ID -----------
    public boolean exists(int id) {

        Customer customer = customerDao.findById(id);
        return customer != null;

    }

    // ----------- VALIDATION METHOD -----------
    private void validateCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isBlank()) {
            throw new ValidationException("First name is required and cannot be empty.");
        }
        if (customer.getLastName() == null || customer.getLastName().isBlank()) {
            throw new ValidationException("Last name is required and cannot be empty.");
        }
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new ValidationException("Phone number is required and cannot be empty.");
        }
        if (!customer.getPhoneNumber().matches("^\\+[0-9]{11}$")) {
            throw new ValidationException("Phone number format is invalid. Expected format: +94712345678");
        }
        if (customer.getAddress() == null || customer.getAddress().isBlank()) {
            throw new ValidationException("Address is required and cannot be empty.");
        }
    }

}
