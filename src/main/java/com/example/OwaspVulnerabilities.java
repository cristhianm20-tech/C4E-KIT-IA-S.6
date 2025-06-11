package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OwaspVulnerabilities {
    
    // A01:2021 – Broken Access Control
    public void insecureAccessControl(HttpServletRequest request, HttpServletResponse response) {
        String userRole = request.getParameter("role");
        // Vulnerabilidad: No verificar permisos antes de cambiar roles
        if (userRole != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userRole", userRole);
        }
    }

    // A02:2021 – Cryptographic Failures
    public String insecurePasswordStorage(String password) {
        try {
            // Vulnerabilidad: Uso de MD5 que es criptográficamente inseguro
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // A03:2021 – Injection
    public void sqlInjection(String userInput) {
        try {
            // Vulnerabilidad: SQL Injection
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            // Ejecutar query...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // A04:2021 – Insecure Design
    public void insecureFileUpload(String fileName, byte[] fileContent) {
        try {
            // Vulnerabilidad: No validar el tipo de archivo ni el contenido
            Files.write(Paths.get("/uploads/" + fileName), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // A05:2021 – Security Misconfiguration
    public void insecureConfiguration() {
        // Vulnerabilidad: Configuración insegura de seguridad
        System.setProperty("javax.net.ssl.trustStore", "truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "password123");
    }

    // A06:2021 – Vulnerable and Outdated Components
    public void vulnerableComponent() {
        // Vulnerabilidad: Uso de componente vulnerable (simulado)
        // En un caso real, sería una dependencia con una versión vulnerable
        String vulnerableVersion = "1.0.0";
        System.out.println("Using vulnerable component version: " + vulnerableVersion);
    }

    // A07:2021 – Identification and Authentication Failures
    public boolean weakAuthentication(String username, String password) {
        // Vulnerabilidad: Autenticación débil
        return username.equals("admin") && password.equals("admin123");
    }

    // A08:2021 – Software and Data Integrity Failures
    public void insecureDeserialization(String serializedData) {
        try {
            // Vulnerabilidad: Deserialización insegura
            Object obj = new java.io.ObjectInputStream(
                new java.io.ByteArrayInputStream(serializedData.getBytes())
            ).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // A09:2021 – Security Logging and Monitoring Failures
    public void insecureLogging(String sensitiveData) {
        // Vulnerabilidad: Logging inseguro de datos sensibles
        System.out.println("User data: " + sensitiveData);
    }

    // A10:2021 – Server-Side Request Forgery (SSRF)
    public void ssrfVulnerability(String url) {
        try {
            // Vulnerabilidad: SSRF
            java.net.URL urlObj = new java.net.URL(url);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) urlObj.openConnection();
            conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 