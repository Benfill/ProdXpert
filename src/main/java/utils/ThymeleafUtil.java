package utils;

import javax.servlet.ServletContext;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ThymeleafUtil {

	public static TemplateEngine templateEngine;

	public ThymeleafUtil(ServletContext context) {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCharacterEncoding("UTF-8");

		// Initialize the template engine
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
	}
}
