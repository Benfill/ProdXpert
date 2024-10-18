package repository;

import entity.Order;
import java.util.List;

public interface IOrderRepository {
    public Order save(Order order) throws Exception;
    public List<Order> getOrderByUserId(Long userId) throws Exception;  
}
