package com.denton.shop.clothshop.service;

import com.denton.shop.clothshop.model.SubCategory;
import com.denton.shop.clothshop.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public ResponseEntity<?> addSubCategory(SubCategory subCategory) {
        try {
            SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
            return new ResponseEntity<>(savedSubCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add SubCategory.", HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<?> updateSubCategory(SubCategory updatedSubCategory) {
        try {
            Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(updatedSubCategory.getId());
            if (optionalSubCategory.isPresent()) {
                SubCategory existingSubCategory = optionalSubCategory.get();
                existingSubCategory.setName(updatedSubCategory.getName());
                subCategoryRepository.save(existingSubCategory);
                return new ResponseEntity<>("SubCategory Updated Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("SubCategory not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update SubCategory.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteSubCategory(Long id) {
        try {
            Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);
            if (optionalSubCategory.isPresent()) {
                subCategoryRepository.delete(optionalSubCategory.get());
                return new ResponseEntity<>("SubCategory Deleted Successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("SubCategory not found.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete SubCategory.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllSubCategories() {
        try {
            List<SubCategory> subCategories = subCategoryRepository.findAll();
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve SubCategories.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
