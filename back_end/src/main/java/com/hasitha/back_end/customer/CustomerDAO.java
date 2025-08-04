package com.hasitha.back_end.customer;

import java.util.List;

/**
 * CustomerDAO defines the contract for performing CRUD operations on Customer
 * entities. DAO stands for Data Access Object, which abstracts the interaction
 * with the database layer.
 */
public interface CustomerDAO {

    /**
     * Retrieves all customers from the data source.
     *
     * @return a list of all customers
     */
    public List<Customer> findAll();

    /**
     * Finds a customer by its unique identifier.
     *
     * @param id the ID of the customer to find
     * @return the Customer object if found, or null if not found
     */
    public Customer findById(int id);

    /**
     * Creates a new customer record in the data source.
     *
     * @param customer the Customer object to create
     * @return the created Customer object with generated ID
     */
    public Customer create(Customer customer);

    /**
     * Updates an existing customer record in the data source.
     *
     * @param id the ID of the customer to update
     * @param customer the updated Customer object
     * @return the updated Customer object
     */
    public Customer update(int id, Customer customer);

    /**
     * Deletes a customer by ID from the data source.
     *
     * @param id the ID of the customer to delete
     */
    public void delete(int id);

    /**
     * Checks whether a customer exists by its ID.
     *
     * @param customerId the ID of the customer to check
     * @return true if the customer exists, false otherwise
     */
    public boolean exists(int customerId);
}
