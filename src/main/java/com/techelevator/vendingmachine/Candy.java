package com.techelevator.vendingmachine;

public class Candy extends Item{

    public Candy(String name, String price) {
        super(name, price);
    }

    @Override
    public String getTypeMessage() {
        return "Munch Munch, Yum!";
    }
}
