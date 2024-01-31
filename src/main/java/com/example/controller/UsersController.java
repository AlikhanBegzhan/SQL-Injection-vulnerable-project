package com.example.controller;

import com.example.model.UsersModel;
import com.example.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.HashMap;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel) throws SQLException {
        System.out.println("register request: " + usersModel);
        UsersModel registeredUser = usersService.unsafeRegisterUser(usersModel.getLogin(), usersModel.getPassword());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel) throws SQLException {
        System.out.println("login request: " + usersModel);
        UsersModel authenticatedUser = usersService.unsafeAuthenticate(usersModel.getLogin(), usersModel.getPassword());
        if (authenticatedUser != null) {
            return "redirect:/personalPage?userId=" + authenticatedUser.getId();
        } else {
            return "error_page";
        }
    }

    @GetMapping("/personalPage")
    public String getPersonalPage(@RequestParam(name = "userId") String userId, Model model) {
        try {
            HashMap<String, String> userData = usersService.findUserByIdParams(userId);
            model.addAttribute("userLogin", userData.get("userLogin"));
            model.addAttribute("userPassword", userData.get("userPassword"));

            model.addAttribute("userId", userId);
            return "personal_page";
        } catch (SQLException e) {
            model.addAttribute("errorMessage", "Error code: " +
                    e.getErrorCode() + "; Error message: " + e.getMessage());
            return "error_page_sql";
        }
    }

}
