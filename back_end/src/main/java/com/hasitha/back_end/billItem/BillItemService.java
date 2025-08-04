package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;
import java.util.List;

/**
 * Service class for handling business logic related to Bill Items. Provides
 * operations such as saving and retrieving bill items, with validation and
 * exception handling.
 */
public class BillItemService {

    private final BillItemDAO billItemDao;

    /**
     * Constructor to inject a custom BillItemDAO (useful for testing or
     * alternative implementations).
     *
     * @param billItemDao the data access object for bill items
     */
    public BillItemService(BillItemDAO billItemDao) {
        this.billItemDao = billItemDao;
    }

    /**
     * Default constructor that initializes the service with the default DAO
     * implementation.
     */
    public BillItemService() {
        this.billItemDao = new BillItemDAOImpl();
    }

    /**
     * Saves a list of bill items to the database for a specific bill ID.
     * Performs validation on bill ID and each item.
     *
     * @param billId the ID of the bill to which items belong
     * @param items the list of bill items to save
     * @throws ValidationException if billId is invalid or items are invalid
     */
    public void saveBillItems(int billId, List<BillItem> items) {
        if (billId <= 0) {
            throw new ValidationException("Invalid bill ID: must be a positive integer.");
        }
        validateBillItems(items);
        billItemDao.saveItems(billId, items);
    }

    /**
     * Retrieves the list of bill items for a given bill ID.
     *
     * @param billId the ID of the bill
     * @return list of bill items for the specified bill
     * @throws ValidationException if the billId is invalid
     * @throws NotFoundException if no items are found for the given billId
     */
    public List<BillItem> getBillItemsByBillId(int billId) {
        if (billId <= 0) {
            throw new ValidationException("Invalid bill ID: must be a positive integer.");
        }
        List<BillItem> billItems = billItemDao.findByBillId(billId);
        if (billItems == null || billItems.isEmpty()) {
            throw new NotFoundException("No items found for bill ID: " + billId);
        }
        return billItems;
    }

    /**
     * Validates the list of bill items before saving. Ensures all required
     * fields are set and logically correct.
     *
     * @param items list of bill items to validate
     * @throws ValidationException if any item is invalid or the list is empty
     */
    private void validateBillItems(List<BillItem> items) {
        if (items == null || items.isEmpty()) {
            throw new ValidationException("A bill must contain at least one item.");
        }

        for (BillItem item : items) {
            if (item.getItemId() <= 0) {
                throw new ValidationException("Item ID must be a positive integer.");
            }
            if (item.getQuantity() <= 0) {
                throw new ValidationException("Quantity must be greater than 0 for item ID: " + item.getItemId());
            }
            if (item.getSubTotal() < 0) {
                throw new ValidationException("Subtotal cannot be negative for item ID: " + item.getItemId());
            }
        }
    }
}
