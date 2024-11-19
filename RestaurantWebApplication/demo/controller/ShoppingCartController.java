package com.RestaurantWebApplication.demo.controller;

import com.RestaurantWebApplication.demo.facade.ShoppingCartFacade;
import com.RestaurantWebApplication.demo.model.Quantity;
import com.RestaurantWebApplication.demo.model.ShoppingCart;
import com.RestaurantWebApplication.demo.model.ShoppingCartSubtotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCarts")
public class ShoppingCartController {

    private final ShoppingCartFacade shoppingCartFacade;

    @Autowired
    public ShoppingCartController(ShoppingCartFacade shoppingCartFacade) {
        this.shoppingCartFacade = shoppingCartFacade;
    }

    @GetMapping
    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartFacade.getAllShoppingCarts();
    }

    @GetMapping("/{shoppingCartId}")
    public ShoppingCart getShoppingCart(@PathVariable Long shoppingCartId) {
        return shoppingCartFacade.getShoppingCartById(shoppingCartId);
    }

    @GetMapping("/current")
    public ResponseEntity<ShoppingCart> getCurrentShoppingCart() {
        try {
            ShoppingCart currentShoppingCart = shoppingCartFacade.getCurrentUserShoppingCartModel();
            return new ResponseEntity<>(currentShoppingCart, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current/subtotal")
    public ResponseEntity<ShoppingCartSubtotal> getShoppingCartSubtotal() {
        try {
            ShoppingCartSubtotal subtotal = shoppingCartFacade.getShoppingCartSubtotal();
            return new ResponseEntity<>(subtotal, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public void createShoppingCart(@RequestBody ShoppingCart shoppingCart) {
        shoppingCartFacade.createShoppingCart(shoppingCart);
    }

    @DeleteMapping("/current/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable Long productId) {
        try {
            shoppingCartFacade.deleteProductFromCart(productId);
            return new ResponseEntity<>("Produsul a fost eliminat din coș.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Eroare la ștergerea produsului din coș.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{shoppingCartId}")
    public void deleteShoppingCart(@PathVariable Long shoppingCartId) {
        shoppingCartFacade.deleteShoppingCart(shoppingCartId);
    }

    @PutMapping("/{shoppingCartId}")
    public void updateShoppingCart(@PathVariable Long shoppingCartId, @RequestBody ShoppingCart updatedShoppingCart) {
        shoppingCartFacade.updateShoppingCart(shoppingCartId, updatedShoppingCart);
    }

    @PutMapping("/addProduct/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long productId, @RequestParam Integer quantity){
        shoppingCartFacade.addProductToCart(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/current/products/{productId}")
    public ResponseEntity<String> updateProductQuantity(@PathVariable Long productId, @RequestBody Quantity quantity) {
        try {
            shoppingCartFacade.updateProductQuantity(productId, quantity.getQuantity());
            return new ResponseEntity<>("Cantitatea a fost actualizată cu succes.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Eroare la actualizarea cantității.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
