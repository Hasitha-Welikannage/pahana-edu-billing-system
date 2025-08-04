/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.customer;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 * Service layer for handling business logic related to customers. Communicates
 * with CustomerDAO for database operations and handles validation and error
 * checking.
 */
public class CustomerService {

    private final CustomerDAO customerDao;

    /**
     * Constructor that accepts a CustomerDAO implementation for dependency
     * injection.
     *
     * @param customerDao DAO implementation to use for database access.
     */
    public CustomerService(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }

    /**
     * Default constructor that initializes with the default CustomerDAOImpl.
     */
    public CustomerService() {
        this.customerDao = new CustomerDAOImpl();
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return a list of Customer objects.
     * @throws NotFoundException if no customers are found.
     */
    public List<Customer> findAll() {
        List<Customer> customerList = customerDao.findAll();
        if (customerList == null || customerList.isEmpty()) {
            throw new NotFoundException("No customers found in the system.");
        }
        return customerList;
    }

    /**
     * Finds a customer by their unique ID.
     *
     * @param id the customer ID.
     * @return the Customer object.
     * @throws NotFoundException if the customer is not found.
     */
    public Customer findById(int id) {
        Customer customer = customerDao.findById(id);
        if (customer == null) {
            throw new NotFoundException("Customer with the specified ID " + id + " does not exist.");
        }
        return customer;
    }

    /**
     * Creates a new customer after validating the input.
     *
     * @param customer the customer object to create.
     * @return the created Customer with generated ID.
     * @throws ValidationException if validation fails.
     */
    public Customer create(Customer customer) {
        validateCustomer(customer);
        return customerDao.create(customer);
    }

    /**
     * Updates an existing customer by ID after validation.
     *
     * @param id the customer ID.
     * @param customerUpdate the customer object with updated data.
     * @return the updated Customer object.
     * @throws NotFoundException if the customer does not exist.
     * @throws ValidationException if validation fails.
     */
    public Customer update(int id, Customer customerUpdate) {
        ensureCustomerExists(id);
        validateCustomer(customerUpdate);
        return customerDao.update(id, customerUpdate);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the customer ID.
     * @throws NotFoundException if the customer does not exist.
     */
    public void delete(int id) {
        ensureCustomerExists(id);
        customerDao.delete(id);
    }

    /**
     * Ensures a customer exists by ID.
     *
     * @param id the customer ID.
     * @throws NotFoundException if the customer is not found.
     */
    public void ensureCustomerExists(int id) {
        if (customerDao.findById(id) == null) {
            throw new NotFoundException("Customer with the specified ID " + id + " does not exist.");
        }
    }

    /**
     * Checks whether a customer exists by ID.
     *
     * @param id the customer ID.
     * @return true if the customer exists, false otherwise.
     */
    public boolean exists(int id) {
        Customer customer = customerDao.findById(id);
        return customer != null;
    }

    /**
     * Validates a customer object to ensure all required fields are present and
     * correctly formatted.
     *
     * @param customer the customer to validate.
     * @throws ValidationException if any validation rules are violated.
     */
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
