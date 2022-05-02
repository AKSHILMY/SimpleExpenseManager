package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

public class PersistentMemoryTransactionDAO implements TransactionDAO {
    private final Context context = MainActivity.getContext();
    private final DatabaseHelper dbHelper = new DatabaseHelper(context);
    private final Map<String, Transaction> transactions;

    public PersistentMemoryTransactionDAO() {
        this.transactions = dbHelper.getTransactions();
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        dbHelper.updateTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return new ArrayList<>(this.transactions.values());
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        int size = transactions.size();
        List<Transaction> transactionList = new ArrayList<>(this.transactions.values());
        if (size <= limit) {
            return transactionList;
        }
        // return the last <code>limit</code> number of transaction logs
        return transactionList.subList(size - limit, size);
    }
}
