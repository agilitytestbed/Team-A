package transactions;

import categories.Category;
import hello.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import users.User;

import javax.validation.constraints.Min;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {
    private final AtomicInteger counter = new AtomicInteger();
    private LinkedHashMap<Integer, User> users =
            new LinkedHashMap<Integer, User>();

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public RedirectView newUser(){
        int userId = counter.incrementAndGet();
        User newUser = new User(userId);
        users.put(userId, newUser);
        //TODO fix redirecting
        //return newUser;
        return new RedirectView("/api/v0/" + userId + "/transactions");
    }

    @RequestMapping("/debug")
    public Object debugInfo(){
        return users;
    }

    @PostMapping("/{userId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction postTransaction(@PathVariable Integer userId, @RequestBody Transaction transaction) {
        int id = counter.incrementAndGet();
        transaction.setId(id);
        users.get(userId).getTransactions().put(id,transaction);
        return transaction;
    }

    @GetMapping("/{userId}/transactions")
    public Map getTransactions(@PathVariable Integer userId, @RequestParam(defaultValue = "0") Integer offset,
                                @RequestParam(defaultValue = "20") Integer limit) {
        LinkedHashMap<Integer, Transaction> transactionMap = users.get(userId).getTransactions();
        SortedSet<Integer> transactionList = new TreeSet<Integer>(transactionMap.keySet());
        SortedSet<Integer> keys = transactionList.subSet(Math.min(offset+2, transactionList.size()-1),
                Math.min(offset+limit+2, transactionList.size()+2));
        //Return 'submap' with the keys from the subset
        return keys.stream().collect(Collectors.toMap(Function.identity(), transactionMap::get));
    }

    @GetMapping(value = "/{userId}/transactions", params = "category")
    public Map getTransactionsCategory(@PathVariable Integer userId, @RequestParam Integer category) {
        LinkedHashMap<Integer, Transaction> resultMap = new LinkedHashMap<Integer, Transaction>();
        LinkedHashMap<Integer, Transaction> transactionMap = users.get(userId).getTransactions();
        for (Transaction transaction : transactionMap.values()) {
            if(transaction.getCategory().getId()==category) {
                resultMap.put(transaction.getId(), transaction);
            }
        }
        return resultMap;
    }

    @GetMapping("/{userId}/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable Integer userId, @PathVariable Integer transactionId){
        return users.get(userId).getTransactions().get(transactionId);
    }

    @DeleteMapping("/{userId}/transactions/{transactionId}")
    public void deleteTransaction(@PathVariable Integer userId, @PathVariable Integer transactionId) {
        users.get(userId).getTransactions().remove(transactionId);
    }

    @PatchMapping("/{userId}/transactions/{transactionId}")
    public Transaction assignCategory(@PathVariable Integer userId, @PathVariable Integer transactionId,
                                      @RequestBody Category category) {
        Transaction transaction = users.get(userId).getTransactions().get(transactionId);
        transaction.setCategory(category);
        return transaction;
    }
}
