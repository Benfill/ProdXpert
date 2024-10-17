package service;

import java.util.List;

import model.ProductDto;

public interface IProductService {

	List<ProductDto> getAllProducts(String offset, String length) throws Exception;

	ProductDto getProductById(String id) throws Exception;

	void createProduct(String nameParam, String descriptionParam, String priceParam, String stockParam,
			String pictureParam) throws Exception;

	void updateProduct(String idParam, String nameParam, String descriptionParam, String priceParam, String stockParam)
			throws Exception;

	void deleteProduct(String id) throws Exception;

	List<ProductDto> searchByName(String name, String pageParam, String length) throws Exception;

	long count() throws Exception;

}
