package repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Order;
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

	@Override
	public List<Order> getAllOrders(int page, int length) throws Exception {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Order> query = session.createQuery("FROM Order", Order.class);
	
			int offset = (page - 1) * length;
			query.setFirstResult(offset);
			query.setMaxResults(length);
	
			List<Order> orders = query.getResultList();
			return orders;
		} catch (Exception e) {
			logger.error("Error fetching orders", e);
			throw new Exception("Error fetching orders", e);
		}
	}
	

    @Override
    public long count() throws Exception {
        sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Order> query = session.createQuery("FROM Order", Order.class);
		long counter = query.getResultList().stream().count();
		session.close();

		return counter;
    }

	@Override
	public void update(Order order) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Order o = session.get(Order.class, order.getId());

		o.setStatus(order.getStatus());

	

		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
		session.close();
	}

	@Override
public Order getOrderById(Long id) throws Exception {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Order order = null;
    
    try {
        session.beginTransaction();
        
        order = session.get(Order.class, id);
        
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session.getTransaction() != null) {
            session.getTransaction().rollback();
        }
        throw e; 
    } finally {
        session.close();
    }
    
    return order; 
}

    
}
