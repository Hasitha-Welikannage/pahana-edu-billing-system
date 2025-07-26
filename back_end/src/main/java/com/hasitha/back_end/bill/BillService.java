/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.bill.CreateBillRequest.ItemDTO;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.billItem.BillItemDAO;
import com.hasitha.back_end.billItem.BillItemDAOInterface;
import com.hasitha.back_end.customer.CustomerDAO;
import com.hasitha.back_end.customer.CustomerDAOInterface;
import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.item.ItemDAO;
import com.hasitha.back_end.item.ItemDAOInterface;
import com.hasitha.back_end.item.ItemService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillService {

    ItemService itemService = new ItemService();
    CustomerDAOInterface customerDao = new CustomerDAO();
    BillDAOInterface billDao = new BillDAO();
    BillItemDAOInterface biDao = new BillItemDAO();

    public Bill createBill(int userId, CreateBillRequest req) throws AppException {

        // 1. Validate request basics
        if (!customerDao.exists(req.getCustomerId())) {
            throw new AppException("Customer does not exist: " + req.getCustomerId());
        }

        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new AppException("Bill must contain at least one item");
        }

        List<BillItem> items = new ArrayList<>();
        double grandTotal = 0;

        // 2. Validate each item and calc totals
        for (ItemDTO dto : req.getItems()) {
            if (dto.getQuantity() <= 0) {
                throw new AppException("Quantity must be >0 for item " + dto.getItemId());
            }
            if (!itemService.exists(dto.getItemId())) {
                throw new AppException("Item does not exist: " + dto.getItemId());
            }

            double unitPrice = itemService.getPriceById(dto.getItemId());
            double subtotal = unitPrice * dto.getQuantity();
            grandTotal += subtotal;

            items.add(new BillItem(0, 0, dto.getItemId(), dto.getQuantity(), subtotal));
        }

        // 3. Persist bill header
        Bill billHeader = new Bill(0, req.getCustomerId(), userId, new Date(), grandTotal);
        billHeader = billDao.create(billHeader);

        // 4. Persist all items
        biDao.saveItems(billHeader.getId(), items);

        billHeader.setItems(biDao.findByBillId(billHeader.getId()));
        return billHeader;
    }
    // convenience pass‑throughs

    public List<Bill> getAllBills() throws AppException {
        return billDao.findAll();
    }

    public List<BillItem> getItemsForBill(int billId) throws AppException {
        return biDao.findByBillId(billId);

    }
}
