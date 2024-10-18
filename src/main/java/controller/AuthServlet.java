package controller;

import entity.Admin;
import entity.Client;
import entity.User;
import enums.UserRole;
import model.UserModel;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import service.impl.UserServiceImpl;
import utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthServlet extends HttpServlet {
    private TemplateEngine templateEngine;
    private UserServiceImpl userService;

    public void init() throws ServletException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(this.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        // Initialize the template engine
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        WebContext ctx = new WebContext(req, res, getServletContext(), req.getLocale());

        String path = req.getServletPath();

        if (checkAuth(req)){ // redirect home if authenticated
            res.sendRedirect(req.getContextPath());
            return;
        }

        if ("/login".equals(path)) {
            templateEngine.process("auth/login", ctx, res.getWriter());
        } else if ("/register".equals(path)) {
            templateEngine.process("auth/register", ctx, res.getWriter());
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        WebContext ctx = new WebContext(req, res, getServletContext(), req.getLocale());
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            login(req, res, ctx);
        } else if ("/register".equals(path)) {
            register(req, res, ctx);
        } else if ("/logout".equals(path)){
            logout(req, res);
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse res, WebContext ctx) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");



        if (userService.userExist(email).successful()) {
            ctx.setVariable("error", "User already exist");
        } else {
            User user = null;
            if (userService.isFirst()){
                user = (Admin) new Admin(firstName, secondName, email, PasswordUtil.hashPassword(password));
            } else {
                user = (Client) new Client(firstName, secondName, email, PasswordUtil.hashPassword(password));
            }
            UserModel creation = userService.create(user);
            if (creation.successful()){
                authUser(req, res, user); return;
            } else ctx.setVariable("error", creation.message());

        }
        templateEngine.process("auth/register", ctx, res.getWriter());

    }

    private void login(HttpServletRequest req, HttpServletResponse res, WebContext ctx) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserModel userExistence = userService.userExist(email);
        
        if (userExistence.successful()) {
            if (PasswordUtil.verifyPassword(userExistence.getUSer().getPassword(), password)){
                authUser(req, res, userExistence.getUSer());
            } else ctx.setVariable("error", "Wrong password.");
        } else ctx.setVariable("error", "We have no records with this email.");
        templateEngine.process("auth/login", ctx, res.getWriter());
    }

    private void authUser(HttpServletRequest req, HttpServletResponse res, User user) throws IOException { // authenticates user
        HttpSession session = req.getSession();
        session.setAttribute("authUser",user);
        res.sendRedirect(req.getContextPath());
    }

    private boolean checkAuth(HttpServletRequest req) { // check authentication
        HttpSession session = req.getSession();
        return session != null && session.getAttribute("authUser") != null;
    }
    private void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		res.sendRedirect(req.getContextPath());
	}
}
