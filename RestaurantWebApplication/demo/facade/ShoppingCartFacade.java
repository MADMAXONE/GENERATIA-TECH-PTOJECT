package com.RestaurantWebApplication.demo.facade;

import com.RestaurantWebApplication.demo.entity.ProductEntity;
import com.RestaurantWebApplication.demo.entity.ShoppingCartEntity;
import com.RestaurantWebApplication.demo.entity.ShoppingCartProduct;
import com.RestaurantWebApplication.demo.model.ShoppingCart;
import com.RestaurantWebApplication.demo.model.ShoppingCartSubtotal;
import com.RestaurantWebApplication.demo.service.ProductService;
import com.RestaurantWebApplication.demo.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCartFacade {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @Autowired
    private HttpSession httpSession;
    @Autowired
    public ShoppingCartFacade(ShoppingCartService shoppingCartService, ProductService productService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        List<ShoppingCartEntity> shoppingCartEntities = shoppingCartService.getAllShoppingCarts();
        return shoppingCartEntities.stream().map(this::convertToShoppingCartModel).collect(Collectors.toList());
    }

    public ShoppingCart getShoppingCartById(Long shoppingCartId) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartService.getShoppingCartById(shoppingCartId);
        Hibernate.initialize(shoppingCartEntity.getProducts());
        return convertToShoppingCartModel(shoppingCartEntity);
    }


    @Transactional
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
        shoppingCartEntity = shoppingCartService.createShoppingCart(shoppingCartEntity);
        return convertToShoppingCartModel(shoppingCartEntity);
    }

    @Transactional
    public void updateShoppingCart(Long shoppingCartId, ShoppingCart shoppingCart) {
        ShoppingCartEntity shoppingCartEntity = shoppingCartService.getShoppingCartById(shoppingCartId);
        if (shoppingCartEntity == null) {
            throw new RuntimeException("ShoppingCart not found with ID: " + shoppingCartId);
        }
        shoppingCartEntity.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartEntity.setNumberOfProducts(shoppingCart.getNumberOfProducts());

        shoppingCartService.updateShoppingCart(shoppingCartId, shoppingCartEntity);
    }

    @Transactional
    public void deleteShoppingCart(Long shoppingCartId) {
        shoppingCartService.deleteShoppingCart(shoppingCartId);
    }

    public ShoppingCartEntity getCurrentUserShoppingCart() {
        Long shoppingCartId = (Long) httpSession.getAttribute("shoppingCartId");
        System.out.println("Sesiunea actuală are ID-ul coșului de cumpărături: " + shoppingCartId);
        ShoppingCartEntity shoppingCart;

        if (shoppingCartId == null) {
            shoppingCart = new ShoppingCartEntity();
            shoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
            httpSession.setAttribute("shoppingCartId", shoppingCart.getId());
        } else {
            shoppingCart = shoppingCartService.getShoppingCartById(shoppingCartId);
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCartEntity();
                shoppingCart = shoppingCartService.createShoppingCart(shoppingCart);
                httpSession.setAttribute("shoppingCartId", shoppingCart.getId());
            }
        }
        return shoppingCart;
    }


    @Transactional
    public ShoppingCart addProductToCart(Long productId, Integer quantity) {
        ShoppingCartEntity shoppingCartEntity = getCurrentUserShoppingCart();

        ProductEntity product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId + ". Cannot add non-existing product to the cart.");
        }

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }

        ShoppingCartProduct existingProduct = shoppingCartEntity.getProducts().stream()
                .filter(scp -> scp.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
        } else {
            ShoppingCartProduct shoppingCartProduct = new ShoppingCartProduct(shoppingCartEntity, product, quantity);
            shoppingCartEntity.getProducts().add(shoppingCartProduct);
        }

        updateShoppingCartTotals(shoppingCartEntity);

        shoppingCartService.saveShoppingCart(shoppingCartEntity);

        return convertToShoppingCartModel(shoppingCartEntity);
    }

    private void updateShoppingCartTotals(ShoppingCartEntity shoppingCart) {
        float total = 0f;
        long numberOfProducts = 0;

        for (ShoppingCartProduct scp : shoppingCart.getProducts()) {
            total += scp.getProduct().getPrice() * scp.getQuantity();
            numberOfProducts += scp.getQuantity();
        }

        shoppingCart.setTotalPrice(total);
        shoppingCart.setNumberOfProducts(numberOfProducts);
    }

    public ShoppingCart getCurrentUserShoppingCartModel() {
        ShoppingCartEntity shoppingCartEntity = getCurrentUserShoppingCart();
        return convertToShoppingCartModel(shoppingCartEntity);
    }


    @Transactional
    public void updateProductQuantity(Long productId, Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Cantitatea nu poate fi negativă.");
        }

        ShoppingCartEntity shoppingCartEntity = getCurrentUserShoppingCart();
        ShoppingCartProduct shoppingCartProduct = shoppingCartEntity.getProducts().stream()
                .filter(scp -> scp.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost găsit în coș."));

        if (quantity == 0) {
            shoppingCartEntity.getProducts().remove(shoppingCartProduct);
        } else {
            shoppingCartProduct.setQuantity(quantity);
        }

        updateShoppingCartTotals(shoppingCartEntity);
        shoppingCartService.saveShoppingCart(shoppingCartEntity);
    }

    @Transactional
    public void deleteProductFromCart(Long productId) {
        ShoppingCartEntity shoppingCartEntity = getCurrentUserShoppingCart();
        ShoppingCartProduct shoppingCartProduct = shoppingCartEntity.getProducts().stream()
                .filter(scp -> scp.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produsul nu a fost găsit în coș."));

        shoppingCartEntity.getProducts().remove(shoppingCartProduct);
        updateShoppingCartTotals(shoppingCartEntity);
        shoppingCartService.saveShoppingCart(shoppingCartEntity);
    }

    public ShoppingCartSubtotal getShoppingCartSubtotal() {
        ShoppingCartEntity shoppingCartEntity = getCurrentUserShoppingCart();
        updateShoppingCartTotals(shoppingCartEntity);
        return new ShoppingCartSubtotal(shoppingCartEntity.getTotalPrice(), shoppingCartEntity.getNumberOfProducts());
    }


    private ShoppingCart convertToShoppingCartModel(ShoppingCartEntity shoppingCartEntity) {
        ShoppingCart shoppingCartModel = new ShoppingCart();
        List<ShoppingCart.ProductInCart> productInCartList = shoppingCartEntity.getProducts().stream().map(scp -> {
            ShoppingCart.ProductInCart productInCart = new ShoppingCart.ProductInCart();
            productInCart.setProductId(scp.getProduct().getId());
            productInCart.setProductName(scp.getProduct().getName());
            productInCart.setPrice(scp.getProduct().getPrice());
            productInCart.setQuantity(scp.getQuantity());
            return productInCart;
        }).collect(Collectors.toList());

        shoppingCartModel.setProducts(productInCartList);
        shoppingCartModel.setTotalPrice(shoppingCartEntity.getTotalPrice());
        shoppingCartModel.setNumberOfProducts(shoppingCartEntity.getNumberOfProducts());
        return shoppingCartModel;
    }




}
