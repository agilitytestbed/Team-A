package transactions;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import sessions.*;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {


    public Transaction postTransaction(Integer sessionId, Transaction transaction) {

        int id = SessionController.counter.incrementAndGet();
        SessionController.sessions.get(sessionId).getTransactions().put(id,transaction);


        return transaction;
    }
    
    public Transaction updateTransaction(Integer sessionId, Transaction transaction ,Integer transactionId){

        SessionController.sessions.get(sessionId).getTransactions().put(transactionId, transaction);
        return transaction;    }

    public Transaction getTransaction(Integer sessionId, Integer transactionId) {
        return SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
    }

    public Map getTransactions(Integer sessionId ,Integer offset, Integer limit){
        LinkedHashMap<Integer, Transaction> transactionMap = SessionController.sessions.get(sessionId).getTransactions();
        SortedSet<Integer> transactionList = new TreeSet<Integer>(transactionMap.keySet());
        SortedSet<Integer> keys = transactionList.subSet(Math.min(offset+2, transactionList.size()-1),
                Math.min(offset+limit+2, transactionList.size()+2));
        //Return 'submap' with the keys from the subset
        return keys.stream().collect(Collectors.toMap(Function.identity(), transactionMap::get));
    }

    public void deleteTransaction(Integer sessionId ,Integer transactionId) {
        SessionController.sessions.get(sessionId).getTransactions().remove(transactionId);
    }
}