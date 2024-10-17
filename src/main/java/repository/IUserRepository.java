package repository;


import entity.User;
import model.UserModel;

import java.util.List;

public interface IUserRepository {
    User findByEmail(String username);
    User findById(int id);
    List<User> getAll();
    UserModel save(User user);
    void update(User user);
    void delete(User user);
}