package com.techelevator.vendingmachine;

import com.techelevator.util.AuditLog;

import java.math.BigDecimal;

public class CustomerAccount extends Account {

    private int numberOfDollars;
    private int numberOfQuarters;
    private int numberOfDimes;
    private int numberOfNickels;

    public int getNumberOfDollars() {
        return numberOfDollars;
    }

    public int getNumberOfQuarters() {
        return numberOfQuarters;
    }

    public int getNumberOfDimes() {
        return numberOfDimes;
    }

    public int getNumberOfNickels() {
        return numberOfNickels;
    }

    public void addMoneyToAccount(int moneyAdded) throws IllegalArgumentException {
        BigDecimal depositAmount = BigDecimal.valueOf(moneyAdded);
        if (super.addMoneyToAccount(depositAmount)) {
            AuditLog.log("FEED MONEY: $" + moneyAdded + ".00 $" + getAvailableMoney());
        }
    }

    public boolean removeMoneyFromAccount(BigDecimal costOfItem) {
        boolean isMoneyRemoved = false;
        if (moneyInAccount.compareTo(costOfItem) >= 0) {
            moneyInAccount = moneyInAccount.subtract(costOfItem);
            isMoneyRemoved = true;
        }
        return  isMoneyRemoved;
    }

    public boolean isAccountLessThanOrEqualToZero() {
        boolean isAccountBalanceZeroOrNegative = false;
        if (getAvailableMoney().compareTo(BigDecimal.ZERO) <= 0) {
            isAccountBalanceZeroOrNegative = true;
        }
        return isAccountBalanceZeroOrNegative;
    }


    public String returnChange() {
        BigDecimal changeToBeGiven = getAvailableMoney();
        String changeMessage;
        if (changeToBeGiven.compareTo(BigDecimal.ZERO) > 0) {
            changeMessage = "Thank you! Your change is $" + changeToBeGiven + ". You have received:\n";
            calculateChange();
            if (getNumberOfDollars() > 0) {
                changeMessage += getNumberOfDollars() + " Dollar(s)\n";
            }
            if (getNumberOfQuarters() > 0) {
                changeMessage += getNumberOfQuarters() + " Quarter(s)\n";
            }
            if (getNumberOfDimes() > 0) {
                changeMessage += getNumberOfDimes() + " Dime(s)\n";
            }
            if (getNumberOfNickels() > 0) {
                changeMessage += getNumberOfNickels() + " Nickel(s)";
            }
            removeMoneyFromAccount(changeToBeGiven);
            AuditLog.log("GIVE CHANGE: $" + changeToBeGiven + " $" + getAvailableMoney());
        } else {
            changeMessage = "Thank you!\nYou don't have any change to return.";
        }
        return changeMessage;
    }

    public void calculateChange() {
        BigDecimal dollar = new BigDecimal("1.00");
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
        BigDecimal[] dollarMath = getAvailableMoney().divideAndRemainder(dollar);
        numberOfDollars = dollarMath[0].intValue();
        BigDecimal[] quarterMath = dollarMath[1].divideAndRemainder(quarter);
        numberOfQuarters = quarterMath[0].intValue();
        BigDecimal[] dimeMath = quarterMath[1].divideAndRemainder(dime);
        numberOfDimes = dimeMath[0].intValue();
        BigDecimal[] nickelMath = dimeMath[1].divideAndRemainder(nickel);
        numberOfNickels = nickelMath[0].intValue();
    }
}
