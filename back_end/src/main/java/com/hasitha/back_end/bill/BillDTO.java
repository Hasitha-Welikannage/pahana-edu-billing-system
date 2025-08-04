/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.user.User;
import java.util.Date;
import java.util.List;


public class BillDTO {

    private int id;
    private Customer customer;
    private User user;
    private Date date;
    private double total;
    private List<BillItemDTO> billItems;

    public BillDTO() {
    }

    public BillDTO(int id, Customer customer, User user, Date date, double total) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.date = date;
        this.total = total;
    }

    public BillDTO(int id, Customer customer, User user, Date date, double total, List<BillItemDTO> billItems) {
        this.id = id;
        this.customer = customer;
        this.user = user;
        this.date = date;
        this.total = total;
        this.billItems = billItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<BillItemDTO> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemDTO> billItems) {
        this.billItems = billItems;
    }

}
