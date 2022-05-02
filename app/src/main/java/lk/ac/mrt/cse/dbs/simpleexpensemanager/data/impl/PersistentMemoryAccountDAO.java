package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentMemoryAccountDAO implements AccountDAO {
    private final Context context = MainActivity.getContext();
    private final DatabaseHelper dbHelper = new DatabaseHelper(context);
    private final Map<String, Account> accounts;

    public PersistentMemoryAccountDAO() {
        this.accounts = dbHelper.getAccounts();
    }

    @Override
    public List<String> getAccountNumbersList() {
        return new ArrayList<>(this.accounts.keySet());
    }

    @Override
    public List<Account> getAccountsList() {
        return new ArrayList<>(this.accounts.values());
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return this.accounts.get(accountNo);
    }

    @Override
    public void addAccount(Account account) {
        dbHelper.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        dbHelper.removeAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if (!accounts.containsKey(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        Account account = accounts.get(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        dbHelper.updateAccount(account);
    }
}
