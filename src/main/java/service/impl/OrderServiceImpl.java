package service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Order;
import repository.impl.OrderRepositoryImpl;
import service.IOrderService;

public class OrderServiceImpl implements IOrderService {

    private OrderRepositoryImpl orderRepositoryImpl;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    public OrderServiceImpl(OrderRepositoryImpl orderRepositoryImpl){
        this.orderRepositoryImpl = orderRepositoryImpl;
    }

    @Override
    public Boolean addOrder(Order order) {
        try {
            Order added = this.orderRepositoryImpl.save(order);
            return added.getId() != null;
        } catch (Exception e) {
            logger.error("Cant save order", e);
        }
        return false;
    }
    
}
