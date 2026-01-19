package com.vasia.ecommercehexagonale.domain.service;

import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.in.ProductCatalogUseCase;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService implements ProductCatalogUseCase {

    private final ProductRepositoryPort productRepositoryPort;


    public  ProductService(ProductRepositoryPort productRepositoryPort){
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    public Product createProduct(String name, String description, BigDecimal price, int stockQuantity, String vendorId) {
        Product product= new Product(name, description, price, stockQuantity, vendorId);
        return productRepositoryPort.save(product);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepositoryPort.fidById(id);
    }

    @Override
    public List<Product> getAllProductById(UUID id) {
        return productRepositoryPort.findAll();
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepositoryPort.deleteById(id);
    }

    public Product updateProduct(UUID id , String name,String description, BigDecimal price, int stockQuantity, String vendorId){
        Optional<Product> existingProductOpt = productRepositoryPort.fidById(id);
        if(existingProductOpt.isEmpty()){
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        Product existingProduct = existingProductOpt.get();
        Product updatedProduct = new Product(id,
                name != null ? name : existingProduct.getName(),
                description != null ? description : existingProduct.getDescription(),
                price != null ? price : existingProduct.getPrice(),
                stockQuantity != 0 ? stockQuantity : existingProduct.getStockQuantity(),
                vendorId != null ? vendorId : existingProduct.getVendorId()
        );
        return productRepositoryPort.save(updatedProduct);
    }

}
