package service;


import entity.User;
import model.UserModel;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User findByEmail(String email);
    User findById(Long id);
    boolean userExist(Long id);
    UserModel userExist(String email);
    UserModel create(User user);
    boolean isFirst();

    UserModel delete(Long id);

    UserModel update(User user);

}
