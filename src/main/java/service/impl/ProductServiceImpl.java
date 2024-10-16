package service.impl;

import java.util.ArrayList;
import java.util.List;

import entity.Product;
import model.ProductDto;
import repository.impl.ProductRepositoryImpl;
import service.IProductService;

public class ProductServiceImpl implements IProductService {

	private ProductRepositoryImpl productRepositoryImpl;

	@Override
	public List<ProductDto> getAllProducts(String pageParam, String lengthParam) throws Exception {
		if (pageParam == null || !((pageParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(pageParam) > 0)))
			throw new Exception("Page Param is null or not a valid number");
		else if (lengthParam == null
				|| !((lengthParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(lengthParam) > 0)))
			throw new Exception("Length Param is null or not a valid number");

		int page = Integer.parseInt(pageParam);
		int length = Integer.parseInt(lengthParam);

		productRepositoryImpl = new ProductRepositoryImpl();
		List<ProductDto> dtos = new ArrayList<ProductDto>();

		int offset = (page - 1) * length;

		List<Product> products = productRepositoryImpl.readAll(offset, length);
		products.forEach(p -> {
			ProductDto productDto = new ProductDto(p.getId(), p.getName(), p.getDescription(), p.getPrice(),
					p.getStock(), p.getPicturePath());
			dtos.add(productDto);
		});

		return dtos;
	}

	@Override
	public ProductDto getProductById(String idParam) throws Exception {
		if (idParam == null || !((idParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(idParam) > 0)))
			throw new Exception("Id Param is null or not a valid number");

		long id = Integer.parseInt(idParam);
		Product product = productRepositoryImpl.readById(id);
		ProductDto dto = null;
		if (product != null)
			dto = new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
					product.getStock(), product.getPicturePath());
		else
			throw new Exception("Product Not Found");

		return dto;
	}

	@Override
	public void updateProduct(String idParam, String nameParam, String descriptionParam, String priceParam,
			String stockParam) throws Exception {
		if (idParam == null || !((idParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(idParam) > 0)))
			throw new Exception("Id Param is null or not a valid number");

		long id = Integer.parseInt(idParam);

		Product product = productRepositoryImpl.readById(id);

		if (product == null)
			throw new Exception("Product not Found");

		if (nameParam != null && !nameParam.isEmpty())
			product.setName(nameParam);
		if (descriptionParam != null && !descriptionParam.isEmpty())
			product.setDescription(descriptionParam);
		if (priceParam == null || !((priceParam.matches("-?\\d+(\\.\\d+)?") && Double.parseDouble(priceParam) > 0)))
			product.setPrice(Double.parseDouble(priceParam));
		if (stockParam == null || !((stockParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(stockParam) > 0)))
			product.setStock(Integer.parseInt(stockParam));

		productRepositoryImpl.update(product);

	}

	@Override
	public void deleteProduct(String idParam) throws Exception {
		if (idParam == null || !((idParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(idParam) > 0)))
			throw new Exception("Id Param is null or not a valid number");

		long id = Integer.parseInt(idParam);

		Product product = productRepositoryImpl.readById(id);
		if (product == null)
			throw new Exception("Product not Found");

		productRepositoryImpl.delete(product);

	}

	@Override
	public List<ProductDto> searchByName(String nameParam, String pageParam, String lengthParam) throws Exception {
		if (nameParam == null || nameParam.isEmpty())
			throw new Exception("Product Name Param is null or empty");
		if (pageParam == null || !((pageParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(pageParam) > 0)))
			throw new Exception("Page Param is null or not a valid number");
		else if (lengthParam == null
				|| !((lengthParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(lengthParam) > 0)))
			throw new Exception("Length Param is null or not a valid number");

		int page = Integer.parseInt(pageParam);
		int length = Integer.parseInt(lengthParam);

		productRepositoryImpl = new ProductRepositoryImpl();
		List<ProductDto> dtos = new ArrayList<ProductDto>();

		int offset = (page - 1) * length;

		List<Product> products = productRepositoryImpl.searchByName(nameParam, offset, length);
		products.forEach(p -> {
			ProductDto productDto = new ProductDto(p.getId(), p.getName(), p.getDescription(), p.getPrice(),
					p.getStock(), p.getPicturePath());
			dtos.add(productDto);
		});

		return dtos;

	}

	@Override
	public long count() throws Exception {
		return productRepositoryImpl.count();
	}

	@Override
	public void createProduct(String nameParam, String descriptionParam, String priceParam, String stockParam,
			String picturePath) throws Exception {

		if (nameParam == null || nameParam.isEmpty())
			throw new Exception("Product Name Param is null or empty");
		if (descriptionParam == null)
			throw new Exception("Product Description Param is null");
		else if (priceParam == null
				|| !((priceParam.matches("-?\\d+(\\.\\d+)?") && Double.parseDouble(priceParam) >= 0)))
			throw new Exception("Price Param is null or not a valid number");
		else if (stockParam == null || !((stockParam.matches("-?\\d+(\\.\\d+)?") && Integer.parseInt(stockParam) >= 0)))
			throw new Exception("Stock Param is null or not a valid number");
		double price = Double.parseDouble(priceParam);
		int stock = Integer.parseInt(stockParam);

		Product product = new Product(nameParam, descriptionParam, price, stock, picturePath);

		productRepositoryImpl.insert(product);

	}

}
