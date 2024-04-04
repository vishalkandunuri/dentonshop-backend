package com.denton.shop.clothshop.controller;

import com.denton.shop.clothshop.model.*;
import com.denton.shop.clothshop.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class EcommerceController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private UserAddressesService userAddressesService;

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/addcategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    @PutMapping("/updatecategory")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @GetMapping("/allcategories")
    public ResponseEntity<?> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/addsubcategory")
    public ResponseEntity<?> addSubCategory(@RequestBody SubCategory subCategory) {
        return subCategoryService.addSubCategory(subCategory);
    }

    @PutMapping("/updatesubcategory")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategory subCategory) {
        return subCategoryService.updateSubCategory(subCategory);
    }

    @DeleteMapping("/deletesubcategory/{id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable Long id) {
        return subCategoryService.deleteSubCategory(id);
    }

    @GetMapping("/allsubcategories")
    public ResponseEntity<?> getAllSubCategories() {
        return subCategoryService.getAllSubCategories();
    }

    @PostMapping("/addmanufacturer")
    public ResponseEntity<?> addManufacturer(@RequestBody Manufacturer manufacturer){
       return manufacturerService.addManufacturer(manufacturer);
    }

    @PutMapping("/updatemanufacturer")
    public ResponseEntity<?> updateManufacturer(@RequestBody Manufacturer manufacturer) {
        return manufacturerService.updateManufacturer(manufacturer);
    }

    @DeleteMapping("/deletemanufacturer/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable("id") long manufacturerId) {
        return manufacturerService.deleteManufacturer(manufacturerId);
    }

    @GetMapping("/allmanufacturers")
    public ResponseEntity<List<Manufacturer>> getAllManufacturers() {
        return manufacturerService.getAllManufacturers();
    }

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product){

        return productService.addProduct(product);
    }

    @PutMapping("/updateproduct")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/allproducts")
    public ResponseEntity<List<Product>> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping("/addaddress")
    public ResponseEntity<?> addUserAddress(@RequestBody UserAddresses userAddresses){
        return userAddressesService.addUserAddress(userAddresses);
    }

    @PutMapping("/updateaddress")
    public ResponseEntity<?> updateuserAddress(@RequestBody UserAddresses userAddresses){
        return userAddressesService.updateAddress(userAddresses);
    }

    @GetMapping("/alladdresses")
    public ResponseEntity<List<UserAddresses>> getAllAddresses(){
        return userAddressesService.getAllAddresses();
    }

    @GetMapping("/alluseraddresses")
    public ResponseEntity<List<UserAddresses>> getUserAddresses(
            @RequestParam(name = "email", required = false) String email){
        return userAddressesService.getAllAddressesByEmail(email);
    }

    @DeleteMapping("/deleteaddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") long addressId){
        return userAddressesService.deleteUserAddress(addressId);
    }

    @GetMapping("/additemtocart")
    public ResponseEntity<?> addItemtoCart(
            @RequestParam(name = "id", required = false) Long productId,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "quantity", required = false) Integer quantity){
        return cartItemService.addItemtoCart(productId, email, quantity);
    }


    @GetMapping("/getallcartitems")
    public ResponseEntity<List<CartItem>> getAllCartItems(){
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/getusercartitems")
    public ResponseEntity<List<CartItem>> getUserCartItems(
            @RequestParam(name = "email", required = true) String email){
        return cartItemService.getUserCartItems(email);
    }

    @GetMapping("/getcurrentusercartquantity")
    public Integer getCurrentUserCartQuantity(
            @RequestParam(name = "email", required = true) String email){
        return cartItemService.getCurrentUserCartQuantity(email);
    }

    //Long[] cartItems, String paymentId, String orderAddress, String orderEmail, BigDecimal orderPrice
    @GetMapping("/placeorder")
    public ResponseEntity<?> placeOrder(
            @RequestParam(name = "cartItems", required = false) Long[] cartItems,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "paymentId", required = false) String paymentId,
            @RequestParam(name = "orderPrice", required = false) BigDecimal orderPrice,
            @RequestParam(name = "orderAddress", required = false) String orderAddress){
        return orderService.addNewOrder(cartItems, paymentId, orderAddress, email, orderPrice);
    }

    @GetMapping("/updateorderstatus")
    public ResponseEntity<?> updateOrderStatus(
            @RequestParam(name = "orderId", required = true) long orderId,
            @RequestParam(name = "status", required = true) String newStatus){
        return orderService.changeOrderStatus(orderId,newStatus);
    }

    @GetMapping("/getallorders")
    public ResponseEntity<List<Order>> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/getuserorders")
    public ResponseEntity<List<Order>> getUserOrders(
            @RequestParam(name = "userEmail", required = true) String email){
        return orderService.getUserOrders(email);
    }

    @GetMapping("/getordercartitems")
    public ResponseEntity<List<CartItem>> getOrderCartItems(
            @RequestParam(name = "cartItemsIds", required = true) Long[] cartItemsIds){
        return cartItemService.getOrderCartItems(cartItemsIds);
    }

}
