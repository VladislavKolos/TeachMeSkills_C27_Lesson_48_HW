package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountManagerTest {

    @InjectMocks
    private BankAccountManager bankAccountManager;

    @Mock
    private BankAccountHelper mockBankAccountHelper;

    private static final BankAccount fromAccount = createAccount(BankAccountHelper.USD_ACCOUNT, 350, 700);
    private static final BankAccount toAccount = createAccount(BankAccountHelper.BYN_ACCOUNT, 500, 1000);
    private static final BankAccount account = createAccount(BankAccountHelper.EUR_ACCOUNT, 300, 500);

    @Test
    public void testTransferMoneySuccess() throws InvalidBankOperationException {
        double amount = 150.00;

        when(mockBankAccountHelper.isBalanceValidForWithdraw(fromAccount, amount,
                BankAccountHelper.USD_ACCOUNT)).thenReturn(true);
        when(mockBankAccountHelper.isAvailableForDailyWithdraw(fromAccount, amount)).thenReturn(true);
        when(mockBankAccountHelper.convertCurrency(fromAccount.getAccountCurrency(), toAccount.getAccountCurrency(),
                amount)).thenReturn(477.00);

        bankAccountManager.transferMoney(fromAccount, toAccount, amount);

        assertEquals(550.00, fromAccount.getCurrentBalance());
        assertEquals(200, fromAccount.getDailyLimit());
        assertEquals(23, toAccount.getDailyLimit());
    }

    @Test
    public void testTransferMoneyFailed() {
        double amount = 550.00;

        when(mockBankAccountHelper.isBalanceValidForWithdraw(fromAccount,
                amount,
                BankAccountHelper.USD_ACCOUNT)).thenReturn(false);

        assertThrows(InvalidBankOperationException.class,
                () -> bankAccountManager.transferMoney(fromAccount, toAccount, amount));
    }

    @Test
    public void testWithdrawMoneySuccess() throws InvalidBankOperationException {
        double amount = 155.35;

        when(mockBankAccountHelper.isBalanceValidForWithdraw(account,
                amount,
                BankAccountHelper.EUR_ACCOUNT)).thenReturn(
                true);
        when(mockBankAccountHelper.isAvailableForDailyWithdraw(account, amount)).thenReturn(true);
        when(mockBankAccountHelper.convertCurrency(account.getAccountCurrency(),
                BankAccountHelper.EUR_ACCOUNT,
                amount)).thenReturn(155.35);

        bankAccountManager.withdrawMoney(account, amount, account.getAccountCurrency());

        assertEquals(144.65, account.getDailyLimit());
        assertEquals(344.65, account.getCurrentBalance());
    }

    @Test
    public void testWithdrawMoneyFailed() {
        double amount = 750.00;

        when(mockBankAccountHelper.isBalanceValidForWithdraw(account,
                amount,
                BankAccountHelper.EUR_ACCOUNT)).thenReturn(
                false);

        assertThrows(InvalidBankOperationException.class,
                () -> bankAccountManager.withdrawMoney(account, amount, BankAccountHelper.EUR_ACCOUNT));

    }

    @Test
    public void testAddMoney() {
        double amount = 150.00;

        when(mockBankAccountHelper.convertCurrency(fromAccount.getAccountCurrency(),
                toAccount.getAccountCurrency(),
                amount)).thenReturn(477.00);

        bankAccountManager.addMoney(toAccount, amount, BankAccountHelper.USD_ACCOUNT);

        assertEquals(1477, toAccount.getCurrentBalance());
    }

    private static BankAccount createAccount(String currency, int dailyLimit, int currentBalance) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountCurrency(currency);
        bankAccount.setDailyLimit(dailyLimit);
        bankAccount.setCurrentBalance(currentBalance);

        return bankAccount;
    }
}
