package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.Category;
import com.denton.shop.clothshop.model.SubCategory;
import com.denton.shop.clothshop.repository.CategoryRepository;
import com.denton.shop.clothshop.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public ResponseEntity<?> addCategory(Category category){
        try{
            Category c=new Category();
            c.setName(category.getName());
            categoryRepository.save(c);
            return new ResponseEntity<>("Category Added Successful.", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to add Category.", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateCategory(Category updatedCategory){
        try{
            Optional<Category> optionalCategory = categoryRepository.findById(updatedCategory.getId());
            if (optionalCategory.isPresent()) {
                Category existingCategory = optionalCategory.get();
                existingCategory.setName(updatedCategory.getName());
                categoryRepository.save(existingCategory);
                return new ResponseEntity<>("Category Updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Failed to update Category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> deleteCategory(Long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (optionalCategory.isPresent()) {
                categoryRepository.delete(optionalCategory.get());
                return new ResponseEntity<>("Category Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete Category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryRepository.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve Categories.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
