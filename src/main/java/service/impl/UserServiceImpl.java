package service.impl;

import entity.User;
import model.UserModel;
import repository.impl.UserRepositoryImpl;
import service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements IUserService {
    private UserRepositoryImpl userRepository;

    public UserServiceImpl(){
        userRepository = new UserRepositoryImpl();
    }

    public UserServiceImpl(UserRepositoryImpl ur){
        userRepository = ur;
    }

    @Override
    public List<User> getAll(String filter) {
        List<User> users = userRepository.getAll();
        if (filter.equals("all")) {
            return users;
        } else if ("admin".equalsIgnoreCase(filter)) {
            return users.stream().filter(u -> u.getType().equalsIgnoreCase("Admin")).collect(Collectors.toList());
        } else if ("client".equalsIgnoreCase(filter)) {
            return users.stream().filter(u -> u.getType().equalsIgnoreCase("Client")).collect(Collectors.toList());
        }
        return null;
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
