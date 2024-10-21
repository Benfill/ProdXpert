import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import entity.Order;
import entity.User;
import enums.OrderStatus;
import model.OrderDto;
import repository.impl.OrderRepositoryImpl;
import service.impl.OrderServiceImpl;

public class OrderServiceImplTest {

    @Mock
    private OrderRepositoryImpl orderRepositoryImpl;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private Order order1;
    private Order order2;

    private User user1;
    private User user2;
    private List<Order> mockOrders;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User("user one", "one", "user_one@example.com", "user_one_password");
        user2 = new User("user two", "two", "user_two@example.com", "user_two_password");

        order1 = new Order(LocalDateTime.now(),"adress 1","111111","kech",OrderStatus.IN_PROGRESS,user1,11.10);
        order2 = new Order(LocalDateTime.now(),"adress 2","111111","safi",OrderStatus.DELIVERED,user2,11.10);

        mockOrders = new ArrayList<>();
        mockOrders.add(order1);
        mockOrders.add(order2);

    }

    @Test 
    void testAllOrders() throws Exception { 

        List<OrderDto> mockOrders = new ArrayList<>();
        mockOrders.add(new OrderDto(1L,"user 1",120.20,OrderStatus.IN_PROGRESS,LocalDateTime.now()));
        mockOrders.add(new OrderDto(2L,"user 2",190.20,OrderStatus.PENDING,LocalDateTime.now()));

        when(orderRepositoryImpl.getAllOrders(1, 5,"")).thenReturn(mockOrders);

        List<OrderDto> result = orderServiceImpl.allOrders("1", "5", "");

        // Assertions
        assertEquals(2, result.size()); 
        assertEquals("user 1", result.get(0).getUserName());
        assertEquals(2L, result.get(1).getId());

        verify(orderRepositoryImpl, times(1)).getAllOrders(1, 5,"");
    }

    @Test
    void testAddNewOrder() throws Exception {
        Order orderToAdd = mockOrders.get(0);
        
        Order savedOrder = orderToAdd;
        savedOrder.setId(1L);  
    
        when(orderRepositoryImpl.save(any(Order.class))).thenReturn(savedOrder);
    
        Boolean result = orderServiceImpl.addOrder(orderToAdd);
    
        assertTrue(result);
        
        verify(orderRepositoryImpl).save(any(Order.class));  
    }

}
