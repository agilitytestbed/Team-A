package users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v0")
public class UserController {
    public static final AtomicInteger counter = new AtomicInteger();
    public static LinkedHashMap<Integer, User> users =
            new LinkedHashMap<>();

    @RequestMapping("/sessions")
    public int newSession() {
        int userId = counter.incrementAndGet();
        User newUser = new User(userId);
        users.put(userId, newUser);
        return userId;
    }

    @RequestMapping("/debug")
    public Object debugInfo(){
        return users;
    }
}
