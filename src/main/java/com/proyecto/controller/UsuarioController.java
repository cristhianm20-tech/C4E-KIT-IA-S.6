package com.proyecto.controller;

import com.proyecto.model.Usuario;
import com.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    // ⚠️ Inyección de dependencia por campo
    @Autowired
    private UsuarioService usuarioService;
    
    // ⚠️ Variable global mutable
    private static Map<String, Integer> requestCount = new HashMap<>();
    
    // ⚠️ Endpoint sin validación de entrada
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Map<String, String> request) {
        // ⚠️ Sin validación de entrada
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        
        // ⚠️ Sin manejo de excepciones
        Usuario usuario = usuarioService.registrarUsuario(username, password, email);
        return ResponseEntity.ok(usuario);
    }
    
    // ⚠️ Endpoint inseguro de login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        // ⚠️ Sin validación de entrada
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        // ⚠️ Sin límite de intentos
        boolean success = usuarioService.login(username, password);
        
        // ⚠️ Respuesta insegura
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", success ? "Login exitoso" : "Credenciales inválidas");
        
        return ResponseEntity.ok(response);
    }
    
    // ⚠️ Endpoint sin autorización
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        // ⚠️ Sin verificación de permisos
        String info = usuarioService.obtenerInformacionUsuario(id);
        return ResponseEntity.ok(info);
    }
    
    // ⚠️ Endpoint con retardo intencional
    @GetMapping("/rol/{role}")
    public ResponseEntity<?> obtenerUsuariosPorRol(@PathVariable String role) {
        // ⚠️ Sin validación de rol
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol(role);
        return ResponseEntity.ok(usuarios);
    }
    
    // ⚠️ Endpoint con contador global inseguro
    @GetMapping("/stats")
    public ResponseEntity<?> obtenerEstadisticas() {
        // ⚠️ Uso de variable global
        requestCount.put("total", requestCount.getOrDefault("total", 0) + 1);
        return ResponseEntity.ok(requestCount);
    }
} 