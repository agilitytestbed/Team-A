package users;

import transactions.Transaction;

import java.util.LinkedHashMap;

public class User {
    private final int userId;
    private LinkedHashMap<Integer, Transaction> transactions = new LinkedHashMap<Integer, Transaction>();

    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public LinkedHashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }
}
