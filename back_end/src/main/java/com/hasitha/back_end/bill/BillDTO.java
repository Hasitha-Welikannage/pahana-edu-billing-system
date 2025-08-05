package com.hasitha.back_end.bill;

import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.user.User;
import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a Bill with detailed information,
 * including customer, user, date, total amount, and list of bill items.
 */
public class BillDTO {

    private int id;
    private Customer customer;
    private User user;
    private Date date;
    private double total;
    private List<BillItemDTO> billItems;

    /**
     * Default constructor.
     */
    public BillDTO() {
    }

    /**
     * Constructs a BillDTO with basic details excluding bill items.
     *
     * @param id the bill ID
     * @param customer the customer associated with the bill
     * @param user the user who created the bill
     * @param date the date of the bill
     * @param total the total amount of the bill
     */
    public BillDTO(int id, Customer customer, User user, Date date, double total) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.date = date;
        this.total = total;
    }

    /**
     * Constructs a BillDTO with full details including bill items.
     *
     * @param id the bill ID
     * @param customer the customer associated with the bill
     * @param user the user who created the bill
     * @param date the date of the bill
     * @param total the total amount of the bill
     * @param billItems list of bill items in the bill
     */
    public BillDTO(int id, Customer customer, User user, Date date, double total, List<BillItemDTO> billItems) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.date = date;
        this.total = total;
        this.billItems = billItems;
    }

    /**
     * Gets the bill ID.
     *
     * @return the bill ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the bill ID.
     *
     * @param id the bill ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the customer of this bill.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer for this bill.
     *
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the user who created this bill.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created this bill.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the date of this bill.
     *
     * @return the bill date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of this bill.
     *
     * @param date the bill date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the total amount of the bill.
     *
     * @return the total amount
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total amount of the bill.
     *
     * @param total the total amount to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Gets the list of bill items.
     *
     * @return list of BillItemDTO objects
     */
    public List<BillItemDTO> getBillItems() {
        return billItems;
    }

    /**
     * Sets the list of bill items.
     *
     * @param billItems the list of BillItemDTO objects to set
     */
    public void setBillItems(List<BillItemDTO> billItems) {
        this.billItems = billItems;
    }
}
