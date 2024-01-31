package com.example.controller;

import com.example.model.UsersModel;
import com.example.service.ProductsService;
import com.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private UsersService usersService;

    private UsersModel user;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/productsPage")
    public String getProductPage(@RequestParam(name = "userId", required = false) String userId, Model model) throws SQLException {
        if (userId != null) {
            user = usersService.findUserById(userId);
        } else if (user == null) {
            return "error_page";
        }

        model.addAttribute("loggedUser", user);
        model.addAttribute("userId", user.getId());
        model.addAttribute("products", productsService.findAllProducts());

        return "products_page";
    }

    @PostMapping("/productsPage")
    public String filterProductsPage(@RequestParam("productName") String productName, Model model) throws IOException {
        try {
            // KMP Algorithm check
            if (!productsService.KmpStringMatching(productName)) {
                model.addAttribute("loggedUser", user);
                model.addAttribute("products", productsService.filterProducts(productName));
            } else {
                System.out.println("SQL Injection detected! For further information please search in logs.");
                model.addAttribute("loggedUser", user);
                // Return empty product when SQLi detected
//                model.addAttribute("products", new ArrayList<ProductsModel>());
                model.addAttribute("products", productsService.filterProducts(productName));
                model.addAttribute("sqli", "SQL Injection detected!");
            }

            return "products_page";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Error code: " +
                    e.getErrorCode() + "; Error message: " + e.getMessage());
            return "error_page_sql";
        }
    }

}
