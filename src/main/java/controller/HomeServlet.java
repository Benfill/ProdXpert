package controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import utils.ThymeleafConfig;

import java.io.IOException;



public class HomeServlet extends HttpServlet {
	private TemplateEngine templateEngine;

    public void init() throws ServletException {
        templateEngine = ThymeleafConfig.getTemplateEngine(getServletContext());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // Create the Thymeleaf context
		   WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
		   ctx.setVariable("message", "Hello from Thymeleaf in JEE!");
		   templateEngine.process("pages/users/index", ctx, response.getWriter());


    }
   
}
