package transactions;

import categories.Category;
import users.User;
import java.util.Date;

public class Transaction {
    private int transactionId;

    private Date transactionDate;

    private String payee;

    private String paymentReference;

    private float amount;

    private User user;

    private Category category;

    public int getId() {

        return transactionId;
    }

    public void setId(int transactionId) {

        this.transactionId = transactionId;
    }

    public Date getTranactionDate() {

        return transactionDate;
    }

    public void setTranactionDate(Date transactionDate) {

        this.transactionDate = transactionDate;
    }

    public String getPayee() {

        return payee;
    }

    public void setPayee(String payee) {

        this.payee = payee;
    }
    public String getPaymentReference() {

        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {

        this.paymentReference = paymentReference;
    }
    public double getAmount() {

        return amount;
    }

    public void setAmount(float amount) {

        this.amount = amount;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }
}
