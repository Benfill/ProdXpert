package controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.io.IOException;


public class UserServlet extends HttpServlet {
	private TemplateEngine templateEngine;

    public void init() throws ServletException {

		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.getServletContext());
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		// Initialize the template engine
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // Create the Thymeleaf context
		   WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
		   ctx.setVariable("message", "Hello from Thymeleaf in JEE!");
		   templateEngine.process("index", ctx, response.getWriter());


    }
   
}
