package com.hasitha.back_end.customer;

/**
 * Represents a customer entity with personal and contact information. This
 * class is typically used for data transfer between layers in the application.
 */
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    /**
     * Default constructor. Required for frameworks that use reflection or
     * serialization.
     */
    public Customer() {
    }

    /**
     * Constructs a new Customer without id.
     *
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @param address the address of the customer
     * @param phoneNumber the contact phone number of the customer
     */
    public Customer(String firstName, String lastName, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a new Customer with all fields.
     *
     * @param id the unique identifier of the customer
     * @param firstName the first name of the customer
     * @param lastName the last name of the customer
     * @param address the address of the customer
     * @param phoneNumber the contact phone number of the customer
     */
    public Customer(int id, String firstName, String lastName, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the customer ID.
     *
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the customer ID.
     *
     * @param id the customer ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the customer's first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the customer's first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the customer's last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the customer's last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the customer's address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer's address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the customer's phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the customer's phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns a string representation of the Customer object.
     *
     * @return a string with customer field values
     */
    @Override
    public String toString() {
        return "Customer{"
                + "id=" + id
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", address=" + address
                + ", phoneNumber=" + phoneNumber
                + '}';
    }
}
