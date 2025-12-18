package com.example.service;

import com.example.model.UsersModel;
import com.example.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) throws SQLException {
        this.usersRepository = usersRepository;
    }

    public UsersModel unsafeRegisterUser(String login, String password) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        if (login == null || password == null) {
            return null;
        } else {
            if (usersRepository.findFirstByLogin(login).isPresent()) {
                System.out.println("Duplicate login");
                return null;
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO users (login, password) values ('" + login
                    + "', '" + password + "')");
            con.close();
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            return usersModel;
        }
    }

    public UsersModel unsafeAuthenticate(String login, String password) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT login, password FROM users WHERE login='" + login
                + "' AND password='" + password + "'");
        con.close();
        rs.next();
        UsersModel usersModel = unsafeAuthenticateModel(rs.getString("login"), rs.getString("password"));
        return usersModel;
    }

    public UsersModel unsafeAuthenticateModel(String login, String password) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        UsersModel foundUser = usersRepository.findByLoginAndPassword(login, password).get();
        con.close();
        return foundUser;
    }

    public UsersModel findUserById(String userId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        UsersModel foundUser = usersRepository.findById(Integer.valueOf(userId)).get();
        con.close();

        return foundUser;
    }

    public HashMap<String, String> findUserByIdParams(String userId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id=" + userId);
        con.close();
        rs.next();
        UsersModel foundUser = new UsersModel(rs.getString("login"), rs.getString("password"));
        foundUser.setId(rs.getInt("id"));

        HashMap<String, String> userData = new HashMap<>();
        userData.put("userId", rs.getString("id"));
        userData.put("userLogin", rs.getString("login"));
        userData.put("userPassword", rs.getString("password"));

        return userData;
    }

    public UsersModel getUserInfo(String userId) throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/test", "postgres", "243090");
        Statement stmt = con.createStatement();
        UsersModel badUser = findUserById(userId);
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE login='" + badUser.getLogin() + "'");
        con.close();
        UsersModel user = new UsersModel();
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));

        return user;
    }

}
