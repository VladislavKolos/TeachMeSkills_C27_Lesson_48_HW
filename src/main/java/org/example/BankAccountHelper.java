package org.example;

public class BankAccountHelper {
    public static String USD_ACCOUNT = "USD";
    public static String EUR_ACCOUNT = "EUR";
    public static String BYN_ACCOUNT = "BYN";

    public double convertCurrency(String fromCurrency, String toCurrency, double amount)
    {
        double result = amount;
        if (fromCurrency.equals(USD_ACCOUNT))
        {
            if (toCurrency.equals(EUR_ACCOUNT))
            {
                result = amount * 0.94;
            }
            else if (toCurrency.equals(BYN_ACCOUNT))
            {
                result = amount * 3.18;
            }
        }
        else if (fromCurrency.equals(EUR_ACCOUNT))
        {
            if (toCurrency.equals(USD_ACCOUNT))
            {
                result = amount * 1.07;
            }
            else if (toCurrency.equals(BYN_ACCOUNT))
            {
                result = amount * 3.4;
            }
        }
        else if (fromCurrency.equals(BYN_ACCOUNT))
        {
            if (toCurrency.equals(USD_ACCOUNT))
            {
                result = amount * 0.31;
            }
            else if (toCurrency.equals(EUR_ACCOUNT))
            {
                result = amount * 0.29;
            }
        }

        return result;
    }

    public boolean isBalanceValidForWithdraw(BankAccount account, double amount, String currency)
    {
        double desiredAmount = convertCurrency(currency, account.getAccountCurrency(), amount);

        return (account.getCurrentBalance() - desiredAmount) >= 0;
    }

    public boolean isAvailableForDailyWithdraw(BankAccount account, double amount)
    {
        return (account.getDailyLimit() - amount) >= 0;
    }
}
