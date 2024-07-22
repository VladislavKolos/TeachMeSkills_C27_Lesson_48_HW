package org.example;

public class BankAccountManager {
    protected BankAccountHelper helper;

    public BankAccountManager(BankAccountHelper helper)
    {
        this.helper = helper;
    }

    public void transferMoney(BankAccount fromAccount, BankAccount toAccount, double amount) throws InvalidBankOperationException
    {
        if (helper.isBalanceValidForWithdraw(fromAccount, amount, fromAccount.getAccountCurrency())
                && helper.isAvailableForDailyWithdraw(fromAccount, amount))
        {
            double convertedAmount = helper.convertCurrency(fromAccount.getAccountCurrency(), toAccount.getAccountCurrency(), amount);

            fromAccount.setCurrentBalance(fromAccount.getCurrentBalance() - amount);
            fromAccount.setDailyLimit(fromAccount.getDailyLimit() - amount);

            toAccount.setDailyLimit(toAccount.getDailyLimit() - convertedAmount);
        }
        else
        {
            throw new InvalidBankOperationException();
        }
    }

    public double withdrawMoney(BankAccount account, double amount, String currency) throws InvalidBankOperationException
    {
        if (helper.isBalanceValidForWithdraw(account, amount, account.getAccountCurrency())
                && helper.isAvailableForDailyWithdraw(account, amount))
        {
            double convertedAmount = helper.convertCurrency(currency, account.getAccountCurrency(), amount);

            account.setCurrentBalance(account.getCurrentBalance() - convertedAmount);
            account.setDailyLimit(account.getDailyLimit() - convertedAmount);

            return convertedAmount;
        }
        else
        {
            throw new InvalidBankOperationException();
        }
    }

    public void addMoney(BankAccount account, double amount, String currency)
    {
        double convertedAmount = helper.convertCurrency(currency, account.getAccountCurrency(), amount);

        account.setCurrentBalance(account.getCurrentBalance() + convertedAmount);
    }
}
