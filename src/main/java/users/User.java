package users;

import categories.Category;
import transactions.Transaction;

import java.util.LinkedHashMap;

public class User {
    private final int userId;
    private LinkedHashMap<Integer, Transaction> transactions = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Category> categories = new LinkedHashMap<>();

    public User(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public LinkedHashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public LinkedHashMap<Integer, Category> getCategories() {
        return categories;
    }
}
