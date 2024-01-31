package com.example.service;

import com.example.logic.KmpStringMatching;
import com.example.model.ProductsModel;
import com.example.repository.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;

@Service
public class ProductsService {

    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);

    @Autowired
    private ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) throws SQLException {
        this.productsRepository = productsRepository;
    }

    public ArrayList<ProductsModel> findAllProducts() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM products");
        con.close();
        ArrayList<ProductsModel> products = new ArrayList<>();
        while (rs.next()) {
            ProductsModel productsModel = new ProductsModel();
            productsModel.setId(rs.getInt("id"));
            productsModel.setProductName(rs.getString("product_name"));
            productsModel.setProductPrice(rs.getInt("product_price"));
            productsModel.setProductDescription(rs.getString("product_description"));
            products.add(productsModel);
        }

        return products;
    }

    public ArrayList<ProductsModel> filterProducts(String name) throws SQLException {
        if (!name.isBlank()) {
            Connection con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE product_name LIKE '%" + name + "%'");
            con.close();
            ArrayList<ProductsModel> products = new ArrayList<>();
            while (rs.next()) {
                ProductsModel productsModel = new ProductsModel();
                productsModel.setId(rs.getInt("id"));
                productsModel.setProductName(rs.getString("product_name"));
                productsModel.setProductPrice(rs.getInt("product_price"));
                productsModel.setProductDescription(rs.getString("product_description"));
                products.add(productsModel);
            }

            return products;
        } else {
            return findAllProducts();
        }
    }

    public Boolean KmpStringMatching(String txt) throws IOException {

        // Reading file with patterns for KMP algorithm
        Resource resource = new ClassPathResource("KMP_patterns.txt");
        File file = resource.getFile();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        // Patterns file should not be empty
        String[] patterns = new String(Files.readAllBytes(file.toPath())).split("\r\n");

//        System.out.println(Arrays.asList(patterns));
        ArrayList<String> patternMatches = new KmpStringMatching().KMPSearch(patterns, txt);

        if (patternMatches.isEmpty()) {
            return false;
        } else {
            logger.info("SQL Injection Detected!");
            for (String patternMatch : patternMatches) {
                logger.info(patternMatch);
            }
            return true;
        }

    }


}
