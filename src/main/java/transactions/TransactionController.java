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
    public ResponseEntity<Transaction> postTransaction(@RequestParam(value = "session_id",required =false) String session_id,
                                                       @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                       @RequestBody Transaction transaction) {
        if (session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        transaction = transactionService.postTransaction(session_id,transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map> getTransactions(@RequestParam(value = "session_id",required =false) String session_id,
                               @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                               @RequestParam(defaultValue = "0") Integer offset,
                               @RequestParam(defaultValue = "20") Integer limit) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        Map response = transactionService.getTransactions(session_id,offset,limit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@RequestParam(value = "session_id",required =false) String session_id,
                                                      @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                      @PathVariable Integer transactionId) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        Transaction transaction = transactionService.getTransaction(session_id, transactionId);
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (transaction, HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> deleteTransaction(@RequestParam(value = "session_id",required =false) String session_id,
                                  @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                  @PathVariable Integer transactionId) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        transactionService.deleteTransaction(session_id, transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping(value = "/transactions/{transactionId}/category")
    public ResponseEntity<Transaction> assignCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                                      @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                      @PathVariable Integer transactionId,
                                                      @RequestBody Category category) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        Transaction transaction = SessionController.sessions.get(session_id).getTransactions().get(transactionId);


        System.out.println("[Category] " + category.getId());
        System.out.println("[Session] " + session_id);
        if (SessionController.sessions.get(session_id).getCategories().containsKey(category.getId())) {
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@RequestParam(value = "session_id",required =false) String session_id,
                                         @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                         @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        transaction = transactionService.updateTransaction(
                session_id,
               transaction,
               transactionId
       );
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}