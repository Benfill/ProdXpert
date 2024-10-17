package repository;

import java.util.List;

import entity.Product;

public interface IProductRepository {

	List<Product> readAll(int offset, int length) throws Exception;

	Product readById(long id) throws Exception;

	void insert(Product product) throws Exception;

	void update(Product product) throws Exception;

	void delete(Product product) throws Exception;

	List<Product> searchByName(String name, int offset, int length) throws Exception;

	long count() throws Exception;

}
