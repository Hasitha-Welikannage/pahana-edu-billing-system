/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.billItem.BillItem;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class Bill {

    private int id;
    private int customerId;
    private int userId;
    private Date date;
    private double total;
    private List<BillItem> items;

    public Bill() {
    }

    public Bill(int id, int customerId, int userId, Date date, double total) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", customerId=" + customerId + ", userId=" + userId + ", date=" + date + '}';
    }

}
