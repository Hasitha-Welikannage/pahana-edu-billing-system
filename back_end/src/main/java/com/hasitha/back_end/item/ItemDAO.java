package com.hasitha.back_end.item;

import java.util.List;

/**
 * Interface that defines the contract for performing CRUD operations on Item
 * entities.
 *
 * Implementations of this interface will handle database interactions for the
 * Item model.
 */
public interface ItemDAO {

    /**
     * Retrieves all items from the data source.
     *
     * @return a list of all items
     */
    public List<Item> findAll();

    /**
     * Finds a single item by its ID.
     *
     * @param id the unique ID of the item
     * @return the item if found, or null if not found
     */
    public Item findById(int id);

    /**
     * Creates a new item in the data source.
     *
     * @param item the item to be created
     * @return the created item with its generated ID
     */
    public Item create(Item item);

    /**
     * Updates an existing item in the data source.
     *
     * @param id the ID of the item to update
     * @param item the updated item data
     * @return the updated item if the update was successful, or null otherwise
     */
    public Item update(int id, Item item);

    /**
     * Deletes an item from the data source.
     *
     * @param id the ID of the item to delete
     */
    public void delete(int id);
}
