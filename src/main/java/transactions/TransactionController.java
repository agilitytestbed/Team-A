package transactions;

import categories.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import users.UserController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {

    @PostMapping("/{userId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction postTransaction(@PathVariable Integer userId, @RequestBody Transaction transaction) {
        int id = UserController.counter.incrementAndGet();
        transaction.setId(id);
        UserController.users.get(userId).getTransactions().put(id,transaction);
        return transaction;
    }

    @GetMapping("/{userId}/transactions")
    public Map getTransactions(@PathVariable Integer userId, @RequestParam(defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "20") Integer limit) {
        LinkedHashMap<Integer, Transaction> transactionMap = UserController.users.get(userId).getTransactions();
        SortedSet<Integer> transactionList = new TreeSet<Integer>(transactionMap.keySet());
        SortedSet<Integer> keys = transactionList.subSet(Math.min(offset+2, transactionList.size()-1),
                Math.min(offset+limit+2, transactionList.size()+2));
        //Return 'submap' with the keys from the subset
        return keys.stream().collect(Collectors.toMap(Function.identity(), transactionMap::get));
    }

    @GetMapping(value = "/{userId}/transactions", params = "category")
    public Map getTransactionsCategory(@PathVariable Integer userId, @RequestParam Integer category) {
        LinkedHashMap<Integer, Transaction> resultMap = new LinkedHashMap<>();
        LinkedHashMap<Integer, Transaction> transactionMap = UserController.users.get(userId).getTransactions();
        for (Transaction transaction : transactionMap.values()) {
            if(transaction.getCategory().getId()==category) {
                resultMap.put(transaction.getId(), transaction);
            }
        }
        return resultMap;
    }

    @GetMapping("/{userId}/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable Integer userId, @PathVariable Integer transactionId) {
        return UserController.users.get(userId).getTransactions().get(transactionId);
    }

    @DeleteMapping("/{userId}/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Integer userId, @PathVariable Integer transactionId) {
        UserController.users.get(userId).getTransactions().remove(transactionId);
    }

    @PatchMapping("/{userId}/transactions/{transactionId}")
    public ResponseEntity<Transaction> assignCategory(@PathVariable Integer userId, @PathVariable Integer transactionId,
                                      @RequestBody Integer categoryId) {
        Transaction transaction = UserController.users.get(userId).getTransactions().get(transactionId);
        if (UserController.users.get(userId).getCategories().containsKey(categoryId)) {
            Category category = UserController.users.get(userId).getCategories().get(categoryId);
            transaction.setCategory(category);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}/transactions/{transactionId}")
    public Transaction updateTransaction(@PathVariable Integer userId, @PathVariable Integer transactionId,
                                         @RequestBody Transaction transaction) {
        transaction.setId(transactionId);
        UserController.users.get(userId).getTransactions().put(transactionId, transaction);
        return transaction;
    }
}