
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

        // 2. Validate each billItem and calc totals
        for (ItemDTO dto : req.getItems()) {
            if (dto.getQuantity() <= 0) {
                throw new AppException("Quantity must be > 0 for item " + dto.getItemId());
            }
            itemService.ensureItemExists(dto.getItemId());
//            if (!itemService.exists(dto.getItemId())) {
//                throw new AppException("Item does not exist: " + dto.getItemId());
//            }

            double unitPrice = itemService.getPriceById(dto.getItemId());
            double subtotal = unitPrice * dto.getQuantity();
            grandTotal += subtotal;

            items.add(new BillItem(0, 0, dto.getItemId(), dto.getQuantity(), subtotal));
        }

        // 3. Persist billDto header
        Bill billHeader = new Bill(0, req.getCustomerId(), userId, new Date(), grandTotal);
        billHeader = billService.createBill(billHeader);

        // 4. Persist all items
        billItemService.saveBillItems(billHeader.getId(), items);

        billHeader.setItems(billItemService.getBillItemsByBillId(billHeader.getId()));

        //Map the data to DTO classes
        List<BillItemDTO> billItemsDto = new ArrayList();

        for (BillItem billItem : billHeader.getItems()) {

            Item item = itemService.findById(billItem.getItemId());

            billItemsDto.add(new BillItemDTO(billItem.getId(), item.getId(), item.getName(), item.getPrice(), billItem.getQuantity(), billItem.getTotalPrice()));
        }

        BillDTO billDto = new BillDTO(billHeader.getId(), customer, user, billHeader.getDate(), billHeader.getTotal(), billItemsDto);

        return billDto;
    }
    // convenience pass‑throughs

    public List<BillDTO> getAllBills() {

        List<Bill> bills = billService.getBillList();
        List<BillDTO> billDto = new ArrayList();
        Customer customer;
        User user;

        for (Bill bill : bills) {

            customer = customerService.findById(bill.getCustomerId());
            user = userService.findById(bill.getUserId());
            user.setPassword("");

            billDto.add(new BillDTO(bill.getId(), customer, user, bill.getDate(), bill.getTotal()));

        }

        return billDto;
    }

    public List<BillItemDTO> getItemsForBill(int billId) {

        List<BillItem> billItems = billItemService.getBillItemsByBillId(billId);
        List<BillItemDTO> billItemsDto = new ArrayList();

        for (BillItem billItem : billItems) {

            Item item = itemService.findById(billItem.getItemId());

            billItemsDto.add(new BillItemDTO(billItem.getId(), item.getId(), item.getName(), item.getPrice(), billItem.getQuantity(), billItem.getTotalPrice()));

        }

        return billItemsDto;

    }
}
