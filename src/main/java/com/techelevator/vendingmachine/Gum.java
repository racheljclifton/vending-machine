package com.techelevator.vendingmachine;

public class Gum extends Item{

    public Gum(String name, String price) {
        super(name, price);
    }

    @Override
    public String getTypeMessage() {
        return "Chew Chew, Yum!";
    }
}
