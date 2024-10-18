package controller;

import java.io.IOException;

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
	UserRole role = UserRole.valueOf(req.getParameter("role").toString().toUpperCase());

	if (!userService.userExist(email).successful()) {
		model = userService.create(new User(firstName, secondName, email, password));
		ctx.setVariable("model", model);
		res.sendRedirect(req.getContextPath() + "/dashboard");
	} else {
		res.sendRedirect(req.getContextPath() + "/dashboard?error=user already exists.");
	}

}

}