package com.techelevator.vendingmachine;

public class Drink extends Item{

    public Drink(String name, String price) {
        super(name, price);
    }

    @Override
    public String getTypeMessage() {
        return "Glug Glug, Yum!";
    }
}
