package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.Product;
import com.denton.shop.clothshop.repository.ProductRepository;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> addProduct(Product product){
        System.out.println("Product details before setting: " + product.toString());
        try{
            Product p = new Product();
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setCategory(product.getCategory());
            p.setSubcategory(product.getSubcategory());
            p.setGender(product.getGender());
            p.setAddedOn(LocalDateTime.now());
            p.setPrice(product.getPrice());
            p.setModel(product.getModel());
            p.setManufacturer(product.getManufacturer());
            p.setLastUpdatedOn(product.getLastUpdatedOn());
            p.setQuantityAvailable(product.getQuantityAvailable());
            p.setImageUrls(product.getImageUrls());
            productRepository.save(p);
            return new ResponseEntity<>("Product Added Successfully.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Failed to add Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateProduct(Product product){
        try{
            Optional<Product> optionalProduct = productRepository.findById(product.getId());
            if(optionalProduct.isPresent()){
                Product p = optionalProduct.get();
                if(product.getName() != null && !product.getName().isEmpty()) {
                    p.setName(product.getName());
                }
                if(product.getDescription() != null) {
                    p.setDescription(product.getDescription());
                }
                if(product.getPrice() != null) {
                    p.setPrice(product.getPrice());
                }
                p.setQuantityAvailable(product.getQuantityAvailable());
                if(product.getGender() != null && !product.getGender().isEmpty()) {
                    p.setGender(product.getGender());
                }
                if(product.getManufacturer() != null && !product.getManufacturer().isEmpty()) {
                    p.setManufacturer(product.getManufacturer());
                }
                if(product.getCategory() != null && !product.getCategory().isEmpty()) {
                    p.setCategory(product.getCategory());
                }
                if(product.getSubcategory() != null && !product.getSubcategory().isEmpty()) {
                    p.setSubcategory(product.getSubcategory());
                }
                if(product.getModel() != null && !product.getModel().isEmpty()) {
                    p.setModel(product.getModel());
                }
                if(product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
                    p.setImageUrls(product.getImageUrls());
                }
                p.setLastUpdatedOn(LocalDateTime.now());
                productRepository.save(p);
                return new ResponseEntity<>("Product Updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> deleteProduct(long productId){
        try{
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if(optionalProduct.isPresent()){
                productRepository.delete(optionalProduct.get());
                return new ResponseEntity<>("Product Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to delete Product", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<List<Product>> getAllProducts(){
        try{
            List<Product> products=productRepository.findAll();
            return new ResponseEntity<>(products,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
}
