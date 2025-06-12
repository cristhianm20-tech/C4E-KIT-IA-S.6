package com.proyecto.controller;

import com.proyecto.dto.LoginRequestDTO;
import com.proyecto.dto.LoginResponseDTO;
import com.proyecto.dto.UsuarioDTO;
import com.proyecto.mapper.UsuarioMapper;
import com.proyecto.model.Usuario;
import com.proyecto.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.debug("REST request para registrar Usuario : {}", usuarioDTO);
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario result = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(usuarioMapper.toDTO(result));
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        log.debug("REST request para login : {}", loginRequest.getUsername());
        boolean success = usuarioService.autenticar(loginRequest.getUsername(), loginRequest.getPassword());
        
        LoginResponseDTO response = LoginResponseDTO.builder()
            .success(success)
            .message(success ? "Login exitoso" : "Credenciales inválidas")
            .build();
            
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable(value = "id") Long id) {
        log.debug("REST request para obtener Usuario : {}", id);
        return usuarioService.obtenerUsuario(id)
            .map(usuarioMapper::toDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rol/{role}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> obtenerUsuariosPorRol(
            @PathVariable String role) {
        log.debug("REST request para obtener usuarios por rol : {}", role);
        Usuario usuarios = usuarioService.obtenerUsuariosPorRol(role);
        UsuarioDTO result = usuarioMapper.toDTO(usuarios);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/search")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> buscarUsuarios(
            @RequestParam String searchTerm) {
        log.debug("REST request para buscar usuarios : {}", searchTerm);
        Usuario usuarios = usuarioService.buscarUsuarios(searchTerm);
        UsuarioDTO result = usuarioMapper.toDTO(usuarios);
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable(value = "id") Long id) {
        log.debug("REST request para eliminar Usuario : {}", id);
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN') or @securityService.isOwner(#id)")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {
        log.debug("REST request para actualizar Usuario : {}, {}", id, usuarioDTO);
        if (!id.equals(usuarioDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        Usuario result = usuarioService.actualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioMapper.toDTO(result));
    }
}