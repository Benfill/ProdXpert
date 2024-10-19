package service.impl;

import entity.User;
import model.UserModel;
import repository.impl.UserRepositoryImpl;
import service.IUserService;

import java.util.List;

public class UserServiceImpl implements IUserService {
    private UserRepositoryImpl userRepository;

    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserModel userExist(String email) {
        UserModel findModel = new UserModel();
        User user = findByEmail(email);
        if (user == null) {
            findModel.setSuccess(false);
        } else {
            findModel.setSuccess(true);
            findModel.setUser(user);
        }
        return findModel;
    }

    @Override
    public boolean userExist(Long id){
        return findById(id) != null;
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public UserModel create(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel delete(Long id){
        User user = findById(id);
        return userRepository.delete(user);
    }

    @Override
    public boolean isFirst(){
        return userRepository.getAll().isEmpty();
    }

    @Override
    public UserModel update(User user){
        return userRepository.update(user);
    }
}
