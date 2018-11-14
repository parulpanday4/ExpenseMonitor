package com.example.parul.expensemonitor;

import java.io.Serializable;


public class ExpenseData implements Serializable
{
    private int sno;
   private  String moneySpendOn;
   private  int amount;
   private int day;
   private int year;
   private String month;

    public ExpenseData(String moneySpendOn, int amount, int day, int year, String month) {

        this.moneySpendOn = moneySpendOn;
        this.amount = amount;
        this.day = day;
        this.year = year;
        this.month = month;
    }

    public String getMoneySpendOn() {
        return moneySpendOn;
    }

    public int getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }


    public ExpenseData(int sno, String moneySpendOn, int amount, int day, int year, String month) {
        this.sno = sno;
        this.moneySpendOn = moneySpendOn;
        this.amount = amount;
        this.day = day;
        this.year = year;
        this.month = month;

    }

    public int getSno() {
        return sno;
    }
}
