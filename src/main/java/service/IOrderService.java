package service;

import java.util.List;

import entity.Order;
import model.OrderDto;

public interface IOrderService {
    public Boolean addOrder(Order order);
    public List<OrderDto> allOrders(String pageParam, String length) throws Exception;
    public long count() throws Exception;
    public void updateStatus(Long id ,String status) throws Exception;
}
