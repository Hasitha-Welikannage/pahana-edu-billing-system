/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillService {

    private final BillDAOInterface billDao = new BillDAO();

    public Bill createBill(Bill bill) {

        validateBill(bill);

        return billDao.create(bill);
    }

    public List<Bill> getBillList() {

        List<Bill> list = billDao.findAll();

        if (list == null || list.isEmpty()) {
            throw new ValidationException("bills are not found");
        }

        return list;
    }

    public Bill gitBillById(int id) {

        Bill bill = billDao.findById(id);

        if (bill == null) {
            throw new ValidationException("bill not found");
        }

        return bill;
    }

    private void validateBill(Bill bill) {
        if (bill == null) {
            throw new ValidationException("Bill header cannot be null.");
        }

        if (bill.getCustomerId() == 0 || bill.getCustomerId() < 0) {
            throw new ValidationException("customer is required.");
        }

        if (bill.getUserId() == 0 || bill.getUserId() < 0) {
            throw new ValidationException("user is required.");
        }

        if (bill.getTotal() == 0) {
            throw new ValidationException("total price cannot be zero.");
        }

        if (bill.getTotal() < 0) {
            throw new ValidationException("total price cannot be negative.");
        }
    }

}
