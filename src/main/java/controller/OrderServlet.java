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

import entity.Order;
import entity.User;
import enums.OrderStatus;
import model.OrderModel;
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
        HttpSession session = request.getSession();
        User loggedInUser = (User) session.getAttribute("authUser");
        
           try {
            String action = request.getParameter("action");
              // Create the Thymeleaf context


            response.setContentType("text/html;charset=UTF-8");
            WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());

              // Check if 'action' is null or empty
                if (action == null || action.isEmpty()) {
                    // Handle empty or missing action
                    response.getWriter().write("Action parameter is missing or empty");
                    return;
                }else{
                    String regex = "^[a-zA-Z]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(action);
    
                    if (matcher.matches()) {
                        if ("admin".equalsIgnoreCase(action)) {  
                            List<Map<String, Object>> products = new ArrayList<>();
                    
                            // Define product 1
                            Map<String, Object> product1 = new HashMap<>();
                            product1.put("id", 1L);
                            product1.put("name", "Smartphone XYZ");
                            product1.put("description", "A high-end smartphone with excellent features.");
                            product1.put("imageUrl", "/resources/images/smartphone_xyz.jpg");
                            product1.put("category", "Electronics");
                            product1.put("price", "$799");
                            products.add(product1);
                    
                            // Define product 2
                            Map<String, Object> product2 = new HashMap<>();
                            product2.put("id", 2L);
                            product2.put("name", "Wireless Headphones");
                            product2.put("description", "Noise-cancelling over-ear wireless headphones.");
                            product2.put("imageUrl", "/resources/images/wireless_headphones.jpg");
                            product2.put("category", "Accessories");
                            product2.put("price", "$199");
                            products.add(product2);
                    
                            try {
                                context.setVariable("title", "Products");
                                context.setVariable("products", products);
                            
                                templateEngine.process("pages/index", context, response.getWriter());
                            } catch (Exception e) {
                                e.printStackTrace(); 
                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                            }
                        }else if("checkout".equalsIgnoreCase(action)){
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("save".equalsIgnoreCase(action)) {
           Save(request,response);
		} else if ("update".equalsIgnoreCase(action)) {
		} else if ("delete".equalsIgnoreCase(action)) {
		}
    }

    protected void Save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
    
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String totalString = request.getParameter("total");
        HttpSession session = request.getSession(false); 
    
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
        // Boolean inserted = this.orderServiceImpl.addOrder(newOrder);
        // if (inserted) {
        //     model.setSuccessMessage("Saved");
        // } else {
        //     model.setErrorMessage("Error occurred while inserting the article");
        // }
        

        // session.setAttribute("model", model);
        templateEngine.process("pages/order/checkout", context, response.getWriter());

    }
    
    
}
