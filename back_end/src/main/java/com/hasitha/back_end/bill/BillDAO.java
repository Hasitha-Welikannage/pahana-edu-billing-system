package com.hasitha.back_end.bill;

import java.util.List;

/**
 * Data Access Object interface for performing CRUD operations on bills.
 */
public interface BillDAO {

    /**
     * Persists a new bill in the database.
     *
     * @param bill the bill object to be created, including customerId, userId,
     * date, total, and optionally bill items
     * @return the created Bill object, possibly with generated fields like ID
     * or timestamps populated
     */
    Bill create(Bill bill);

    /**
     * Retrieves all bills from the database.
     *
     * @return a list of all Bill objects
     */
    List<Bill> findAll();

    /**
     * Finds a specific bill by its unique ID.
     *
     * @param id the ID of the bill to retrieve
     * @return the Bill object with the given ID, or null if not found
     */
    Bill findById(int id);
}
