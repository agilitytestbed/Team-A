package transactions;

import categories.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import sessions.SessionController;
import sessions.Session;

import java.util.*;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{sessionId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction postTransaction(@PathVariable Integer sessionId, @RequestBody Transaction transaction) {
               return transactionService.postTransaction(
                       sessionId,transaction
                       );
    }

    @GetMapping("/{sessionId}/transactions")
    public Map getTransactions(@PathVariable Integer sessionId, @RequestParam(defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "20") Integer limit) {
    return transactionService.getTransactions(sessionId,offset,limit);
    }

    @GetMapping(value = "/{sessionId}/transactions", params = "category")
    public Map getTransactionsCategory(@PathVariable Integer sessionId, @RequestParam Integer categoryId) {
        LinkedHashMap<Integer, Transaction> resultMap = new LinkedHashMap<>();
        LinkedHashMap<Integer, Transaction> transactionMap = SessionController.sessions.get(sessionId).getTransactions();
        for (Transaction transaction : transactionMap.values()) {
            if(transaction.getCategory().getId()== categoryId) {
                resultMap.put(transaction.getId(), transaction);
            }
        }
        return resultMap;
    }

    @GetMapping("/{sessionId}/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId) {
            return transactionService.getTransaction(sessionId, transactionId);
    }

    @DeleteMapping("/{sessionId}/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId) {
        transactionService.deleteTransaction(sessionId, transactionId);
    }
    @PatchMapping(value = "/{sessionId}/transactions/{transactionId}", params = "category")
    public ResponseEntity<Transaction> assignCategory(@PathVariable Integer sessionId, @PathVariable Integer transactionId,
                                                      @RequestParam Category category) {
        Transaction transaction = SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
        if (SessionController.sessions.get(sessionId).getCategories().containsKey(category)) {
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{sessionId}/transactions/{transactionId}")
    public Transaction updateTransaction(@PathVariable Integer sessionId, @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
       return transactionService.updateTransaction(
               sessionId,
               transaction,
               transactionId
       );
    }
}