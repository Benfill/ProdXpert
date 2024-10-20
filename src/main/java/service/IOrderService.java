package service;

import java.util.List;

import entity.Order;
import model.OrderDto;

public interface IOrderService {
    public Boolean addOrder(Order order);
    public List<OrderDto> allOrders(String pageParam, String length,String search) throws Exception;
    public long count(String search) throws Exception;
    public void updateStatus(Long id ,String status) throws Exception;
    public void updateOrder(Order order) throws Exception ;
    public void deleteOrder(Order order) throws Exception ;
    public List<OrderDto> myOrders(String pageParam, String lengthParam,Long id) throws Exception;

}
