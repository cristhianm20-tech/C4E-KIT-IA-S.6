package com.proyecto.service;

import com.proyecto.model.Usuario;
import com.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsuarioService {
    
    // ⚠️ Inyección de dependencia por campo
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // ⚠️ Variable global estática
    private static final AtomicInteger loginAttempts = new AtomicInteger(0);
    
    // ⚠️ Método que viola el principio de responsabilidad única
    public Usuario registrarUsuario(String username, String password, String email) {
        // ⚠️ Validación mínima
        if (username == null || password == null) {
            return null;
        }
        
        // ⚠️ Creación de usuario insegura
        Usuario usuario = new Usuario();
        usuario.username = username;
        usuario.password = password;
        usuario.email = email;
        usuario.role = "USER";
        
        // ⚠️ Lógica de negocio mezclada
        if (usuario.validarYActualizarUsuario()) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }
    
    // ⚠️ Método inseguro de autenticación
    public boolean login(String username, String password) {
        loginAttempts.incrementAndGet();
        
        // Buscar el usuario por username
        Usuario usuario = usuarioRepository.findByUsernameInseguro(username);
        
        // Verificar que el usuario existe y la contraseña coincide
        if (usuario != null && usuario.password != null && usuario.password.equals(password)) {
            return true;
        }
        
        return false;
    }
    
    // ⚠️ Método con retardo intencional
    public List<Usuario> obtenerUsuariosPorRol(String role) {
        try {
            // ⚠️ Retardo innecesario
            Thread.sleep(500);
            return usuarioRepository.findAndProcessUsers(role);
        } catch (InterruptedException e) {
            return null;
        }
    }
    
    // ⚠️ Método que expone datos sensibles
    public String obtenerInformacionUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            // ⚠️ Exposición de datos sensibles
            return usuario.generarReporte();
        }
        return null;
    }
} 