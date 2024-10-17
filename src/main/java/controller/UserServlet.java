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

import entity.User;
import enums.UserRole;
import service.impl.UserServiceImpl;
import utils.ThymeleafUtil;

public class UserServlet extends HttpServlet {
	private TemplateEngine templateEngine;
	private UserServiceImpl userService;

	public void init() throws ServletException {

		ThymeleafUtil thymeleafUtil = new ThymeleafUtil(getServletContext());
		templateEngine = thymeleafUtil.templateEngine;
		userService = new UserServiceImpl();
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
		String firstName = (String) req.getAttribute("firstName");

		if ("/dashboard/create".equals(path)) {
			create(req, res);
		}
	}
	
    private boolean authAndHasAccess(HttpServletRequest req) { // check authentication
        HttpSession session = req.getSession();
		User user = (User) session.getAttribute("authUser");
        return session != null &&  user != null && user.getRole().equals(UserRole.ADMIN);
    }

private void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	String firstName = (String) req.getAttribute("firstName"); 
}

}