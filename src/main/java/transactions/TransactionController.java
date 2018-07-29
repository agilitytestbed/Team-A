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
    public ResponseEntity<Transaction> postTransaction(@RequestParam(value = "session_id", required = false) String session_id,
                                                       @RequestHeader(value = "X-session-ID", required = false) String X_session_ID,
                                                       @RequestBody Transaction transaction) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (null == transaction || !transaction.validTransaction()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        transaction = transactionService.postTransaction(session.getSessionId(), transaction);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Map> getTransactions(@RequestParam(value = "session_id", required = false) String session_id,
                                               @RequestHeader(value = "X-session-ID", required = false) String X_session_ID,
                                               @RequestParam(defaultValue = "0") Integer offset,
                                               @RequestParam(defaultValue = "20") Integer limit) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        Map transactions = transactionService.getTransactions(session.getSessionId(), offset, limit);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@RequestParam(value = "session_id", required = false) String session_id,
                                                      @RequestHeader(value = "X-session-ID", required = false) String X_session_ID,
                                                      @PathVariable Integer transactionId) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Session session = SessionController.getSession(session_id,X_session_ID);
        Transaction transaction = session.getTransactions().get(transactionId);

        if ( null == transaction ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> deleteTransaction(@RequestParam(value = "session_id", required = false) String session_id,
                                                         @RequestHeader(value = "X-session-ID", required = false) String X_session_ID,
                                                         @PathVariable Integer transactionId) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        boolean success = transactionService.deleteTransaction(session.getSessionId(), transactionId);

        if (!success) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping(value = "/transactions/{transactionId}/category")
    public ResponseEntity<Transaction> assignCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                                      @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                      @PathVariable Integer transactionId,
                                                      @RequestBody Category category) {

        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        Transaction transaction = session.getTransactions().get(transactionId);


        System.out.println("[Category] " + category.getId());
        System.out.println("[Session] " + session_id);
        if (SessionController.sessions.get(session_id).getCategories().containsKey(category.getId())) {
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@RequestParam(value = "session_id",required =false) String session_id,
                                         @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                         @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        if(transaction == null || !transaction.validTransaction()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        transaction = transactionService.updateTransaction(
                session.getSessionId(),
                transaction,
                transactionId
        );
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}