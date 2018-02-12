package users;

import transactions.Transaction;

import java.util.LinkedHashMap;

public class User {
    private int userId;
    private String name;
    private LinkedHashMap<Integer, Transaction> transactions = new LinkedHashMap<Integer, Transaction>();

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedHashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }
}
