package com.techelevator.vendingmachine;

import java.math.BigDecimal;

public class Item {

    private String name;
    private BigDecimal price;
    private String typeOfItem;
    private int availableInventory;
    private static final int STARTING_INVENTORY = 5;


    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getTypeOfItem() {
        return typeOfItem;
    }
    public int getAvailableInventory() {
        return availableInventory;
    }

    private void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

    public Item (String name, String price, String typeOfItem) {
        this.name = name;
        this.setPrice(price);
        this.typeOfItem = typeOfItem;
        availableInventory = STARTING_INVENTORY;
    }

    public Item (String name, String price) {
        this.name = name;
        this.setPrice(price);
        availableInventory = STARTING_INVENTORY;
    }

    public String getItemInfo() {
        String name = this.getName();
        BigDecimal price = this.getPrice();
        int availableInventory = this.getAvailableInventory();
        String display = name + " " + price;
        if (availableInventory <= 0) {
            display += " SOLD OUT";
        }
        return display;
    }

    public boolean removeOneFromInventory() {
        boolean isOneRemovedFromInventory = false;
        if (availableInventory > 0) {
            availableInventory--;
            isOneRemovedFromInventory = true;
        }
        return isOneRemovedFromInventory;
    }

    public String getTypeMessage() {
        return "Yum";
    }

    public int getNumberSold() {
        return STARTING_INVENTORY - availableInventory;
    }

}
