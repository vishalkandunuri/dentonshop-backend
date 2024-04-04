package com.denton.shop.clothshop.repository;

import com.denton.shop.clothshop.model.CartItem;
import com.denton.shop.clothshop.model.CartItemType;
import com.denton.shop.clothshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByProductAndUserEmailAndCartItemType(Product product, String userEmail, CartItemType cartItemType);

}
