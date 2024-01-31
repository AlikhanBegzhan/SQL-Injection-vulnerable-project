package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductsModel {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @JsonProperty("product_name")
    String productName;

    @JsonProperty("product_price")
    Integer productPrice;

    @JsonProperty("product_description")
    String productDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductsModel that)) return false;
        return Objects.equals(productName, that.productName) && Objects.equals(productPrice, that.productPrice) && Objects.equals(productDescription, that.productDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productPrice, productDescription);
    }

    @Override
    public String toString() {
        return "ProductsModel{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}
