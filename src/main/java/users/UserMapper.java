package users;



import java.util.List;

public interface UserMapper {

    void createUser(User user);
    void updateUser(User user);
    User getUser(int userId);
    List<User> getUsers();
    void deleteUser(int userId);

}
