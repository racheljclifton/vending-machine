package com.techelevator.vendingmachine;

import com.techelevator.util.AuditLog;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {

    Map<String, Item> inventoryMap = new LinkedHashMap<>();
    Account vendingMachineAccount = new Account();


    public void inventoryScanner(Scanner fileScanner) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] splitLine = line.split("\\|");
            String name = splitLine[1];
            String price = splitLine[2];
            String typeOfItem = splitLine[3];
            Item item;
            if(typeOfItem.equals("Chip")) {
                item = new Chip(name, price);
            } else if (typeOfItem.equals("Candy")) {
                item = new Candy(name, price);
            } else if (typeOfItem.equals("Drink")) {
                item = new Drink(name, price);
            } else if (typeOfItem.equals("Gum")) {
                item = new Gum(name, price);
            } else {
                item = new Item(name, price, typeOfItem);
            }
            inventoryMap.put(splitLine[0], item);
        }
    }

    public String displayInventoryList() {
        String items = "";
        for (Map.Entry<String, Item> item : inventoryMap.entrySet()) {
            items += "\n" + item.getKey() + " " + item.getValue().getItemInfo();
        }
        return items;
    }

    public boolean doesSlotExist(String slotChoice) {
        boolean result = false;
        if (inventoryMap.containsKey(slotChoice)) {
            result = true;
        }
        return result;
    }

    public String getItemAndPay(String slotChoice, CustomerAccount customerAccount) {
        String message;
        Item itemChoice = inventoryMap.get(slotChoice);
        if (itemChoice.removeOneFromInventory()) {
            BigDecimal itemPrice = itemChoice.getPrice();
            if (customerAccount.removeMoneyFromAccount(itemPrice)) {
                vendingMachineAccount.addMoneyToAccount(itemPrice);
                AuditLog.log(itemChoice.getName() + " " + slotChoice + " $" + itemPrice + " $" + customerAccount.getAvailableMoney());
                message = itemChoice.getTypeMessage() + "\nRemaining balance: $" + customerAccount.getAvailableMoney();
            } else {
                message = "Insufficient funds. Please deposit more money or make a different selection. " +
                        "\nCurrent balance: $" + customerAccount.getAvailableMoney();
            }
        } else {
            message = "Item is sold out, please make another selection.";
        }
        return message;
    }


//    public String salesReport() {
//        String salesReport = "";
//        for (Map.Entry<String, Item> item : inventoryMap.entrySet()) {
//            salesReport += item.getValue().getName() + "|" + item.getValue().getNumberSold() + "\n";
//        }
//        BigDecimal totalSales = vendingMachineAccount.getAvailableMoney();
//        salesReport += "**TOTAL SALES** $" + totalSales;
//        return salesReport;
//    }

    public String salesReport(String openTime) {
        String message;
        try (PrintWriter writer = new PrintWriter("logs/salesreport.log")) {
            writer.println("Sales Report for Vending Machine opened " + openTime + "\n");
            for (Map.Entry<String, Item> item : inventoryMap.entrySet()) {
                writer.println(item.getValue().getName() + "|" + item.getValue().getNumberSold());
            }
            BigDecimal totalSales = vendingMachineAccount.getAvailableMoney();
            writer.println("\n**TOTAL SALES** $" + totalSales);
            message = "Sales Report has been created.";
        } catch (Exception e) {
            message = "There has been an error creating the Sales Report.";
        }
        return message;
    }






}
