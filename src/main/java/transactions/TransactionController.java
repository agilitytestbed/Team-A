package transactions;

import hello.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import users.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v0")
public class TransactionController {
    private final AtomicInteger counter = new AtomicInteger();
    private LinkedHashMap<Integer, User> users =
            new LinkedHashMap<Integer, User>();

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public RedirectView newUser(){
        int userId = counter.incrementAndGet();
        User newUser = new User(userId);
        users.put(userId, newUser);
        System.out.println(userId);
        //TODO fix redirecting
        return new RedirectView(userId + "/transactions");
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
    public HashMap getTransactions(@PathVariable Integer userId) {
        return users.get(userId).getTransactions();
    }
}
