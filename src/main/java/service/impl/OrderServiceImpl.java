package service.impl;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Order;
import enums.OrderStatus;
import model.OrderDto;
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

    @Override
    public List<OrderDto> allOrders(String pageParam, String lengthParam,String search) throws Exception {

        if (pageParam == null || !((pageParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(pageParam) > 0))){
            logger.error("Page Param is null or not a valid number");
            throw new Exception("Page Param is null or not a valid number");

        }

        else if (lengthParam == null
                || !((lengthParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(lengthParam) > 0)))
        {
            logger.error("Length Param is null or not a valid number");
            throw new Exception("Length Param is null or not a valid number");

        }
        int page = Integer.parseInt(pageParam);
		int length = Integer.parseInt(lengthParam);

        List<OrderDto> orders = orderRepositoryImpl.getAllOrders(page, length,search);
        
        return orders;
        
    }

    @Override
	public long count(String search) throws Exception {
		return this.orderRepositoryImpl.count(search);
	}

    @Override
    public void updateStatus(Long id , String status) throws Exception {
        Order order = this.orderRepositoryImpl.getOrderById(id);
        if (order == null) {
            throw new Exception("Order not found with id: " + id);
        }

        OrderStatus newStatus = OrderStatus.valueOf(status);
        order.setStatus(newStatus);

        this.orderRepositoryImpl.update(order);
        
    }

   


    
}
