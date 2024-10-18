package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.OverridesAttribute;

import org.apache.commons.io.IOExceptionList;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import entity.Admin;
import entity.Client;
import entity.User;
import enums.UserRole;
import model.UserModel;
import service.impl.UserServiceImpl;
import utils.PasswordUtil;
import utils.ThymeleafUtil;

public class UserServlet extends HttpServlet {
	private TemplateEngine templateEngine;
	private UserServiceImpl userService;
	private UserModel model;

	public void init() throws ServletException {

		ThymeleafUtil thymeleafUtil = new ThymeleafUtil(getServletContext());
		templateEngine = thymeleafUtil.templateEngine;
		userService = new UserServiceImpl();
		model = new UserModel();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext ctx = new WebContext(req, res, getServletContext(), req.getLocale());
		String path = req.getServletPath();

		if (!authAndHasAccess(req)) {
			templateEngine.process("404", ctx, res.getWriter());
			return;
		}

		if ("/dashboard".equalsIgnoreCase(path)) {
			ctx.setVariable("users", userService.getAll());
			templateEngine.process("dashboard/index", ctx, res.getWriter());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = req.getServletPath();
        WebContext ctx = new WebContext(req, res, getServletContext(), req.getLocale());

		if ("/dashboard/users/create".equals(path)) {
			create(req, res, ctx);
		}
	}
	
    private boolean authAndHasAccess(HttpServletRequest req) { // check authentication
        HttpSession session = req.getSession();
		User user = (User) session.getAttribute("authUser");
        return session != null &&  user != null && user instanceof Admin;
    }

private void create(HttpServletRequest req, HttpServletResponse res, WebContext ctx) throws ServletException, IOException {
	String firstName = req.getParameter("firstName"); 
	String secondName = req.getParameter("secondName"); 
	String email = req.getParameter("email"); 
	String password = PasswordUtil.hashPassword(req.getParameter("password").toString()); 
	String role = req.getParameter("role");

	if (!userService.userExist(email).successful()) {
		if (role.equalsIgnoreCase("admin")) {
			String accessLevel = req.getParameter("accessLevel");
			model = userService.create(new Admin(firstName, secondName, email, password, Integer.parseInt(accessLevel)));
		} else if (role.equalsIgnoreCase("client")) {
			String deliveryAddress = req.getParameter("deliveryAddress");
			String paymentMethod = req.getParameter("paymentMethod");
			model = userService.create(new Client(firstName, secondName, email, password, deliveryAddress, paymentMethod));
		}
	
		res.sendRedirect(req.getContextPath() + "/dashboard?" + (model.successful() ? "success=" + model.message() : "error?" + model.message()));
	} else {
		res.sendRedirect(req.getContextPath() + "/dashboard?error=user already exists.");
	}

}

}