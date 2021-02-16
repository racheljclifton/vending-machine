package com.techelevator.vendingmachine;

public class Chip extends Item{

    public Chip(String name, String price) {
        super(name, price);
    }

    @Override
    public String getTypeMessage() {
        return "Crunch Crunch, Yum!";
    }
}
