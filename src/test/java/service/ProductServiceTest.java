package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import entity.Product;
import model.ProductDto;
import repository.impl.ProductRepositoryImpl;
import service.impl.ProductServiceImpl;

public class ProductServiceTest {

	@Mock
	ProductRepositoryImpl repository;

	@InjectMocks
	ProductServiceImpl service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new ProductServiceImpl(repository);
	}

	@Test
	public void getAllTest() throws Exception {
		List<Product> mockProductList = Arrays.asList(
				new Product(1, "product 1", "description", 200, 100, "picture1.png"),
				new Product(2, "product 2", "description", 150, 50, "picture2.png"));

		when(repository.readAll(0, 5)).thenReturn(mockProductList);

		List<ProductDto> result = service.getAllProducts("1", "5");

		assertEquals(2, result.size());
		assertEquals("product 1", result.get(0).getName());
		assertEquals("product 2", result.get(1).getName());

		verify(repository).readAll(0, 5);
	}

	@Test
	public void searchTest() throws Exception {
		List<Product> mockProductList = Arrays.asList(
				new Product(1, "product 1", "description", 200, 100, "picture1.png"),
				new Product(2, "product 2", "description", 150, 50, "picture2.png"));

		when(repository.searchByName("product", 0, 5)).thenReturn(mockProductList);

		List<ProductDto> result = service.searchByName("product", "1", "5");

		assertEquals(2, result.size());
		assertEquals("product 1", result.get(0).getName());
		assertEquals("product 2", result.get(1).getName());

		verify(repository).searchByName("product", 0, 5);
	}

}
