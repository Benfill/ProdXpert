package repository.impl;

import entity.Admin;
import entity.Client;
import entity.User;
import enums.UserRole;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import repository.IUserRepository;
import utils.HibernateUtil;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
public class UserRepositoryImpl implements IUserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    private final SessionFactory sessionFactory;
    private final UserModel model;

    public UserRepositoryImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
        model = new UserModel();
    }

    @Override
    public List<User> getAll() {
        // Transaction transaction = null;
        List<User> users = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            // transaction = s.beginTransaction();
            users = s.createQuery("select u FROM User u", User.class).getResultList();
            // transaction.commit();
        } catch (Exception e) {
            // if (transaction != null) {
            //     transaction.rollback();
            // }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public UserModel save(User user) {
        Transaction t = null;
        Session s = sessionFactory.openSession();
        try {
            t = s.beginTransaction();
            s.save(user instanceof Admin ? (Admin) user : (Client) user);
            t.commit();
            model.setSuccess(true);
            model.setMessage("User created successfully.");
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
            model.setSuccess(false);
            model.setMessage("Failed to create user.");
        }
        return model;
    }

    @Override
    public UserModel update(User user) { // primary update
        Transaction t = null;

        try(Session s = sessionFactory.openSession()){
            t = s.beginTransaction();
            s.update(user);
            t.commit();
            model.setSuccess(true);
            model.setMessage("User updated.");
        } catch(Exception e){
            e.printStackTrace();
            model.setSuccess(false);
            model.setMessage("Failed updating user.");
        }
        return model;
    }

    @Override
    public UserModel delete(User user) {
        Transaction t = null;
        try(Session s = sessionFactory.openSession()){
            if (userAccess(user) > 1) { // can delete only client or sub admin (access 2 or 3)
                t = s.beginTransaction();
                s.delete(user);
                t.commit();
                model.setSuccess(true);
                model.setMessage("User deleted.");
            } else {
                model.setSuccess(false); model.setMessage("Cannot delete super admin.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.setSuccess(false);
            model.setMessage("Failed deleting user.");
        }
        return model;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
    
        try (Session s = sessionFactory.openSession()) {
            user = s.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
                    
        } catch (Exception e) {
            e.printStackTrace();

        }
        return user;
    }
    

    @Override
    public User findById(Long id) {
        Transaction t = null;
        User user = null;
        try(Session s = sessionFactory.openSession()) {
            t = s.beginTransaction();
            user = s.find(User.class, id);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    private int userAccess(User user) {
        int access = 3; // client
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            access = admin.getAccessLevel();
        }
        return access;
    }

}
