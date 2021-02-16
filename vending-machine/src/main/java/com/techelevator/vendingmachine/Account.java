package com.techelevator.vendingmachine;

import java.math.BigDecimal;

public class Account {

    protected BigDecimal moneyInAccount;
    private static final BigDecimal STARTING_MONEY = new BigDecimal("0.00");


    public Account(){
        moneyInAccount = STARTING_MONEY;
    }

    public boolean addMoneyToAccount(BigDecimal moneyAdded) throws IllegalArgumentException{
        boolean isMoneyAdded = false;
        if (moneyAdded.compareTo(BigDecimal.ZERO) >= 0) {
            moneyInAccount = moneyInAccount.add(moneyAdded);
            isMoneyAdded = true;
        } else {
            throw new IllegalArgumentException();
        }
        return isMoneyAdded;
    }

    public BigDecimal getAvailableMoney(){
        return this.moneyInAccount;
    }

}
