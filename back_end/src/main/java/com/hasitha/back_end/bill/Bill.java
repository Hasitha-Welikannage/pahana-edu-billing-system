/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class Bill {

    private int id;
    private int customerId;
    private int userId;
    private String date;
    private List<BillItem> items;
    private double total;

    public Bill() {
    }

    public Bill(int id, int customerId, int userId, String date, List<BillItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.date = date;
        this.items = items;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = calculateTotal(this.items);
    }

    public double calculateTotal(List<BillItem> items) {

        double totalAmount = 0;
        for (BillItem item : items) {
            totalAmount += item.getTotalPrice();
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Bill{" + "id=" + id + ", customerId=" + customerId + ", userId=" + userId + ", date=" + date + ", items=" + items + '}';
    }

}
