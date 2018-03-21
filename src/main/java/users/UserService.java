package users;

import java.util.List;
import users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User createUser(String name , int userId) {

        User user = new User(userId);
        user.setName(name);

        userMapper.createUser(user);

        return user;
    }

    public User updateUser(int userId, String name) {

        User user = new User(userId);
        user.setUserId(userId);
        user.setName(name);

        userMapper.updateUser(user);

        return user;
    }

    public User getUser(int userId) {
        return userMapper.getUser(userId);
    }

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public void deleteUser(int userId) {
        userMapper.deleteUser(userId);
    }
}