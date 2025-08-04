package com.hasitha.back_end.billItem;

/**
 * Data Transfer Object (DTO) representing a single item in a bill. This class
 * is used to send item data between the backend and the frontend.
 */
public class BillItemDTO {

    private int id;             // Unique identifier of the bill item (optional)
    private int itemId;         // ID of the item from the items table
    private String itemName;    // Name of the item
    private double unitPrice;   // Price per unit of the item
    private int quantity;       // Quantity of the item purchased
    private double subTotal;    // Total price (unitPrice * quantity)

    // Default constructor
    public BillItemDTO() {
    }

    /**
     * Constructor with all fields.
     *
     * @param id the bill item ID
     * @param itemId the ID of the item
     * @param itemName name of the item
     * @param unitPrice price per unit
     * @param quantity quantity purchased
     * @param subTotal total cost (unitPrice * quantity)
     */
    public BillItemDTO(int id, int itemId, String itemName, double unitPrice, int quantity, double subTotal) {
        this.id = id;
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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
}
