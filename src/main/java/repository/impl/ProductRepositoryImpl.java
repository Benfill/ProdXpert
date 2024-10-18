package repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import entity.Product;
import repository.IProductRepository;
import utils.HibernateUtil;

public class ProductRepositoryImpl implements IProductRepository {
	private SessionFactory sessionFactory;

	@Override
	public List<Product> readAll(int offset, int length) throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Product> query = session.createQuery("FROM Product", Product.class);

		query.setFirstResult(offset);
		query.setMaxResults(length);

		List<Product> products = query.getResultList();
		session.close();
		return products;
	}

	@Override
	public Product readById(long id) throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Product> query = session.createQuery("FROM Product WHERE id = :id", Product.class);
		query.setParameter("id", id);
		Product product = query.getSingleResult();
		session.close();
		return product;
	}

	@Override
	public void update(Product product) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Product p = session.get(Product.class, product.getId());

		p.setName(product.getName());
		p.setDescription(product.getDescription());
		p.setPrice(product.getPrice());
		p.setStock(product.getStock());

		session.beginTransaction();
		session.update(p);
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public void delete(Product product) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Product p = session.get(Product.class, product.getId());

		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
		session.close();

	}

	@Override
	public List<Product> searchByName(String name, int offset, int length) throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Product> query = session.createQuery("FROM Product WHERE name like :name", Product.class);

		String q = "%" + name + "%";
		query.setParameter("name", q);

		query.setFirstResult(offset);
		query.setMaxResults(length);
		List<Product> products = query.getResultList();
		session.close();
		return products;
	}

	@Override
	public long count() throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Query<Product> query = session.createQuery("FROM Product", Product.class);
		long counter = query.getResultList().stream().count();
		session.close();

		return counter;
	}

	@Override
	public void insert(Product product) throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		session.save(product);
		session.getTransaction().commit();
		session.close();
	}

}
