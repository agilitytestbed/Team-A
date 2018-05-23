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
    public ResponseEntity<Transaction> postTransaction(@RequestParam(defaultValue = "") String sessionId,
                                                       @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                                       @RequestBody Transaction transaction) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        transaction = transactionService.postTransaction(sessionId,transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map> getTransactions(@RequestParam(defaultValue = "") String sessionId,
                               @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                               @RequestParam(defaultValue = "0") Integer offset,
                               @RequestParam(defaultValue = "20") Integer limit) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        Map response = transactionService.getTransactions(sessionId,offset,limit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@RequestParam(defaultValue = "") String sessionId,
                                                      @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                                      @PathVariable Integer transactionId) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        Transaction transaction = transactionService.getTransaction(sessionId, transactionId);
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (transaction, HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> deleteTransaction(@RequestParam(defaultValue = "") String sessionId,
                                  @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                  @PathVariable Integer transactionId) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        transactionService.deleteTransaction(sessionId, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping(value = "/transactions/{transactionId}")
    public ResponseEntity<Transaction> assignCategory(@RequestParam(defaultValue = "") String sessionId,
                                                      @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                                      @PathVariable Integer transactionId,
                                                      @RequestBody Category category) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
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
    public ResponseEntity<Transaction> updateTransaction(@RequestParam(defaultValue = "") String sessionId,
                                         @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                         @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        transaction = transactionService.updateTransaction(
               sessionId,
               transaction,
               transactionId
       );
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}