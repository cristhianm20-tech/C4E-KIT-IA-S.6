package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SecurityVulnerabilities {
    // Vulnerabilidad 1: SQL Injection
    public void unsafeQuery(String userInput) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db");
            Statement stmt = conn.createStatement();
            // Vulnerabilidad SQL Injection
            stmt.executeQuery("SELECT * FROM users WHERE id = " + userInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Vulnerabilidad 2: Uso de algoritmo de hash inseguro
    public String hashPassword(String password) {
        return String.valueOf(password.hashCode()); // MD5 es inseguro
    }

    // Vulnerabilidad 3: Exposición de información sensible
    public void printUserData(String username, String password) {
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
} 