package com.proyecto;

import com.proyecto.model.Usuario;
import com.proyecto.service.UsuarioService;
import com.proyecto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
    @InjectMocks
    private UsuarioService usuarioService;
    
    private Usuario testUser;
    private Pageable pageable;
    
    @BeforeEach
    void setup() {
        testUser = new Usuario();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("password123");
        testUser.setEmail("test@test.com");
        testUser.setRole("USER");
        
        pageable = PageRequest.of(0, 10);
    }
    
    @Test
    void deberiaRegistrarUsuarioExitosamente() {
        // Arrange
        when(usuarioRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(usuarioRepository.existsByEmail(testUser.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(testUser);
        
        // Act
        Usuario result = usuarioService.registrarUsuario(testUser);
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getEmail(), result.getEmail());
        assertEquals("USER", result.getRole());
        verify(usuarioRepository).save(any(Usuario.class));
    }
    
    @Test
    void deberiaLanzarExcepcionSiUsernameExiste() {
        // Arrange
        when(usuarioRepository.existsByUsername(testUser.getUsername())).thenReturn(true);
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.registrarUsuario(testUser);
        });
        
        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
    
    @Test
    void deberiaAutenticarUsuarioCorrectamente() {
        // Arrange
        when(usuarioRepository.findByUsername(testUser.getUsername()))
            .thenReturn(Optional.of(testUser));
        
        // Act
        boolean result = usuarioService.autenticar(testUser.getUsername(), testUser.getPassword());
        
        // Assert
        assertTrue(result);
        verify(usuarioRepository).findByUsername(testUser.getUsername());
    }
    
    @Test
    void deberiaObtenerUsuariosPorRol() {
        // Arrange
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(testUser);
        when(usuarioRepository.findByRole(eq("USER"))).thenReturn(usuarios.get(0));

        // Act
        Usuario result = usuarioService.obtenerUsuariosPorRol("USER");
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        verify(usuarioRepository).findByRole(eq("USER"));
    }
    
    @Test
    void deberiaObtenerUsuarioPorId() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(testUser));
        
        // Act
        Optional<Usuario> result = usuarioService.obtenerUsuario(1L);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(testUser.getUsername(), result.get().getUsername());
        verify(usuarioRepository).findById(1L);
    }
    
    @Test
    void deberiaBuscarUsuarios() {
        // Arrange
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(testUser);
        Usuario page = usuarios.get(0);
        when(usuarioRepository.searchUsers(anyString())).thenReturn(page);
        
        // Act
        Usuario result = usuarioService.buscarUsuarios("test");
        
        // Assert
        assertNotNull(result);
        assertEquals(testUser.getUsername(), result.getUsername());
        verify(usuarioRepository).searchUsers(eq("test"));
    }
    
    @Test
    void deberiaEliminarUsuario() {
        // Arrange
        doNothing().when(usuarioRepository).deleteById(1L);
        
        // Act
        usuarioService.eliminarUsuario(1L);
        
        // Assert
        verify(usuarioRepository).deleteById(1L);
    }
    
    @Test
    void deberiaActualizarUsuario() {
        // Arrange
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setUsername("updated");
        usuarioActualizado.setEmail("updated@test.com");
        
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);
        
        // Act
        Usuario result = usuarioService.actualizarUsuario(1L, usuarioActualizado);
        
        // Assert
        assertNotNull(result);
        assertEquals("updated", result.getUsername());
        assertEquals("updated@test.com", result.getEmail());
        verify(usuarioRepository).save(any(Usuario.class));
    }
    
    @Test
    void deberiaLanzarExcepcionAlActualizarUsuarioInexistente() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.actualizarUsuario(999L, testUser);
        });
        
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}