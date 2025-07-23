/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.item.ItemDAO;
import com.hasitha.back_end.item.ItemDAOInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillItemService {

    BillItemDAOInterface billItemDAO = new BillItemDAO();
    ItemDAOInterface itemDAO = new ItemDAO();

    public void saveItems(int billId, List<BillItem> items) throws AppException {

        List<BillItem> newItemList = new ArrayList();

        //validate if the items list is empty
        if (!items.isEmpty()) {
            for (BillItem billItem : items) {
                //get the quantity from the list and item unitprice from the item table and calculate the total
                int quantity = billItem.getQuantity();
                double unitPrice = itemDAO.getPriceById(billItem.getItemId());
                double price = quantity * unitPrice;
                //add to the new item list
                newItemList.add(new BillItem(billId, billItem.getItemId(), billItem.getQuantity(), price));
            }
        } else {
            throw new AppException("Item list is empty");
        }
        //save the item in the db
        billItemDAO.saveItems(newItemList);
    }

    public List<BillItem> findByBillId(int billId) throws AppException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
