package com.hasitha.back_end.bill;

import com.hasitha.back_end.billItem.BillItem;
import java.util.Date;
import java.util.List;

/**
 * Represents a bill/invoice created during a purchase transaction. Includes
 * details about customer, user who created the bill, timestamp, total amount,
 * and the list of bill items.
 */
public class Bill {

    /**
     * Unique identifier for the bill
     */
    private int id;

    /**
     * ID of the customer associated with the bill
     */
    private int customerId;

    /**
     * ID of the user who created the bill (e.g., logged-in staff)
     */
    private int userId;

    /**
     * Timestamp when the bill was created
     */
    private Date date;

    /**
     * Total amount of the bill
     */
    private double total;

    /**
     * List of individual bill items (products and their quantities)
     */
    private List<BillItem> items;

    /**
     * Default constructor
     */
    public Bill() {
    }
    
    /**
     * Constructs a Bill with basic details excluding bill Id and items.
     *
     * @param customerId the customer ID
     * @param userId the user ID
     * @param date the date/time of the bill
     * @param total the total bill amount
     */
    public Bill(int customerId, int userId, Date date, double total) {
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.total = total;
    }

    /**
     * Constructs a Bill with basic details excluding items.
     *
     * @param id the bill ID
     * @param customerId the customer ID
     * @param userId the user ID
     * @param date the date/time of the bill
     * @param total the total bill amount
     */
    public Bill(int id, int customerId, int userId, Date date, double total) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.total = total;
    }

    /**
     * Constructs a Bill with all details including the bill items.
     *
     * @param id the bill ID
     * @param customerId the customer ID
     * @param userId the user ID
     * @param date the date/time of the bill
     * @param total the total bill amount
     * @param items the list of bill items
     */
    public Bill(int id, int customerId, int userId, Date date, double total, List<BillItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.total = total;
        this.items = items;
    }

    /**
     * @return the bill ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id sets the bill ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId sets the customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId sets the user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the date/time of the bill
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date sets the date/time of the bill
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the total bill amount
     */
    public double getTotal() {
        return total;
    }

    /**
     * @param total sets the total bill amount
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * @return the list of bill items
     */
    public List<BillItem> getItems() {
        return items;
    }

    /**
     * @param items sets the list of bill items
     */
    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    /**
     * Returns a summary string representation of the bill.
     *
     * @return formatted string of bill details
     */
    @Override
    public String toString() {
        return "Bill{"
                + "id=" + id
                + ", customerId=" + customerId
                + ", userId=" + userId
                + ", date=" + date
                + ", total=" + total
                + ", itemsCount=" + (items != null ? items.size() : 0)
                + '}';
    }
}
