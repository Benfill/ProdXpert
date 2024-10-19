package controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import entity.Admin;
import entity.Client;
import entity.User;
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
		String filterBy = "all";

		if (!authAndHasAccess(req)) {
			templateEngine.process("404", ctx, res.getWriter());
			return;
		}

		if ("/dashboard".equalsIgnoreCase(path)) {
			String isFilter = req.getParameter("type");
			if(isFilter != null) filterBy = isFilter;
			List<User> users = userService.getAll(filterBy);
			String search = req.getParameter("search");
			if (search != null && !search.isEmpty()) {
				users = users.stream().filter(u -> (u.getFirstName() + " " + u.getSecondName()).contains(search)).collect(Collectors.toList());
			}
			ctx.setVariable("users", users);
			templateEngine.process("dashboard/index", ctx, res.getWriter());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String path = req.getServletPath();
        WebContext ctx = new WebContext(req, res, getServletContext(), req.getLocale());

		if(authAndHasAccess(req)){
			if ("/dashboard/users/create".equals(path)) {
				create(req, res, ctx);
			} else if ("/dashboard/users/delete".equals(path)) {
				delete(req, res, ctx);
			} else if ("/dashboard/users/update".equals(path)){
				update(req, res);
			}
		} else res.sendRedirect(req.getContentType() + "?error=access denied.");
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

	private void delete(HttpServletRequest req, HttpServletResponse res, WebContext ctx) throws ServletException, IOException {
		long userId = Long.parseLong(req.getParameter("user_id"));
		if (userService.userExist(userId)) {
			UserModel model = userService.delete(userId);
			res.sendRedirect(req.getContextPath() + "/dashboard?" + (model.successful() ? "success=" + model.message() : "error=" + model.message()));
		} else res.sendRedirect(req.getContextPath() + "dashboard?error=no user found.");
	}
	private void update(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		long userId = Long.parseLong(req.getParameter("id"));
		String firstName = req.getParameter("firstName");
		String secondName = req.getParameter("secondName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String role = req.getParameter("role");
		
		if (userService.userExist(userId)) {
			if ("Admin".equalsIgnoreCase(role)) {
				String accessLevel = req.getParameter("accessLevel");
				Admin admin = new Admin(userId, firstName, secondName, email, password, Integer.parseInt(accessLevel));
				model = userService.update(admin);
			} else if ("Client".equalsIgnoreCase(role)) {
				String deliveryAddress = req.getParameter("deliveryAddress");				
				String paymentMethod = req.getParameter("paymentMethod");
				Client client = new Client(userId, firstName, secondName, email, password, deliveryAddress, paymentMethod);
				model = userService.update(client);
			} else {
				model.setSuccess(false);
				model.setMessage("invalid role, " + role);
			}
			res.sendRedirect(req.getContextPath() + "/dashboard?" + (model.successful() ? "success=" + model.message() : "error=" + model.message()));

		} else res.sendRedirect(req.getContextPath() + "/dashboard?error=user not found.");

	}
}