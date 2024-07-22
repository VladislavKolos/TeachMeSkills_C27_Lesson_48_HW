package org.example;

public class BankAccount {
    private String accountNumber;
    private String personName;
    private double currentBalance;
    private String accountCurrency;
    private double dailyLimit;

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    public double getCurrentBalance()
    {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance)
    {
        this.currentBalance = currentBalance;
    }

    public String getAccountCurrency()
    {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency)
    {
        this.accountCurrency = accountCurrency;
    }

    public double getDailyLimit()
    {
        return dailyLimit;
    }

    public void setDailyLimit(double dailyLimit)
    {
        this.dailyLimit = dailyLimit;
    }
}
