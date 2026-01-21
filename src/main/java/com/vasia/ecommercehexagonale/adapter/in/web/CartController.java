package com.vasia.ecommercehexagonale.adapter.in.web;



import com.vasia.ecommercehexagonale.adapter.in.dto.CartResponse;
import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.port.in.CartManagementPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
@Tag(name = "Carts", description = "API pour gérer les paniers")
public class CartController {
    private final CartManagementPort cartManagementPort;

    public CartController(CartManagementPort cartManagementPort) {
        this.cartManagementPort = cartManagementPort;
    }


    @Operation(summary = "Récupérer le panier", description = "Retourne le panier correspondant au client")
    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse> getCart(@Parameter(description = "ID du client") @PathVariable String customerId) {
        Cart cart = cartManagementPort.getCartByCustomerId(customerId);
        return new ResponseEntity<>(toResponse(cart), HttpStatus.OK);
    }


    @Operation(summary = "Ajouter un article au panier", description = "Ajoute un produit au panier du client")
    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartResponse> addItem(
            @Parameter(description = "ID du client") @PathVariable String customerId,
            @Parameter(description = "UUID du produit") @RequestParam UUID productId,
            @Parameter(description = "Quantité du produit") @RequestParam int quantity)
    {
        Cart cart = cartManagementPort.addProductToCart(customerId, productId, quantity);
        return new ResponseEntity<>(toResponse(cart), HttpStatus.OK);
    }


    @Operation(summary = "Mettre à jour la quantité d'un article", description = "Met à jour la quantité d'un produit dans le panier")
    @PutMapping("/{customerId}/items")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @Parameter(description = "ID du client") @PathVariable String customerId,
            @Parameter(description = "UUID du produit") @RequestParam UUID productId,
            @Parameter(description = "Nouvelle quantité") @RequestParam int quantity) {
        Cart cart = cartManagementPort.updateProductQuantity(customerId, productId, quantity);
        return new ResponseEntity<>(toResponse(cart), HttpStatus.OK);
    }


    @Operation(summary = "Supprimer un article du panier", description = "Supprime un produit du panier du client")
    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @Parameter(description = "ID du client") @PathVariable String customerId,
            @Parameter(description = "UUID du produit") @PathVariable UUID productId) {
        Cart cart = cartManagementPort.removeProductFromCart(customerId, productId);
        return new ResponseEntity<>(toResponse(cart), HttpStatus.OK);
    }


    @Operation(summary = "Appliquer un code promo", description = "Applique une réduction au panier via un code promo")
    @PostMapping("/{customerId}/discount")
    public ResponseEntity<CartResponse> applyDiscount(
            @Parameter(description = "ID du client") @PathVariable String customerId,
            @Parameter(description = "Code promo") @RequestParam String code) {
        Cart cart = cartManagementPort.applyDiscountCode(customerId, code);
        return new ResponseEntity<>(toResponse(cart), HttpStatus.OK);
    }



    private CartResponse toResponse(Cart cart) {
        List<CartResponse.CartItemResponse> itemResponses = cart.getItems().values().stream()
                .filter(item -> item.getProductId() != null && item.getProductName() != null)
                .map(item -> new CartResponse.CartItemResponse(
                        item.getProductId().toString(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());
        return new CartResponse(cart.getId().toString(), cart.getCustomerId(), itemResponses, cart.getTotalCartPrice());
    }
}