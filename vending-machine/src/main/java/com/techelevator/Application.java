package com.techelevator;

import com.techelevator.util.AuditLog;
import com.techelevator.vendingmachine.*;
import com.techelevator.view.Menu;
import com.techelevator.view.MenuDrivenCLI;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Application {

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed money into vending machine";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish transaction";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String MAIN_MENU_OPTION_SALES_REPORT = Menu.HIDDEN_OPTION;
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};
    private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
    private String openTime;

    VendingMachine vendingMachine = new VendingMachine();

    CustomerAccount customerAccount = new CustomerAccount();

    private final MenuDrivenCLI ui = new MenuDrivenCLI();


    public static void main(String[] args) {
        Application application = new Application();
        application.importInventory();
        application.openTime = AuditLog.saveOpenTime();
        application.run();
    }

    public void run() {
        boolean finished = false;
        while (!finished) {
            String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);
            if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
                displayItems();
                ui.pauseOutput();
            } else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
                openPurchaseMenu();
            } else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {
                finished = true;
            } else if (selection.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
                printSalesReport();
            }
        }
    }

    public void openPurchaseMenu() {
        boolean finished = false;
        while (!finished) {
            String selectionProductMenu = ui.promptForSelection(PURCHASE_MENU_OPTIONS);
            if (selectionProductMenu.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
                feedMoneyToMachine();
            } else if (selectionProductMenu.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
                if (isAccountGreaterThanZero()) {
                    displayItems();
                    selectProduct();
                }
            } else if (selectionProductMenu.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
                returnCustomerChange();
                finished = true;
            }
        }
    }

    public void importInventory() {
        boolean finished = false;
        while (!finished) {
            String userInput = ui.promptForString("Enter the fully qualified name of the file to read in your inventory? ");
            Path myPath = Path.of(userInput);
            try (Scanner fileScanner = new Scanner(myPath)) {
                vendingMachine.inventoryScanner(fileScanner);
                finished = true;
            } catch (IOException e) {
                ui.output("Cannot read file\n");
            }
        }
    }

    public void displayItems() {
        ui.listOutput(vendingMachine.displayInventoryList());
    }


    public void feedMoneyToMachine() {
        String depositAmount = ui.promptForString("\nPlease enter the amount you would like to feed (bills only): ");
        try {
            int moneyAdded = Integer.parseInt(depositAmount);
            customerAccount.addMoneyToAccount(moneyAdded);
            ui.output("Money to spend: $" + customerAccount.getAvailableMoney());
        } catch (NumberFormatException e) {
            ui.output("Sorry we could not process your money. Please try again and enter number values, no decimals (e.g. 5 for $5.00 deposit)");
            ui.pauseOutput();
        } catch (IllegalArgumentException e) {
            ui.output("Sorry your deposit cannot be negative. Please try again.");
            ui.pauseOutput();
        }
    }

    public boolean isAccountGreaterThanZero() {
        boolean isAccountGreaterThanZero = false;
        if (customerAccount.isAccountLessThanOrEqualToZero()) {
            ui.output("No funds available. Please deposit money before making a selection.");
            ui.pauseOutput();
        } else {
            isAccountGreaterThanZero = true;
        }
        return isAccountGreaterThanZero;
    }

    public void selectProduct() {
        String slotChoice = ui.promptForString("\nPlease enter the slot ID of the item you would like to purchase: ");
        slotChoice = slotChoice.toUpperCase();
        if (vendingMachine.doesSlotExist(slotChoice)) {
            ui.output(vendingMachine.getItemAndPay(slotChoice, customerAccount));
            ui.pauseOutput();
        } else {
            ui.output("Sorry we could not process your choice, please try again and enter the slot ID of the item (e.g. A1).");
            ui.pauseOutput();
        }
    }

    public void returnCustomerChange() {
        ui.output(customerAccount.returnChange());
        ui.pauseOutput();
    }

    public void printSalesReport() {
        ui.output(vendingMachine.salesReport(openTime));
        ui.pauseOutput();
    }

}
