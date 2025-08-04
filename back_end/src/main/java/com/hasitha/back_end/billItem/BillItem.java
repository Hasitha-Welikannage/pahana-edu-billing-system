package com.hasitha.back_end.billItem;

/**
 * Represents an item in a bill, containing details such as item ID, quantity,
 * and total price.
 */
public class BillItem {

    /**
     * Unique identifier of the bill item (primary key).
     */
    private int id;

    /**
     * Identifier of the bill to which this item belongs (foreign key).
     */
    private int billId;

    /**
     * Identifier of the item being billed (foreign key).
     */
    private int itemId;

    /**
     * Quantity of the item purchased.
     */
    private int quantity;

    /**
     * Total price for the quantity of this item (usually quantity × unit
     * price).
     */
    private double subTotal;

    /**
     * Default no-argument constructor.
     */
    public BillItem() {
    }

    /**
     * Constructor with all fields including the ID.
     *
     * @param id the ID of the bill item
     * @param billId the associated bill ID
     * @param itemId the ID of the item
     * @param quantity the quantity purchased
     * @param totalPrice the total price for this item
     */
    public BillItem(int id, int billId, int itemId, int quantity, double totalPrice) {
        this.id = id;
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subTotal = totalPrice;
    }

    /**
     * Constructor excluding the bill item ID (used for inserts).
     *
     * @param billId the associated bill ID
     * @param itemId the ID of the item
     * @param quantity the quantity purchased
     * @param totalPrice the total price for this item
     */
    public BillItem(int billId, int itemId, int quantity, double totalPrice) {
        this.billId = billId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.subTotal = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "BillItem{"
                + "itemId=" + itemId
                + ", quantity=" + quantity
                + ", totalPrice=" + subTotal
                + '}';
    }
}
