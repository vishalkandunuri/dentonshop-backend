package com.denton.shop.clothshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity_available")
    private int quantityAvailable;

    @Column(name = "gender")
    private String gender;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "category")
    private String category;
    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "model")
    private String model;

    @Column(name = "added_on")
    private LocalDateTime addedOn;

    @Column(name = "last_updated_on")
    private LocalDateTime lastUpdatedOn;

    @Column(columnDefinition = "text", name = "image_urls")
    private String imageUrls;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", gender='" + gender + '\'' +
                ", addedOn=" + addedOn +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", lastUpdatedOn=" + lastUpdatedOn +
                ", quantityAvailable=" + quantityAvailable +
                ", imageUrls=" + imageUrls +
                '}';
    }

}
