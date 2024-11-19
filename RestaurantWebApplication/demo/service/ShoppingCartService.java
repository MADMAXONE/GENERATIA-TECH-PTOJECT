package com.RestaurantWebApplication.demo.service;

import com.RestaurantWebApplication.demo.repository.ShoppingCartRepository;
import com.RestaurantWebApplication.demo.entity.ShoppingCartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<ShoppingCartEntity> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCartEntity getShoppingCartById(Long shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId).orElse(null);
    }

    public ShoppingCartEntity createShoppingCart(ShoppingCartEntity shoppingCartEntity) {
        return shoppingCartRepository.save(shoppingCartEntity);
    }

    public ShoppingCartEntity saveShoppingCart(ShoppingCartEntity shoppingCartEntity) {
        if (shoppingCartEntity.getId() == null) {
            return createShoppingCart(shoppingCartEntity);
        } else {
            return shoppingCartRepository.save(shoppingCartEntity);
        }
    }


    public void deleteShoppingCart(Long shoppingCartId) {
        shoppingCartRepository.deleteById(shoppingCartId);
    }



    public ShoppingCartEntity updateShoppingCart(Long shoppingCartId, ShoppingCartEntity updatedShoppingCartEntity) {
        ShoppingCartEntity existingShoppingCartEntity = getShoppingCartById(shoppingCartId);

        if (existingShoppingCartEntity == null) {
            throw new RuntimeException("ShoppingCart not found with ID: " + shoppingCartId);
        }

        existingShoppingCartEntity.setProducts(updatedShoppingCartEntity.getProducts());
        existingShoppingCartEntity.setTotalPrice(updatedShoppingCartEntity.getTotalPrice());
        existingShoppingCartEntity.setNumberOfProducts(updatedShoppingCartEntity.getNumberOfProducts());

        return shoppingCartRepository.save(existingShoppingCartEntity);
    }



}
