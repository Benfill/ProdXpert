package utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

import javax.servlet.ServletContext;

public class ThymeleafConfig {

    private static TemplateEngine templateEngine;

    public static TemplateEngine getTemplateEngine(ServletContext servletContext) {
        if (templateEngine == null) {
            ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
            templateResolver.setTemplateMode("HTML");
            templateResolver.setPrefix("/WEB-INF/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCacheable(false);

            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            // IDialect layoutDialect = new LayoutDialect();
            // templateEngine.addDialect(layoutDialect);
        }
        return templateEngine;
    }
}