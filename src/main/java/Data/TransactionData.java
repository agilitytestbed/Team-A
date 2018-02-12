package Data;

import users.User;

import java.util.Date;

public class TransactionData {
    private Integer transactionId;
    private Date transactionDate;
    private String payee;
    private String paymentReference;
    private float amount;
    private User user;
    private int categoryId;

    public Integer getId() {

        return transactionId;
    }

    public Date getTransactionDate() {

        return transactionDate;
    }

    public String getPayee() {

        return payee;
    }

    public String getPaymentReference() {

        return paymentReference;
    }

    public float getAmount() {

        return amount;
    }

    public User getUser() {

        return user;
    }
    public int getCategory_id() {

        return categoryId;
    }
}