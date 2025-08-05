package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.ValidationException;
import com.hasitha.back_end.exceptions.NotFoundException;  // Assuming this exists
import java.util.List;

/**
 * Service layer for handling Bill-related business logic and validation.
 */
public class BillService {

    private final BillDAO billDao;

    /**
     * Constructs BillService with a specified BillDAO.
     *
     * @param billDao the BillDAO implementation to use
     */
    public BillService(BillDAO billDao) {
        this.billDao = billDao;
    }

    /**
     * Default constructor initializes with BillDAOImpl.
     */
    public BillService() {
        this.billDao = new BillDAOImpl();
    }

    /**
     * Creates a new Bill after validating its fields.
     *
     * @param bill the Bill to create
     * @return the created Bill with generated ID
     * @throws ValidationException if the bill is invalid
     */
    public Bill create(Bill bill) {
        validateBill(bill);
        return billDao.create(bill);
    }

    /**
     * Retrieves all bills from the data source.
     *
     * @return list of all bills
     * @throws NotFoundException if no bills are found
     */
    public List<Bill> findAll() {
        List<Bill> list = billDao.findAll();

        if (list == null || list.isEmpty()) {
            throw new NotFoundException("No bills found.");
        }

        return list;
    }

    /**
     * Retrieves a Bill by its ID.
     *
     * @param id the bill ID
     * @return the Bill object
     * @throws NotFoundException if no bill with the given ID exists
     */
    public Bill findById(int id) {
        Bill bill = billDao.findById(id);

        if (bill == null) {
            throw new NotFoundException("Bill with ID " + id + " not found.");
        }

        return bill;
    }

    /**
     * Validates required fields of the given Bill.
     *
     * @param bill the Bill object to validate
     * @throws ValidationException if validation fails
     */
    private void validateBill(Bill bill) {
        if (bill == null) {
            throw new ValidationException("Bill cannot be null.");
        }
        if (bill.getCustomerId() <= 0) {
            throw new ValidationException("Customer ID must be a positive integer.");
        }
        if (bill.getUserId() <= 0) {
            throw new ValidationException("User ID must be a positive integer.");
        }
        if (bill.getTotal() == 0) {
            throw new ValidationException("Total price must be greater than zero.");
        }
        if (bill.getTotal() < 0) {
            throw new ValidationException("Total price cannot be negative.");
        }
    }
}
