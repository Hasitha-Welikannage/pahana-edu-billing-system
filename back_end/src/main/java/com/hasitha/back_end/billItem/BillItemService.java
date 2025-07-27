/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillItemService {

    private BillItemDAOInterface billItemDao = new BillItemDAO();

    public void saveBillItems(int billId, List<BillItem> items) {

        if (items == null || items.isEmpty()) {
            throw new ValidationException("Must contain at least one item");
        }

        billItemDao.saveItems(billId, items);

    }

    public List<BillItem> getBillItemsByBillId(int billId) {

        List<BillItem> billItems = billItemDao.findByBillId(billId);

        if (billItems == null || billItems.isEmpty()) {
            throw new NotFoundException("bill items can not found");
        }

        return billItems;
    }

}
