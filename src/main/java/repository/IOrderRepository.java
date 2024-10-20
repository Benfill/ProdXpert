package repository;

import entity.Order;
import model.OrderDto;

import java.util.List;

public interface IOrderRepository {
    public Order save(Order order) throws Exception;
    public List<OrderDto> getOrderByUserId(int page, int length,Long userId) throws Exception;  
    List<OrderDto> getAllOrders(int page,int length,String search) throws Exception;
	long count(String search) throws Exception;
	public void update(Order order) throws Exception;
	public void delete(Order order) throws Exception;
    public Order getOrderById(Long id) throws Exception ;

}
