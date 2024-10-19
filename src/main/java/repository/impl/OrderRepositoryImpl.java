package repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Order;
import entity.Product;
import repository.IOrderRepository;

import java.util.List;
import utils.HibernateUtil;

public class OrderRepositoryImpl implements IOrderRepository {

    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @Override
    public Order save(Order order) throws Exception {
        sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		session.save(order);
		session.getTransaction().commit();
		session.close();

        return order;

    }

    @Override
    public List<Order> getOrderByUserId(Long userId) throws Exception {
        sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Order> query = session.createQuery("FROM Order", Order.class);
        query.setParameter("userId", userId);

		

		List<Order> orders = query.getResultList();
		session.close();
		return orders;

    }
    
}
