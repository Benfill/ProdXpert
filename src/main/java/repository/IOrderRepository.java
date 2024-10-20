package repository;

import entity.Order;

import java.util.List;

public interface IOrderRepository {
    public Order save(Order order) throws Exception;
    public List<Order> getOrderByUserId(Long userId) throws Exception;  
    List<Order> getAllOrders(int page,int length) throws Exception;
	long count() throws Exception;
	public void update(Order order) throws Exception;
    public Order getOrderById(Long id) throws Exception ;

}
