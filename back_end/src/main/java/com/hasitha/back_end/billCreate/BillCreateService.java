/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.Bill;
import com.hasitha.back_end.bill.BillDTO;
import com.hasitha.back_end.bill.BillService;
import com.hasitha.back_end.billCreate.CreateBillRequest.ItemDTO;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.billItem.BillItemService;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.customer.CustomerService;
import com.hasitha.back_end.exceptions.AppException;
import com.hasitha.back_end.item.Item;
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

    public BillDTO createBill(int userId, CreateBillRequest req) {

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
        billHeader = billService.createBill(billHeader);

        // 4. Persist all items
        billItemService.saveBillItems(billHeader.getId(), items);

        billHeader.setItems(billItemService.getBillItemsByBillId(billHeader.getId()));

        //Map the data to DTO classes
        List<BillItemDTO> billItems = new ArrayList();

        for (BillItem item : billHeader.getItems()) {

            Item i = itemService.findById(item.getItemId());

            billItems.add(new BillItemDTO(item.getId(), i.getId(), i.getName(), i.getUnitPrice(), item.getQuantity(), item.getTotalPrice()));
        }

        BillDTO bill = new BillDTO(billHeader.getId(), customer, user, billHeader.getDate(), billHeader.getTotal(), billItems);

        return bill;
    }
    // convenience pass‑throughs

    public List<Bill> getAllBills() {
        return billService.getBillList();
    }

    public List<BillItem> getItemsForBill(int billId) {
        return billItemService.getBillItemsByBillId(billId);

    }
}
