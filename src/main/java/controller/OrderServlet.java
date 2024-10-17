package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.util.regex.Pattern;
// import java.util.regex.Matcher;

import utils.ThymeleafConfig;

public class OrderServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private static final Logger logger = LoggerFactory.getLogger(OrderServlet.class);

    public void init() throws ServletException {
        templateEngine = ThymeleafConfig.getTemplateEngine(getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

           try {
            String action = request.getParameter("action");
              // Create the Thymeleaf context


            response.setContentType("text/html;charset=UTF-8");

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
                                WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
                                context.setVariable("title", "Products");
                                context.setVariable("products", products);
                            
                                // Process the template and write to response
                                response.setContentType("text/html;charset=UTF-8");
                                templateEngine.process("pages/index", context, response.getWriter());
                            } catch (Exception e) {
                                e.printStackTrace(); // Log the exception for debugging
                                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                            }
                        }else if("checkout".equalsIgnoreCase(action)){
                            try {
                                WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
                                // context.setVariable("title", "Products");
                            
                                // Process the template and write to response
                                response.setContentType("text/html;charset=UTF-8");
                                templateEngine.process("pages/order/checkout", context, response.getWriter());
                            } catch (Exception e) {
                                e.printStackTrace(); // Log the exception for debugging
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
}
