package repository;


import entity.User;
import model.UserModel;

import java.util.List;

public interface IUserRepository {
    User findByEmail(String username);
    User findById(Long id);
    List<User> getAll();
    UserModel save(User user);
    UserModel update(User user);
    UserModel delete(User user);
}