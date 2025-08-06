package com.hasitha.back_end.billCreate;

import com.hasitha.back_end.bill.Bill;
import com.hasitha.back_end.bill.BillDTO;
import com.hasitha.back_end.bill.BillService;
import com.hasitha.back_end.billCreate.CreateBillRequest.BillItemRequest;
import com.hasitha.back_end.billItem.BillItem;
import com.hasitha.back_end.billItem.BillItemDTO;
import com.hasitha.back_end.billItem.BillItemService;
import com.hasitha.back_end.customer.Customer;
import com.hasitha.back_end.customer.CustomerService;
import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.item.Item;
import com.hasitha.back_end.item.ItemService;
import com.hasitha.back_end.user.User;
import com.hasitha.back_end.user.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for handling bill creation and retrieval logic.
 */
public class BillCreateService {

    private final ItemService itemService;
    private final CustomerService customerService;
    private final UserService userService;
    private final BillService billService;
    private final BillItemService billItemService;

    /**
     * Constructor with dependency injection.
     */
    public BillCreateService(ItemService itemService, CustomerService customerService, UserService userService, BillService billService, BillItemService billItemService) {
        this.itemService = itemService;
        this.customerService = customerService;
        this.userService = userService;
        this.billService = billService;
        this.billItemService = billItemService;
    }

    /**
     * Default constructor with default service initializations.
     */
    public BillCreateService() {
        this.itemService = new ItemService();
        this.customerService = new CustomerService();
        this.userService = new UserService();
        this.billService = new BillService();
        this.billItemService = new BillItemService();
    }

    /**
     * Creates a bill from the provided request and user.
     *
     * @param userId ID of the user creating the bill.
     * @param req Request object containing customer ID and bill items.
     * @return Created {@link BillDTO} containing full bill details.
     * @throws ValidationException if input data is invalid.
     */
    public BillDTO createBill(CreateBillRequest req) {
        Bill bill;
        List<BillItem> billItems = new ArrayList<>();
        List<BillItemDTO> billItemDtos = new ArrayList<>();
        double grandTotal = 0;

        validateCreateRequest(req);

        // Calculate total and prepare bill items
        for (BillItemRequest itemReq : req.getItems()) {
            double unitPrice = itemService.getPriceById(itemReq.getItemId());
            double subtotal = unitPrice * itemReq.getQuantity();
            grandTotal += subtotal;
            billItems.add(new BillItem(itemReq.getItemId(), itemReq.getQuantity(), subtotal));
        }

        // Create bill and save items
        bill = billService.create(new Bill(req.getCustomerId(), req.getUserId(), new Date(), grandTotal));
        billItemService.saveBillItems(bill.getId(), billItems);

        // Retrieve and map saved bill items
        bill.setItems(billItemService.getBillItemsByBillId(bill.getId()));
        for (BillItem billItem : bill.getItems()) {
            billItemDtos.add(convertToBillItemDTO(billItem));
        }

        Customer customer = customerService.findById(req.getCustomerId());
        User user = userService.findById(req.getUserId());

        return new BillDTO(bill.getId(), customer, user, bill.getDate(), bill.getTotal(), billItemDtos);
    }

    /**
     * Returns all bills with basic customer and user information.
     *
     * @return List of {@link BillDTO} without bill items.
     */
    public List<BillDTO> getAllBills() {
        List<Bill> bills = billService.findAll();
        List<BillDTO> billDtos = new ArrayList<>();
        for (Bill bill : bills) {
            Customer customer = customerService.findById(bill.getCustomerId());
            User user = userService.findById(bill.getUserId());
            billDtos.add(new BillDTO(bill.getId(), customer, user, bill.getDate(), bill.getTotal()));
        }
        return billDtos;
    }

    /**
     * Returns list of items for a specific bill.
     *
     * @param billId ID of the bill.
     * @return List of {@link BillItemDTO} for the bill.
     */
    public List<BillItemDTO> getBillItemsByBillId(int billId) {
        List<BillItem> billItems = billItemService.getBillItemsByBillId(billId);
        List<BillItemDTO> billItemDtos = new ArrayList<>();

        for (BillItem billItem : billItems) {
            billItemDtos.add(convertToBillItemDTO(billItem));
        }
        return billItemDtos;
    }

    public BillDTO getBill(int id) {

        Bill bill = billService.findById(id);
        List<BillItemDTO> billItems = getBillItemsByBillId(id);

        Customer customer = customerService.findById(bill.getCustomerId());
        User user = userService.findById(bill.getUserId());

        return new BillDTO(bill.getId(), customer, user, bill.getDate(), bill.getTotal(), billItems);
    }

    /**
     * Validates the request object before bill creation.
     *
     * @param req The {@link CreateBillRequest} to validate.
     * @throws ValidationException if any rule is violated.
     */
    private void validateCreateRequest(CreateBillRequest req) {
        userService.ensureUserExists(req.getUserId());
        customerService.ensureCustomerExists(req.getCustomerId());
        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new ValidationException("Bill must contain at least one item.");
        }
        for (BillItemRequest dto : req.getItems()) {
            if (dto.getQuantity() <= 0) {
                throw new ValidationException("Quantity must be > 0 for item " + dto.getItemId());
            }
            itemService.ensureItemExists(dto.getItemId());
        }
    }

    /**
     * Maps a {@link BillItem} to a {@link BillItemDTO}, including item details.
     *
     * @param billItem The bill item to map.
     * @return Mapped DTO.
     */
    private BillItemDTO convertToBillItemDTO(BillItem billItem) {
        Item item = itemService.findById(billItem.getItemId());
        return new BillItemDTO(
                billItem.getId(),
                item.getId(),
                item.getName(),
                item.getPrice(),
                billItem.getQuantity(),
                billItem.getSubTotal()
        );
    }
}
