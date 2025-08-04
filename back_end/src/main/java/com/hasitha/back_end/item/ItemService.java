package com.hasitha.back_end.item;

import com.hasitha.back_end.exceptions.NotFoundException;
import com.hasitha.back_end.exceptions.ValidationException;

import java.util.List;

/**
 * Service class for managing business logic related to items.
 *
 * This class acts as a middle layer between the controller/resource layer and
 * the data access layer. It handles validation and error handling.
 */
public class ItemService {

    // DAO used for database operations
    ItemDAO itemDao = new ItemDAOImpl();

    /**
     * Retrieves all items.
     *
     * @return a list of items
     * @throws NotFoundException if no items are found
     */
    public List<Item> findAll() {
        List<Item> itemList = itemDao.findAll();

        if (itemList == null || itemList.isEmpty()) {
            throw new NotFoundException("items can not be found");
        }

        return itemList;
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id the item ID
     * @return the item with the specified ID
     * @throws NotFoundException if the item does not exist
     */
    public Item findById(int id) {
        Item item = itemDao.findById(id);

        if (item == null) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        return item;
    }

    /**
     * Creates a new item after validating it.
     *
     * @param item the item to create
     * @return the newly created item
     * @throws ValidationException if the item is invalid
     */
    public Item create(Item item) {
        validateItem(item);
        return itemDao.create(item);
    }

    /**
     * Updates an existing item.
     *
     * @param id the ID of the item to update
     * @param item the updated item data
     * @return the updated item
     * @throws NotFoundException if the item does not exist
     * @throws ValidationException if the updated data is invalid
     */
    public Item update(int id, Item item) {
        if (!exists(id)) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        validateItem(item);
        item.setId(id); // Ensure ID consistency

        return itemDao.update(id, item);
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id the ID of the item to delete
     * @throws NotFoundException if the item does not exist
     */
    public void delete(int id) {
        if (!exists(id)) {
            throw new NotFoundException("Item with ID " + id + " does not exist.");
        }

        itemDao.delete(id);
    }

    /**
     * Gets the price of an item by its ID.
     *
     * @param id the ID of the item
     * @return the unit price of the item
     * @throws NotFoundException if the item is not found
     */
    public double getPriceById(int id) {
        Item item = itemDao.findById(id);

        if (item == null) {
            throw new NotFoundException("Item with ID " + id + " not found.");
        }

        return item.getPrice();
    }

    /**
     * Checks if an item exists by its ID.
     *
     * @param id the ID to check
     * @return true if the item exists, false otherwise
     */
    public boolean exists(int id) {
        Item item = itemDao.findById(id);
        return item != null;
    }

    /**
     * Validates item fields before saving or updating.
     *
     * @param item the item to validate
     * @throws ValidationException if any field is invalid
     */
    private void validateItem(Item item) {
        if (item == null) {
            throw new ValidationException("Item cannot be null.");
        }

        if (item.getName() == null || item.getName().trim().isEmpty()) {
            throw new ValidationException("Item name is required.");
        }

        if (item.getPrice() == 0) {
            throw new ValidationException("Item price cannot be zero.");
        }

        if (item.getPrice() < 0) {
            throw new ValidationException("Item price cannot be negative.");
        }
    }
}
