package transactions;

import categories.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sessions.Session;
import sessions.SessionController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {

    @PostMapping("/{sessionId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction postTransaction(@PathVariable Integer sessionId, @RequestBody Transaction transaction) {
        int id = SessionController.counter.incrementAndGet();
        transaction.setId(id);
        SessionController.sessions.get(sessionId).getTransactions().put(id,transaction);
        return transaction;
    }

    @GetMapping("/{sessionId}/transactions")
    public Map getTransactions(@PathVariable Integer sessionId, @RequestParam(defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "20") Integer limit) {
        LinkedHashMap<Integer, Transaction> transactionMap = SessionController.sessions.get(sessionId).getTransactions();
        SortedSet<Integer> transactionList = new TreeSet<Integer>(transactionMap.keySet());
        SortedSet<Integer> keys = transactionList.subSet(Math.min(offset+2, transactionList.size()-1),
                Math.min(offset+limit+2, transactionList.size()+2));
        //Return 'submap' with the keys from the subset
        return keys.stream().collect(Collectors.toMap(Function.identity(), transactionMap::get));
    }

    @GetMapping(value = "/{sessionId}/transactions", params = "category")
    public Map getTransactionsCategory(@PathVariable Integer sessionId, @RequestParam Integer category) {
        LinkedHashMap<Integer, Transaction> resultMap = new LinkedHashMap<>();
        LinkedHashMap<Integer, Transaction> transactionMap = SessionController.sessions.get(sessionId).getTransactions();
        for (Transaction transaction : transactionMap.values()) {
            if(transaction.getCategory().getId()==category) {
                resultMap.put(transaction.getId(), transaction);
            }
        }
        return resultMap;
    }

    @GetMapping("/{sessionId}/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId) {
        return SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
    }

    @DeleteMapping("/{sessionId}/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId) {
        SessionController.sessions.get(sessionId).getTransactions().remove(transactionId);
    }
    @PatchMapping(value = "/{sessionId}/transactions/{transactionId}", params = "categoryId")
    public ResponseEntity<Transaction> assignCategory(@PathVariable Integer sessionId, @PathVariable Integer transactionId,
                                                      @RequestParam Integer categoryId) {
        Transaction transaction = SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
        if (SessionController.sessions.get(sessionId).getCategories().containsKey(categoryId)) {
            Category category = SessionController.sessions.get(sessionId).getCategories().get(categoryId);
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{sessionId}/transactions/{transactionId}")
    public Transaction updateTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
        transaction.setId(transactionId);
        SessionController.sessions.get(sessionId).getTransactions().put(transactionId, transaction);
        return transaction;
    }
}