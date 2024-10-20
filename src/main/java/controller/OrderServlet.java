package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import entity.Admin;
import entity.Order;
import entity.User;
import enums.OrderStatus;
import model.OrderDto;
import model.OrderModel;
import model.ProductDto;
import model.ProductModel;
import repository.impl.OrderRepositoryImpl;
import service.impl.OrderServiceImpl;

import java.util.regex.Pattern;
// import java.util.regex.Matcher;

import utils.ThymeleafConfig;

public class OrderServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(OrderServlet.class);
    private  OrderServiceImpl orderServiceImpl;

    public void init() throws ServletException {
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl();
        orderServiceImpl = new OrderServiceImpl(orderRepositoryImpl);
        templateEngine = ThymeleafConfig.getTemplateEngine(getServletContext());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        
           try {
            String action = request.getParameter("action");
              // Create the Thymeleaf context
            response.setContentType("text/html;charset=UTF-8");
            WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());

          

              // Check if 'action' is null or empty
                if (action == null || action.isEmpty()) {
                    templateEngine.process("pages/404", context, response.getWriter());
                }else{
                    String regex = "^[a-zA-Z]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(action);
    
                    if (matcher.matches()) {
                        if ("admin".equalsIgnoreCase(action)) {  
                            admin(request,response);
                        }else if("checkout".equalsIgnoreCase(action)){
                          checkout(request,response);                     
                        }else if("myorders".equalsIgnoreCase(action)){
                            myOrders(request,response);
                        }
                    }
                    else {
                        // Handle invalid 'action'
                        response.getWriter().write("Invalid action parameter. Only alphabetic characters are allowed.");
                    }
                }

             
           
        } catch (Exception e) {
            logger.error("Error processing home template", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred.");
        }


    }


    protected void admin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());

        if (!authAndHasAccess(request)) {
			templateEngine.process("404", context, response.getWriter());
			return;
		}
        List<OrderDto> orders = null;
		OrderModel model = new OrderModel();
		String length = "5";
		String pageParam = request.getParameter("page");
		String searchString = request.getParameter("search");


        if (pageParam == null)
			pageParam = "1";

        if (searchString == null) 
            searchString="";

		try {
			orders = this.orderServiceImpl.allOrders(pageParam, length,searchString);
         
			long total = this.orderServiceImpl.count(searchString);
			int totalPages = (int) Math.ceil((double) total / 5);

            logger.info("orders count"+total);


			model.setOrders(orders);
			model.setOrderTotal(total);
			model.setPage(Integer.parseInt(pageParam));
			model.setTotalPages(totalPages);

            context.setVariable("model",model);
            templateEngine.process("pages/order/index", context, response.getWriter());
		} catch (Exception e) {
            logger.error("Error in admin get method", e);
            model.setErrorMessage(e.getMessage());
		}
    }


    protected void myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("authUser");

        List<OrderDto> orders = null;
		OrderModel model = new OrderModel();
		String length = "5";
		String pageParam = request.getParameter("page");


        if (pageParam == null)
			pageParam = "1";

        try {
            if (session != null && loggedInUser != null) {
                orders = this.orderServiceImpl.myOrders(pageParam, length ,loggedInUser.getId());
         
                long total = orders.size();
                int totalPages = (int) Math.ceil((double) total / 5);

                logger.info("orders count"+total);


                model.setOrders(orders);
                model.setOrderTotal(total);
                model.setPage(Integer.parseInt(pageParam));
                model.setTotalPages(totalPages);

                context.setVariable("model",model);
                templateEngine.process("pages/order/myorders", context, response.getWriter());
            }else{
                templateEngine.process("auth/login", context, response.getWriter());
            }
        } catch (Exception e) {
            logger.info("Internal Server Error"+e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }     


    }
   

    protected void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());

        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("authUser");

        try {
            if (session != null && loggedInUser != null) {
                templateEngine.process("pages/order/checkout", context, response.getWriter());
            }else{
                templateEngine.process("auth/login", context, response.getWriter());
            }
        } catch (Exception e) {
            logger.info("Internal Server Error"+e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }     
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("save".equalsIgnoreCase(action)) {
           Save(request,response);
		} else if ("update".equalsIgnoreCase(action)) {
            update(request,response);
		} else if ("updateMyOrder".equalsIgnoreCase(action)) {
            updateMyOrder(request,response);
		}
    }



    protected void Save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
    
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String totalString = request.getParameter("total");
        HttpSession session = request.getSession(false); 

        logger.info("city" + city);
        logger.info("address" + address);
        logger.info("phone" + phone);
        logger.info("totalString" + totalString);

    
        if (session == null || session.getAttribute("authUser") == null) {
            response.sendRedirect("login"); 
            return;
        }
    
        User loggedInUser = (User) session.getAttribute("authUser");
        Long userId = loggedInUser.getId();
    
        User user = new User();
        user.setId(userId);
    
        Double total;
        try {
            total = Double.parseDouble(totalString);
        } catch (NumberFormatException e) {
            context.setVariable("error", "Invalid total amount.");
            templateEngine.process("pages/order/checkout", context, response.getWriter());
            return;
        }
    
        Order newOrder = new Order();
        newOrder.setAddress(address);
        newOrder.setCity(city);
        newOrder.setPhone(phone);
        newOrder.setTotal(total);
        newOrder.setStatus(OrderStatus.IN_PROGRESS);
        newOrder.setUser(user);
    
        OrderModel model = new OrderModel();
        Set<ConstraintViolation<Order>> violations = validator.validate(newOrder);

        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            for (ConstraintViolation<Order> violation : violations) {
                String propertyName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(propertyName, errorMessage);
                logger.error("Validation error in property: {} with message: {}", propertyName, errorMessage);
            }
            
            // Store model in the session
            // session.setAttribute("model", model);
            context.setVariable("errors", errors);
            templateEngine.process("pages/order/checkout", context, response.getWriter());
            return;
        }
        
        // After adding order
        Boolean inserted = this.orderServiceImpl.addOrder(newOrder);
        if (inserted) {
            context.setVariable("successMessage", "Order has been successfully saved.");
        } else {
            context.setVariable("errorMessage", "Error occurred while inserting the order.");
        }
        

        // session.setAttribute("model", model);
        templateEngine.process("pages/order/checkout", context, response.getWriter());

    }

    // update order details for client

    protected void updateMyOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        OrderModel model=null;

       

        // String id = request.getParameter("order_id");
        // String 
        // String status = request.getParameter("status");

       

        try {
            this.orderServiceImpl.updateOrder(null);
			model.setSuccessMessage("Order status changed successfully");



        } catch (NumberFormatException e) {
            logger.error("cant parse  order id string", e);            
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error in update order", e);            
        }

		response.sendRedirect(request.getContextPath() + "/order?action=admin&page=1");


    }



    // update status for admin
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        OrderModel model=null;

        if (!authAndHasAccess(request)) {
			templateEngine.process("404", context, response.getWriter());
			return;
		}

        String id = request.getParameter("order_id");
        String status = request.getParameter("status");

       

        try {
            this.orderServiceImpl.updateStatus(Long.parseLong(id), status);
			model.setSuccessMessage("Order status changed successfully");



        } catch (NumberFormatException e) {
            logger.error("cant parse  order id string", e);            
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("Error in update order", e);            
        }

		response.sendRedirect(request.getContextPath() + "/order?action=admin&page=1");


    }

     private static boolean authAndHasAccess(HttpServletRequest req) { 
        HttpSession session = req.getSession();
		User user = (User) session.getAttribute("authUser");
        return session != null &&  user != null && user instanceof Admin;
    }
    
}
