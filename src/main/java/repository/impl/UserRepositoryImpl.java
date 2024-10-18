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
        Transaction transaction = null;
        List<User> users = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            transaction = s.beginTransaction();
            Query<User> query = s.createQuery("FROM User", User.class);
            users = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
            // s.save((Admin) user);
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
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findByEmail(String email) {
        User user = null;
    
        try (Session s = sessionFactory.openSession()) {
            user = s.createQuery("from User where email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
                    logger.error("the EMAIL to SEARCH with : " + email);
                    logger.error("the user FOUND : ", user);
                    logger.error("the user FIRST NAME : ", user.getFirstName());
                    
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ERROR FINDING USER : ", e);
        }
    
        return user;
    }
    

    @Override
    public User findById(int id) {
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


}
