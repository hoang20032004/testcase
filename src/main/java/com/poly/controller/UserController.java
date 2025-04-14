package com.poly.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class UserController {
    private Map<String, String> users = new HashMap<>();
    
    public boolean registerUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password required");
        }
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, password);
        return true;
    }

    public boolean loginUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("Missing credentials");
        }
        return password.equals(users.get(username));
    }
    
    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }
    
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        if (!loginUser(username, oldPassword)) {
            return false;
        }
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Invalid password format");
        }
        users.put(username, newPassword);
        return true;
    }
    
    public boolean deleteUser(String username, String password) {
        if (!loginUser(username, password)) {
            return false;
        }
        users.remove(username);
        return true;
    }
    
    private boolean isValidPassword(String password) {
        return password != null && 
               password.length() >= 6 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*\\d.*");
    }
}
