package com.hasitha.back_end.billCreate;

import java.util.List;

/**
 * Request DTO for creating a bill. Contains customer ID and list of items to be
 * included in the bill.
 */
public class CreateBillRequest {

    /**
     * ID of the user whom creating the bill.
     */
    private int userId;

    /**
     * ID of the customer for whom the bill is being created.
     */
    private int customerId;

    /**
     * List of items to be added in the bill.
     */
    private List<BillItemRequest> items;

    /**
     * Inner static class representing a single item in the bill request.
     */
    public static class BillItemRequest {

        /**
         * ID of the item being billed.
         */
        private int itemId;

        /**
         * Quantity of the item being billed. The unit price is fetched on the
         * server based on item ID.
         */
        private int quantity;

        /**
         * Gets the item ID.
         *
         * @return item ID
         */
        public int getItemId() {
            return itemId;
        }

        /**
         * Sets the item ID.
         *
         * @param itemId the ID of the item
         */
        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        /**
         * Gets the quantity of the item.
         *
         * @return quantity
         */
        public int getQuantity() {
            return quantity;
        }

        /**
         * Sets the quantity of the item.
         *
         * @param quantity the quantity to set
         */
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    /**
     * Gets the user ID.
     *
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the ID of the user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the customer ID.
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     *
     * @param customerId the ID of the customer
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the list of bill item requests.
     *
     * @return list of items
     */
    public List<BillItemRequest> getItems() {
        return items;
    }

    /**
     * Sets the list of bill item requests.
     *
     * @param items the list of bill items to set
     */
    public void setItems(List<BillItemRequest> items) {
        this.items = items;
    }
}
