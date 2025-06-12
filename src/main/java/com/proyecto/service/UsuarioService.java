package com.proyecto.service;

import com.proyecto.model.Usuario;
import com.proyecto.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {
    
    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);
    
    private final UsuarioRepository usuarioRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrarUsuario(@Valid Usuario usuario) {
        log.debug("Registrando nuevo usuario: {}", usuario.getUsername());
        
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        usuario.setPassword(usuario.getPassword()); // Aquí deberías codificar la contraseña
        usuario.setRole("USER");
        
        return usuarioRepository.save(usuario);
    }
    
    public boolean autenticar(String username, String password) {
        log.debug("Intentando autenticar usuario: {}", username);
        
        return usuarioRepository.findByUsername(username)
            .map(usuario -> usuario.getPassword().equals(password)) // Aquí deberías usar passwordEncoder.matches(password, usuario.getPassword())
            .orElse(false);
    }
    public Page<Usuario> obtenerUsuariosPorRol(String role, Pageable pageable) {
        log.debug("Buscando usuarios por rol: {}", role);
        return usuarioRepository.findByRole(role, pageable);
    }
    
    public Optional<Usuario> obtenerUsuario(Long id) {
        log.debug("Buscando usuario por ID: {}", id);
        return usuarioRepository.findById(id);
    }
    
    public Page<Usuario> buscarUsuarios(String searchTerm, Pageable pageable) {
        log.debug("Buscando usuarios que coincidan con: {}", searchTerm);
        return usuarioRepository.searchUsers(searchTerm, pageable);
    }
    
    @Transactional
    public void eliminarUsuario(Long id) {
        log.debug("Eliminando usuario con ID: {}", id);
        usuarioRepository.deleteById(id);
    }
    
    @Transactional
    public Usuario actualizarUsuario(Long id, @Valid Usuario usuarioActualizado) {
        log.debug("Actualizando usuario con ID: {}", id);
        
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setUsername(usuarioActualizado.getUsername());
                usuario.setEmail(usuarioActualizado.getEmail());
                if (usuarioActualizado.getPassword() != null) {
                    usuario.setPassword(usuarioActualizado.getPassword()); // Aquí deberías codificar la contraseña
                }
                return usuarioRepository.save(usuario);
            })
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
} 