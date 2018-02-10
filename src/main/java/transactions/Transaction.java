package transactions;

import categories.Category;

public class Transaction {
    private int id;
    private Category category;
    private float amount;

    public Transaction(int id, float amount) {
        this.id = id;
        this.amount = amount;
        this.category = null;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
