package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import utils.ThymeleafConfig;

public class HomeServlet extends HttpServlet {
	private TemplateEngine templateEngine;

	public void init() throws ServletException {
		templateEngine = ThymeleafConfig.getTemplateEngine(getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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

		// Create the Thymeleaf context
		WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
		ctx.setVariable("title", "Products");
		ctx.setVariable("products", products);

		if (!request.getServletPath().equals("/")) {
			templateEngine.process("404", ctx, response.getWriter());
			return;
		}

		ctx.setVariable("message", "Hello from Thymeleaf in JEE!");
		templateEngine.process("pages/index", ctx, response.getWriter());

	}

}
