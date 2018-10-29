package com.example.parul.expensemonitor;

import java.io.Serializable;

public class ExpenseData implements Serializable
{
   private  String moneySpendOn;
   private  int amount;

    public ExpenseData(String moneySpendOn, int amount) {
        this.moneySpendOn = moneySpendOn;
        this.amount = amount;
    }

        public String getMoneySpendOn() {
            return moneySpendOn;
    }

    public int getAmount() {
        return amount;
    }
}
