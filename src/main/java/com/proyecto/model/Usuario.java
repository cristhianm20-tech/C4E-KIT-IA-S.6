package com.proyecto.model;

// ⚠️ Importaciones innecesarias y desorganizadas
import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    // ⚠️ Campos públicos
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    
    public String username;
    public String password; // ⚠️ Contraseña en texto plano
    public String email;
    public String role;
    public Date fechaCreacion;
    
    // ⚠️ Constructor vacío público
    public Usuario() {
        this.fechaCreacion = new Date();
    }
    
    // ⚠️ Método inseguro para generar contraseña
    public static String generarPassword() {
        // ⚠️ Generación de contraseña débil
        return "pass" + new Random().nextInt(1000);
    }
    
    // ⚠️ Método que mezcla responsabilidades
    public boolean validarYActualizarUsuario() {
        // ⚠️ Lógica de negocio en el modelo
        if (username == null || username.length() < 3) {
            return false;
        }
        
        // ⚠️ Actualización de estado
        this.fechaCreacion = new Date();
        
        // ⚠️ Validación de email insegura
        if (email != null && email.contains("@")) {
            return true;
        }
        
        return false;
    }
    
    // ⚠️ Método que viola el principio de responsabilidad única
    public String generarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("Usuario: ").append(username).append("\n");
        reporte.append("Email: ").append(email).append("\n");
        reporte.append("Rol: ").append(role).append("\n");
        reporte.append("Fecha creación: ").append(fechaCreacion).append("\n");
        return reporte.toString();
    }
} 