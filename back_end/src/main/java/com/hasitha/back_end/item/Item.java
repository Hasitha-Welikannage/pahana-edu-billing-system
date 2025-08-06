package com.hasitha.back_end.item;

/**
 * Represents an item in the system, such as a product in a bookshop or
 * inventory.
 *
 * Each item has: - a unique ID - a name - a unit price - a stock quantity
 */
public class Item {

    // Unique identifier for the item
    private int id;

    // Name of the item
    private String name;

    // Price per unit of the item
    private double price;

    // Available quantity in stock
    private int stock;

    /**
     * Default constructor. Needed for frameworks like Jackson or JPA that
     * require a no-arg constructor.
     */
    public Item() {
    }

    /**
     * Parameterized constructor to create an item without id.
     *
     * @param name the item name
     * @param unitPrice the price per unit
     * @param stock the quantity available in stock
     */
    public Item(String name, double unitPrice, int stock) {
        this.name = name;
        this.price = unitPrice;
        this.stock = stock;
    }

    /**
     * Parameterized constructor to create an item with all fields.
     *
     * @param id the item ID
     * @param name the item name
     * @param unitPrice the price per unit
     * @param stock the quantity available in stock
     */
    public Item(int id, String name, double unitPrice, int stock) {
        this.id = id;
        this.name = name;
        this.price = unitPrice;
        this.stock = stock;
    }

    /**
     * Returns the ID of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unit price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the unit price of the item.
     */
    public void setPrice(double unitPrice) {
        this.price = unitPrice;
    }

    /**
     * Returns the stock quantity available for the item.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity for the item.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns a string representation of the item object.
     */
    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", unitPrice=" + price
                + ", stock=" + stock
                + '}';
    }
}
