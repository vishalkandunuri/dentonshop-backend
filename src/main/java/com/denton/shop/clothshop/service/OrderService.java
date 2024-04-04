package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.CartItem;
import com.denton.shop.clothshop.model.Order;
import com.denton.shop.clothshop.model.OrderStatus;
import com.denton.shop.clothshop.repository.CartItemRepository;
import com.denton.shop.clothshop.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartItemService cartItemService;
    public ResponseEntity<?> addNewOrder(Long[] cartItems, String paymentId,
                                         String orderAddress, String orderEmail, BigDecimal orderPrice){
        try{
            // Change Cart Item Status to ORDERED
            ResponseEntity<?> response = cartItemService.changeItemsCarttoOrder(cartItems);
            if (response.getStatusCode() != HttpStatus.OK) {
                // If changing cart items to ORDERED fails, return the response
                return response;
            }

            Order order=new Order();
            order.setOrderAddress(orderAddress);
            order.setOrderTotalPrice(orderPrice);
            order.setStatus(OrderStatus.PLACED);
            order.setUserEmail(orderEmail);
            order.setPaymentId(paymentId);
            order.setOrderedOn(LocalDateTime.now());
            order.setItems(cartItems);
            orderRepository.save(order);

            return new ResponseEntity<>("Order Placed Successfully.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to Place Order.",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> changeOrderStatus(Long orderId, String newStatus){
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                switch (newStatus){
                    case "PLACED":
                        order.setStatus(OrderStatus.PLACED);
                        break;
                    case "INTRANSIT":
                        order.setStatus(OrderStatus.INTRANSIT);
                        break;
                    case "DELIVERED":
                        order.setStatus(OrderStatus.DELIVERED);
                        break;
                    default:
                        return new ResponseEntity<>("Invalid order status provided.", HttpStatus.BAD_REQUEST);
                }
                orderRepository.save(order);
                return new ResponseEntity<>("Order status updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Order not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update order status.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Order>> getAllOrders(){
        try {
            List<Order> allOrders=orderRepository.findAll();
            return new ResponseEntity<>(allOrders,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<List<Order>> getUserOrders(String email){
        try {
            List<Order> userOrders = orderRepository.findByUserEmail(email);
            return new ResponseEntity<>(userOrders, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }
}
