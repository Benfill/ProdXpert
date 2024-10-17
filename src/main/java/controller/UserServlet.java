package controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import utils.ThymeleafConfig;

import java.io.IOException;


public class UserServlet extends HttpServlet {
	private TemplateEngine templateEngine;
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    public void init() throws ServletException {
        templateEngine = ThymeleafConfig.getTemplateEngine(getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

           try {
             // Create the Thymeleaf context

            WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
            context.setVariable("title", "users");
            context.setVariable("message", "Welcome to ProdXpert!");

            response.setContentType("text/html;charset=UTF-8");
            templateEngine.process("pages/index", context, response.getWriter());
        } catch (Exception e) {
            logger.error("Error processing home template", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An internal error occurred.");
        }


    }
   
}
