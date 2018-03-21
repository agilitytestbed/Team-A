package transactions;

import java.util.Date;
import java.util.List;


public interface TransactionMapper {
    void createTransaction(Transaction transaction);
    void updateTransaction(Transaction transaction);
    Transaction getTransaction(int transactionId);
    List<Transaction> getTransactions(int userId);
    void deleteTransaction(int id);
}