package com.example;

public class Utils {
    // Vulnerabilidad 1: Contraseña hardcodeada
    private static final String DB_PASSWORD = "SuperSecretPassword123!";
    
    // Vulnerabilidad 2: Uso de System.out.println para logging
    public static void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
    
    public static void logError(String message) {
        System.out.println("[ERROR] " + message);
    }
    
    public static String getDatabasePassword() {
        return DB_PASSWORD;
    }
} 