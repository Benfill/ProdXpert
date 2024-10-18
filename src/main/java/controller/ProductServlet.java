package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import model.ProductDto;
import model.ProductModel;
import service.impl.ProductServiceImpl;
import utils.ThymeleafUtil;

@MultipartConfig
public class ProductServlet extends HttpServlet {
	private TemplateEngine templateEngine;

	private ProductServiceImpl productService;
	private ProductModel model;

	private final Logger logger = Logger.getLogger(ProductServlet.class.getName());
	private static final String UPLOAD_DIRECTORY = "uploads";

	@Override
	public void init() throws ServletException {
		ThymeleafUtil thymeleafUtil = new ThymeleafUtil(getServletContext());
		templateEngine = ThymeleafUtil.templateEngine;
		productService = new ProductServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		String action = "";

		if (path != null)
			action = path.substring(1, path.length());

		switch (action) {
		case "store":
			store(req, resp);
			break;
		case "update":
			update(req, resp);
			break;
		case "delete":
			delete(req, resp);
			break;
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();
		String action = "";

		if (path != null)
			action = path.substring(1, path.length());

		switch (action) {
		case "details":
			show(req, resp);
			break;
		case "admin":
			index(req, resp, "product/index");
			break;
		case "admin/search":
			search(req, resp, "product/index");
			break;
		case "search":
			search(req, resp, "index");
			break;
		default:
			index(req, resp, "index");
			break;
		}

	}

	private void index(HttpServletRequest req, HttpServletResponse resp, String view)
			throws ServletException, IOException {
		List<ProductDto> dtos = null;
		model = new ProductModel();
		String pageParam = req.getParameter("page");
		String length = "5";

		if (pageParam == null)
			pageParam = "1";

		try {
			dtos = productService.getAllProducts(pageParam, length);
			long total = productService.count();
			int totalPages = (int) Math.ceil((double) total / 5);

			model.setProducts(dtos);
			model.setSuccessMessage("The products have been successfully retrieved");
			model.setProductTotal(total);
			model.setPage(Integer.parseInt(pageParam));
			model.setTotalPages(totalPages);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
		}
		String path = "pages/" + view;
		returnView(req, resp, path, model);
	}

	private void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("product_id");
		model = new ProductModel();
		try {
			ProductDto dto = productService.getProductById(idParam);
			model.setProduct(dto);
			model.setSuccessMessage("The product have been successfully retrieved");
			returnView(req, resp, "pages/product/details", model);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/product");
		}

	}

	private void store(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		model = new ProductModel();
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String price = req.getParameter("price");
		String stock = req.getParameter("stock");
		String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

		// Ensure the directory exists
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); // Use mkdirs to create necessary parent directories if they don't exist
		}

		// Get the uploaded file
		Part filePart = req.getPart("picture");
		if (filePart == null) {
			model.setErrorMessage("File part is missing.");
			logger.warning("File part is missing.");
			return;
		}

		// Save the file with its original name
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Extract file name
		String filePath = uploadPath + File.separator + fileName;
		filePart.write(filePath); // Write the file to the disk

		// Store relative URL in the database (this is critical for cross-platform
		// support)
		String fileUrl = "/" + UPLOAD_DIRECTORY + "/" + fileName;

		try {
			productService.createProduct(name, description, price, stock, fileUrl);
			model.setSuccessMessage("The product have been successfully created");
			logger.info("The product have been successfully created");
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
		}

		resp.sendRedirect(req.getContextPath() + "/product/admin");

	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("product_id");
		String name = req.getParameter("name");
		String description = req.getParameter("description");
		String price = req.getParameter("price");
		String stock = req.getParameter("stock");

		model = new ProductModel();
		try {
			productService.updateProduct(idParam, name, description, price, stock);
			model.setSuccessMessage("The product have been successfully updated");
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
		}
		resp.sendRedirect(req.getContextPath() + "/product/admin");

	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idParam = req.getParameter("product_id");
		model = new ProductModel();
		try {
			productService.deleteProduct(idParam);
			model.setSuccessMessage("The product have been successfully deleted");
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
		}

		resp.sendRedirect(req.getContextPath() + "/product/admin");

	}

	private void search(HttpServletRequest req, HttpServletResponse resp, String view)
			throws ServletException, IOException {
		List<ProductDto> dtos = null;
		model = new ProductModel();
		String name = req.getParameter("q");
		String pageParam = req.getParameter("page");
		String length = "6";

		if (pageParam == null)
			pageParam = "1";

		try {
			dtos = productService.searchByName(name, pageParam, length);

			long total = productService.count();
			int totalPages = (int) Math.ceil((double) total / 6);

			model.setProducts(dtos);
			model.setSuccessMessage("The products have been successfully retrieved");
			model.setProductTotal(total);
			model.setPage(Integer.parseInt(pageParam));
			model.setTotalPages(totalPages);
		} catch (Exception e) {
			logger.warning(e.getMessage());
			model.setErrorMessage(e.getMessage());
			resp.sendRedirect(req.getContextPath() + "/product");
			return;
		}
		String path = "pages/" + view;
		returnView(req, resp, path, model);
	}

	private void returnView(HttpServletRequest req, HttpServletResponse resp, String view, ProductModel model) {
		// Create the Thymeleaf context
		WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());

		ArrayList<String> arrays = new ArrayList<String>();
		ctx.setVariable("model", model);
		try {
			templateEngine.process(view, ctx, resp.getWriter());
		} catch (IOException e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}
}
