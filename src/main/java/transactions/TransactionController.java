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
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction postTransaction(@RequestParam Integer sessionId, @RequestBody Transaction transaction) {
               return transactionService.postTransaction(
                       sessionId,transaction
                       );
    }

    @GetMapping("/transactions")
    public Map getTransactions(@RequestParam Integer sessionId, @RequestParam(defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "20") Integer limit) {
    return transactionService.getTransactions(sessionId,offset,limit);
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@RequestParam Integer sessionId, @PathVariable Integer transactionId) {
        Transaction transaction = transactionService.getTransaction(sessionId, transactionId);
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (transaction, HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public void deleteTransaction(@RequestParam Integer sessionId, @PathVariable Integer transactionId) {
        transactionService.deleteTransaction(sessionId, transactionId);
    }
    @PatchMapping(value = "/transactions/{transactionId}")
    public ResponseEntity<Transaction> assignCategory(@RequestParam Integer sessionId, @PathVariable Integer transactionId,
                                                      @RequestBody Category category) {
        Transaction transaction = SessionController.sessions.get(sessionId).getTransactions().get(transactionId);
        System.out.println("[Category] " + category.getId());
        System.out.println("[Session] " + sessionId);
        if (SessionController.sessions.get(sessionId).getCategories().containsKey(category.getId())) {
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/transactions/{transactionId}")
    public Transaction updateTransaction(@RequestParam Integer sessionId, @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
       return transactionService.updateTransaction(
               sessionId,
               transaction,
               transactionId
       );
    }
}