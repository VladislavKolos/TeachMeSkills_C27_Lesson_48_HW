package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountHelperTest {

    @InjectMocks
    private BankAccountHelper bankAccountHelper;

    @Mock
    private BankAccount mockBankAccount;

    @BeforeEach
    public void initialize() {
        bankAccountHelper = new BankAccountHelper();
    }

    @Test
    public void testConvertUsdToByn() {
        double amount = 250.55;

        double expected = amount * 3.18;
        double actual = bankAccountHelper.convertCurrency(BankAccountHelper.USD_ACCOUNT, BankAccountHelper.BYN_ACCOUNT,
                amount);

        assertEquals(expected, actual);
    }

    @Test
    public void testConvertEurToByn() {
        double amount = 250.00;

        double expected = amount * 3.4;
        double actual = bankAccountHelper.convertCurrency(BankAccountHelper.EUR_ACCOUNT, BankAccountHelper.BYN_ACCOUNT,
                amount);

        assertEquals(expected, actual);
    }

    @Test
    public void testConvertBynToUsd() {
        double amount = 1000;

        double expected = amount * 0.31;
        double actual = bankAccountHelper.convertCurrency(BankAccountHelper.BYN_ACCOUNT, BankAccountHelper.USD_ACCOUNT,
                amount);

        assertEquals(expected, actual);
    }

    @Test
    public void testIsBynBalanceValidForWithdraw() {
        when(mockBankAccount.getAccountCurrency()).thenReturn(BankAccountHelper.BYN_ACCOUNT);
        when(mockBankAccount.getCurrentBalance()).thenReturn(300.00);

        double amount = 50.00;
        String currency = BankAccountHelper.USD_ACCOUNT;

        boolean result = bankAccountHelper.isBalanceValidForWithdraw(mockBankAccount, amount, currency);

        assertTrue(result);
    }

    @Test
    public void testIsBynBalanceNoValidForWithdraw() {
        when(mockBankAccount.getAccountCurrency()).thenReturn(BankAccountHelper.BYN_ACCOUNT);
        when(mockBankAccount.getCurrentBalance()).thenReturn(300.00);

        double amount = 100.33;
        String currency = BankAccountHelper.USD_ACCOUNT;

        boolean result = bankAccountHelper.isBalanceValidForWithdraw(mockBankAccount, amount, currency);

        assertFalse(result);
    }

    @Test
    public void testIsAvailableForDailyWithdraw() {
        when(mockBankAccount.getDailyLimit()).thenReturn(300.00);

        double amount = 159;

        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(mockBankAccount, amount);

        assertTrue(result);
    }

    @Test
    public void testIsNotAvailableForDailyWithdraw() {
        when(mockBankAccount.getDailyLimit()).thenReturn(300.00);

        double amount = 319.05;

        boolean result = bankAccountHelper.isAvailableForDailyWithdraw(mockBankAccount, amount);

        assertFalse(result);
    }
}
