package sessions;

import categories.Category;
import categoryRule.CategoryRule;
import transactions.Transaction;

import java.util.LinkedHashMap;

public class Session {
    private final String sessionId;
    private LinkedHashMap<Integer, Transaction> transactions = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Category> categories = new LinkedHashMap<>();
    private LinkedHashMap<Integer, CategoryRule> categoryRules = new LinkedHashMap<>();


    public Session(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LinkedHashMap<Integer, Transaction> getTransactions() {
        return transactions;
    }

    public LinkedHashMap<Integer, Category> getCategories() {
        return categories;
    }

    public LinkedHashMap<Integer, CategoryRule> getCategoryRules() {
        return categoryRules;
    }
}

