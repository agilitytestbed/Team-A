package transactions;

import java.util.Date;
import categories.Category;
import users.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private TransactionMapper transactionMapper;

    public Transaction createTransaction(Date transactionDate, String payee, String paymentReference, float amount, int userId, int categoryId) {

        User user = new User(userId);
        user.setUserId(userId);
        Category category = new Category();
        category.setId(categoryId);

        Transaction transaction = new Transaction();
        transaction.setTranactionDate(transactionDate);
        transaction.setPayee(payee);
        transaction.setPaymentReference(paymentReference);
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setCategory(category);

        transactionMapper.createTransaction(transaction);

        return this.getTransaction(transaction.getId());
    }

    public Transaction updateTransaction(int id, Date transactionDate, String payee, String paymentReference, float amount, int userId, int categoryId) {

        User user = new User(userId);
        user.setUserId(userId);
        Category category = new Category();
        category.setId(categoryId);

        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setTranactionDate(transactionDate);
        transaction.setPayee(payee);
        transaction.setPaymentReference(paymentReference);
        transaction.setAmount(amount);
        transaction.setUser(user);
        transaction.setCategory(category);

        transactionMapper.updateTransaction(transaction);

        return this.getTransaction(transaction.getId());
    }

    public Transaction getTransaction(int transactionId) {
        return transactionMapper.getTransaction(transactionId);
    }

    public List<Transaction> getTransactions(int userId) {
        return transactionMapper.getTransactions(userId);
    }



    public void deleteTransaction(int transactionId) {
        transactionMapper.deleteTransaction(transactionId);
    }
}