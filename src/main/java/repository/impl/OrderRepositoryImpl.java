package repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Order;
import model.OrderDto;
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
    public List<OrderDto> getOrderByUserId(int page, int length,Long userId) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		String hql = "SELECT new model.OrderDto(o.id, u.firstName, o.total, o.status, o.dateCommande) "
		+ "FROM Order o LEFT JOIN o.user u  WHERE  u.id=:userId";

		Query<OrderDto> query = session.createQuery(hql, OrderDto.class);

		query.setParameter("userId", userId);

		query.setFirstResult((page - 1) * length);
		query.setMaxResults(length);

		List<OrderDto> orders = query.getResultList();

		session.getTransaction().commit();
		session.close();

		return orders;

    }

	@Override
	public List<OrderDto> getAllOrders(int page, int length, String search) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		String hql = "SELECT new model.OrderDto(o.id, u.firstName, o.total, o.status, o.dateCommande) "
		+ "FROM Order o LEFT JOIN o.user u";

		if (search != null && !search.trim().isEmpty()) {
			hql += " WHERE u.firstName LIKE :search";
		}
		Query<OrderDto> query = session.createQuery(hql, OrderDto.class);

		if (search != null && !search.trim().isEmpty()) {
			query.setParameter("search", "%" + search + "%");
		}

		// Set pagination
		query.setFirstResult((page - 1) * length);
		query.setMaxResults(length);

		// Get the list of OrderDto
		List<OrderDto> orders = query.getResultList();

		session.getTransaction().commit();
		session.close();

		return orders;
	}



	
	@Override
	public long count(String search) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
	
		String hql = "SELECT COUNT(o) FROM Order o LEFT JOIN o.user u";

		if (search != null && !search.trim().isEmpty()) {
			hql += " WHERE u.firstName LIKE :search";
		}
	
		Query<Long> query = session.createQuery(hql, Long.class);
	
		if (search != null && !search.trim().isEmpty()) {
			query.setParameter("search", "%" + search + "%");
		}
	
		long count = query.uniqueResult();
		session.close();
		return count;
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

	@Override
	public void delete(Order order) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Order o = session.get(Order.class, order.getId());

		session.beginTransaction();
		session.delete(o);
		session.getTransaction().commit();
		session.close();
	}

    
}
