package users;

import java.util.List;

import Data.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value="/users", method=RequestMethod.POST)
    public User createUser(@RequestBody UserData userData) {
        return userService.createUser(userData.getName());
    }

    // The path variable id is not used
    @RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
    public User updateUser(@RequestBody UserData userData) {
        return userService.updateUser(userData.getId(), userData.getName());
    }

    @RequestMapping("/users/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value="/users/{userId}", method=RequestMethod.DELETE)
    public void deleteAccount(@PathVariable int userId) {
        userService.deleteUser(userId);
    }
}