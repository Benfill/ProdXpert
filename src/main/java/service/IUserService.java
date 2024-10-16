package service;


import entity.User;
import model.UserModel;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User findByEmail(String email);
    UserModel userExist(String email);
    UserModel create(User user);
    boolean isFirst();

}
