package controller;

import java.io.IOException;
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

            WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
            context.setVariable("title", "users");
            context.setVariable("message", "Welcome to ProdXpert!");

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
                            templateEngine.process("pages/order/index", context, response.getWriter());

                        } else {
                            response.getWriter().write("Valid action, but not admin: " + action);
                        }
                    } else {
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
