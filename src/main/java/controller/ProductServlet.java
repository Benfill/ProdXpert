package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import service.impl.ProductServiceImpl;
import utils.ThymeleafUtil;

public class ProductServlet extends HttpServlet {
	private TemplateEngine templateEngine;

	private ProductServiceImpl productService;

	@Override
	public void init() throws ServletException {
		ThymeleafUtil thymeleafUtil = new ThymeleafUtil(getServletContext());
		templateEngine = ThymeleafUtil.templateEngine;
		productService = new ProductServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		returnView(req, resp, "product");
	}

	private void returnView(HttpServletRequest req, HttpServletResponse resp, String view) {
		// Create the Thymeleaf context
		WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());
		ctx.setVariable("message", "Hello from Thymeleaf in JEE!");
		try {
			templateEngine.process(view, ctx, resp.getWriter());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
