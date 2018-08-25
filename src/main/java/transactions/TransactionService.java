package transactions;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import sessions.*;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {


    public Transaction postTransaction(String sessionId, Transaction transaction) {

        int id = SessionController.counter.incrementAndGet();
        transaction.setId(id);
        SessionController.sessions.get(sessionId).getTransactions().put(id,transaction);

        return transaction;
    }
    
    public Transaction updateTransaction(String sessionId, Transaction transaction ,Integer transactionId){

        transaction.setId(transactionId);
        SessionController.sessions.get(sessionId).getTransactions().put(transactionId, transaction);
        return transaction;
    }

    public Transaction getTransaction(String sessionId, Integer transactionId) {
        return SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
    }

    public Map getTransactions(String sessionId ,Integer offset, Integer limit){
        LinkedHashMap<Integer, Transaction> transactionMap = SessionController.sessions.get(sessionId).getTransactions();
        SortedSet<Integer> transactionList = new TreeSet<Integer>(transactionMap.keySet());
        SortedSet<Integer> keys = transactionList.subSet(Math.min(offset+2, transactionList.size()-1),
                Math.min(offset+limit+2, transactionList.size()+2));
        //Return 'submap' with the keys from the subset
        return keys.stream().collect(Collectors.toMap(Function.identity(), transactionMap::get));
    }

    public boolean deleteTransaction(String sessionId ,Integer transactionId) {
        boolean success = false;
        SessionController.sessions.get(sessionId).getTransactions().remove(transactionId);
        for (Transaction transaction : SessionController.sessions.get(sessionId).getTransactions().values()) {
            if (transaction != null) {
                if (transaction.getId() == transactionId) {
                    success = true;
                }
            }
        }
        return success;
    }
}