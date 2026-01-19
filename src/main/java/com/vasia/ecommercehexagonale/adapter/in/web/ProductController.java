package com.vasia.ecommercehexagonale.adapter.in.web;


import com.vasia.ecommercehexagonale.adapter.in.dto.ProductRequest;
import com.vasia.ecommercehexagonale.adapter.in.dto.ProductResponse;
import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.in.ProductCatalogUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// Ajouts OpenAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "API pour gérer les produits")
public class ProductController {
    private final ProductCatalogUseCase productCatalogUseCase;

    public ProductController(ProductCatalogUseCase productCatalogUseCase) {
        this.productCatalogUseCase = productCatalogUseCase;
    }

    @Operation(summary = "Créer un produit", description = "Crée un nouveau produit avec les informations fournies")
    @PostMapping
    public ResponseEntity<ProductResponse>  createProduct(@Valid @RequestBody ProductRequest request){
        Product product = productCatalogUseCase.createProduct(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity(),
                request.getVendorId()
        );

        return new ResponseEntity<>(toResponse(product), HttpStatus.CREATED);
    }



    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getVendorId()
        );
    }

    @Operation(summary = "Récupérer un produit par ID", description = "Retourne le produit correspondant à l'UUID passé en paramètre")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@Parameter(description = "UUID du produit") @PathVariable UUID id){
        return productCatalogUseCase.getProductById(id)
                .map(product -> new ResponseEntity<>(toResponse(product), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Récupérer tous les produits", description = "Retourne la liste de tous les produits")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productCatalogUseCase.getAllProduct().stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Mettre à jour un produit", description = "Met à jour un produit existant par ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@Parameter(description = "UUID du produit") @PathVariable UUID
                                                                 id, @jakarta.validation.Valid @RequestBody ProductRequest request) {
        try {
            Product updatedProduct = productCatalogUseCase.updateProduct(
                    id,
                    request.getName(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getStockQuantity(),
                    request.getVendorId()
            );
            return new ResponseEntity<>(toResponse(updatedProduct),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Supprimer un produit", description = "Supprime le produit identifié par l'UUID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@Parameter(description = "UUID du produit") @PathVariable UUID id) {
        productCatalogUseCase.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
