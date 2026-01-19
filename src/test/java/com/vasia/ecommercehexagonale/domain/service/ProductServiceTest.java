package com.vasia.ecommercehexagonale.domain.service;

import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;


    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private int stockQuantity;
    private String vendorId;

    @BeforeEach
    void setup(){
        productId = UUID.randomUUID();
        productName = "Test Product";
        productDescription = "Test Description";
        productPrice = new BigDecimal("99.99");
        stockQuantity = 100;
        vendorId = "vendor-1";
    }


    @Test
    @DisplayName("Should create product successfully")
    void testCreateProductSuccess() {
        //Arrange or Given
        Product expectedProduct = new Product(productId,productName,productDescription,productPrice,stockQuantity,vendorId);

        when(productRepositoryPort.save(any(Product.class))).thenReturn(expectedProduct);

        //Act or When
        Product createdProduct = productService.createProduct(productName,productDescription,productPrice,stockQuantity,vendorId);

        //Assert or Then
        assertEquals(productName, createdProduct.getName());
        verify(productRepositoryPort,times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Should get product By Id")
    void getProductById() {
        //Arrange
        Product productExpected = new Product(productId, productName, productDescription, productPrice, stockQuantity, vendorId);

        when(productRepositoryPort.fidById(productId)).thenReturn(Optional.of(productExpected));

        //Act
        Optional<Product> product = productService.getProductById(productId);

        //Assert
        assertTrue(product.isPresent());
        verify(productRepositoryPort , times(1)).fidById(productId);
    }

    @Test
    @DisplayName("Should get All products")
    void getAllProduct() {
        //Arrange
        Product product1 = new Product(UUID.randomUUID(), "Product 1", "Description 1", new BigDecimal("50.00"), 50, "vendor-1");
        Product product2 = new Product(UUID.randomUUID(), "Product 2", "Description 2", new BigDecimal("75.00"), 75, "vendor-2");

        when(productRepositoryPort.findAll()).thenReturn(List.of(product1,product2));
        //Act
        List<Product> products = productService.getAllProduct();

        //Assert
        assertEquals(2,products.size());
        verify(productRepositoryPort, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete product successfully")
    void deleteProduct() {
        productService.deleteProduct(productId);

        verify(productRepositoryPort, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Should update product successfully")
    void testUpdateProductSuccess() {

        //Assert
        String updatedName = "Updated Product";
        String updatedDescription = "Updated Description";
        BigDecimal updatedPrice = new BigDecimal("149.99");
        int updatedStock = 200;
        String updatedVendor = "vendor-2";

        Product existingProduct = new Product(productId, productName, productDescription, productPrice, stockQuantity, vendorId);
        Product updatedProduct = new Product(productId, updatedName, updatedDescription, updatedPrice, updatedStock, updatedVendor);

        when(productRepositoryPort.fidById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepositoryPort.save(any(Product.class))).thenReturn(updatedProduct);

        //Act
        Product result = productService.updateProduct(productId, updatedName, updatedDescription, updatedPrice, updatedStock, updatedVendor);

        assertEquals(updatedName, result.getName());
        assertEquals(updatedDescription, result.getDescription());
        assertEquals(updatedPrice, result.getPrice());
        assertEquals(updatedStock, result.getStockQuantity());
        assertEquals(updatedVendor, result.getVendorId());
        verify(productRepositoryPort, times(1)).fidById(productId);
        verify(productRepositoryPort, times(1)).save(any(Product.class));
    }
}