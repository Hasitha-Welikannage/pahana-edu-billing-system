/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.Bill;
import com.hasitha.back_end.bill.BillDAO;
import com.hasitha.back_end.bill.BillDAOInterface;
import com.hasitha.back_end.bill.BillService;
import com.hasitha.back_end.billCreate.CreateBillRequest.ItemDTO;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.billItem.BillItemDAO;
import com.hasitha.back_end.billItem.BillItemDAOInterface;
import com.hasitha.back_end.billItem.BillItemService;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.customer.CustomerService;
import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.item.ItemService;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.user.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public class BillCreateService {

    ItemService itemService = new ItemService();
    CustomerService customerService = new CustomerService();
    UserService userService = new UserService();
    BillService billService = new BillService();
    BillItemService billItemService = new BillItemService();

    BillDAOInterface billDao = new BillDAO();
    BillItemDAOInterface biDao = new BillItemDAO();

    public Bill createBill(int userId, CreateBillRequest req) {

        List<BillItem> items = new ArrayList<>();
        double grandTotal = 0;
        Customer customer = customerService.findById(req.getCustomerId());
        User user = userService.findById(userId);

        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new AppException("bill must contain at least one item");
        }

        // 2. Validate each item and calc totals
        for (ItemDTO dto : req.getItems()) {
            if (dto.getQuantity() <= 0) {
                throw new AppException("Quantity must be > 0 for item " + dto.getItemId());
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

    public List<Bill> getAllBills() {
        return billDao.findAll();
    }

    public List<BillItem> getItemsForBill(int billId) {
        return biDao.findByBillId(billId);

    }
}
