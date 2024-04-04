package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.CartItem;
import com.denton.shop.clothshop.model.CartItemType;
import com.denton.shop.clothshop.model.Product;
import com.denton.shop.clothshop.repository.CartItemRepository;
import com.denton.shop.clothshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addItemtoCart(Long prId, String userEmail, Integer quantity) {
        try {
            if (quantity == 0) {
                Optional<Product> optionalProduct = productRepository.findById(prId);
                if (optionalProduct.isPresent()) {
                    Optional<CartItem> optionalCartItem = cartItemRepository.findByProductAndUserEmailAndCartItemType(optionalProduct.get(), userEmail, CartItemType.INCART);
                    optionalCartItem.ifPresent(cartItemRepository::delete);
                    return new ResponseEntity<>("Item removed from cart successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
            } else {
                Optional<Product> optionalProduct = productRepository.findById(prId);
                if (optionalProduct.isPresent()) {
                    Optional<CartItem> optionalCartItem = cartItemRepository.findByProductAndUserEmailAndCartItemType(optionalProduct.get(), userEmail, CartItemType.INCART);
                    if (optionalCartItem.isPresent()) {
                        CartItem cartItem = optionalCartItem.get();
                        cartItem.setQuantity(quantity);
                        cartItemRepository.save(cartItem);
                    } else {
                        CartItem newCartItem = new CartItem();
                        newCartItem.setProduct(optionalProduct.get());
                        newCartItem.setQuantity(quantity);
                        newCartItem.setUserEmail(userEmail);
                        newCartItem.setCartItemType(CartItemType.INCART);
                        cartItemRepository.save(newCartItem);
                    }
                    return new ResponseEntity<>("Item added to cart successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add/remove item to/from cart", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<CartItem>> getUserCartItems(String userEmail){
        try{
            List<CartItem> userCartItems = cartItemRepository.findAll().stream()
                    .filter(cartItem -> cartItem.getUserEmail().equals(userEmail) && cartItem.getCartItemType() == CartItemType.INCART)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(userCartItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CartItem>> getAllCartItems(){
        try{
            List<CartItem> allCartItems = cartItemRepository.findAll();
            return new ResponseEntity<>(allCartItems, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Integer getCurrentUserCartQuantity(String userEmail) {
        try {
            // Retrieve all cart items for the current user
            List<CartItem> userCartItems = cartItemRepository.findAll().stream()
                    .filter(cartItem -> cartItem.getUserEmail().equals(userEmail) && cartItem.getCartItemType() == CartItemType.INCART)
                    .collect(Collectors.toList());

            int totalQuantity = userCartItems.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            return totalQuantity;
        } catch (Exception e) {
            e.printStackTrace(); // Or log the error
            return null;
        }
    }

    public ResponseEntity<?> changeItemsCarttoOrder(Long[] cartItems) {
        try {
            for (Long cartItemId : cartItems) {
                Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
                if (optionalCartItem.isPresent()) {
                    CartItem cartItem = optionalCartItem.get();
                    cartItem.setCartItemType(CartItemType.ORDERED);
                    cartItemRepository.save(cartItem);

                    Product product = cartItem.getProduct();
                    int currentQuantity = product.getQuantityAvailable();
                    int cartItemQuantity = cartItem.getQuantity();
                    if (currentQuantity >= cartItemQuantity) {
                        product.setQuantityAvailable(currentQuantity - cartItemQuantity);
                        productRepository.save(product);
                    } else {
                        return new ResponseEntity<>("Insufficient quantity for product: " + product.getName(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Cart item not found with ID: " + cartItemId, HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>("Cart items changed to ORDERED successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to change cart items to ORDERED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<CartItem>> getOrderCartItems(Long[] cartItemIds){
        try {
            List<CartItem> orderCartItems = cartItemRepository.findAllById(Arrays.asList(cartItemIds));
            return new ResponseEntity<>(orderCartItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
