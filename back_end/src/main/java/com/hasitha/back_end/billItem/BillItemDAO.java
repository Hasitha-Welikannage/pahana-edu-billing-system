package com.hasitha.back_end.billItem;

import java.util.List;

/**
 * Data Access Object interface for managing BillItem entities.
 */
public interface BillItemDAO {

    /**
     * Saves a list of BillItems associated with a specific bill ID.
     *
     * @param billId the ID of the bill to which these items belong
     * @param items the list of BillItem objects to save
     */
    public void saveItems(int billId, List<BillItem> items);

    /**
     * Retrieves the list of BillItems associated with a specific bill ID.
     *
     * @param billId the ID of the bill whose items to retrieve
     * @return List of BillItem objects related to the bill
     */
    public List<BillItem> findByBillId(int billId);
}
