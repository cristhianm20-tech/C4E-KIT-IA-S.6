package com.proyecto;

import com.proyecto.model.Usuario;
import com.proyecto.service.UsuarioService;
import com.proyecto.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UsuarioServiceTest {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private Usuario testUser;
    
    @Before
    public void setup() {
        // Limpiamos la base de datos antes de cada test
        usuarioRepository.deleteAll();
        
        // Creamos y guardamos el usuario de prueba
        testUser = new Usuario();
        testUser.username = "testuser";
        testUser.password = "password123";
        testUser.email = "test@test.com";
        testUser.role = "USER";
        testUser = usuarioRepository.save(testUser);
        
        // Verificamos que el usuario se guardó correctamente
        assertNotNull("El usuario de prueba no debería ser null", testUser);
        assertNotNull("El ID del usuario de prueba no debería ser null", testUser.id);
    }
    
    @Test
    public void testRegistrarUsuario() {
        // Registramos un nuevo usuario
        Usuario nuevoUsuario = usuarioService.registrarUsuario("newuser", "pass123", "new@test.com");
        
        // Verificamos que el usuario se registró correctamente
        assertNotNull("El usuario no debería ser null", nuevoUsuario);
        assertNotNull("El ID del usuario no debería ser null", nuevoUsuario.id);
        assertEquals("El username debería coincidir", "newuser", nuevoUsuario.username);
        assertEquals("El email debería coincidir", "new@test.com", nuevoUsuario.email);
        assertEquals("El rol debería ser USER", "USER", nuevoUsuario.role);
        
        // Verificamos que el usuario existe en la base de datos
        Usuario usuarioGuardado = usuarioRepository.findById(nuevoUsuario.id).orElse(null);
        assertNotNull("El usuario debería existir en la base de datos", usuarioGuardado);
        assertEquals("El username debería coincidir", "newuser", usuarioGuardado.username);
    }
    
    @Test
    public void testLogin() {
        // Registramos un usuario específico para el test de login
        Usuario loginUser = usuarioService.registrarUsuario("loginuser", "loginpass", "login@test.com");
        assertNotNull("El usuario de login no debería ser null", loginUser);
        assertNotNull("El ID del usuario de login no debería ser null", loginUser.id);
        
        // Intentamos hacer login con credenciales correctas
        boolean result = usuarioService.login("loginuser", "loginpass");
        assertTrue("El login debería ser exitoso con credenciales correctas", result);
        
        // Intentamos hacer login con credenciales incorrectas
        boolean resultFail = usuarioService.login("loginuser", "wrongpass");
        assertFalse("El login debería fallar con contraseña incorrecta", resultFail);
        
        // Intentamos hacer login con usuario inexistente
        boolean resultNoUser = usuarioService.login("nonexistent", "pass");
        assertFalse("El login debería fallar con usuario inexistente", resultNoUser);
    }
    
    @Test
    public void testObtenerUsuariosPorRol() {
        // Creamos varios usuarios con el mismo rol
        Usuario user1 = usuarioService.registrarUsuario("user1", "pass1", "user1@test.com");
        Usuario user2 = usuarioService.registrarUsuario("user2", "pass2", "user2@test.com");
        
        assertNotNull("user1 no debería ser null", user1);
        assertNotNull("user2 no debería ser null", user2);
        
        // Obtenemos todos los usuarios con rol USER
        List<Usuario> usuarios = usuarioService.obtenerUsuariosPorRol("USER");
        
        // Verificamos la lista de usuarios
        assertNotNull("La lista de usuarios no debería ser null", usuarios);
        assertFalse("La lista de usuarios no debería estar vacía", usuarios.isEmpty());
        assertTrue("Debería haber al menos 3 usuarios (testUser + 2 nuevos)", usuarios.size() >= 3);
        
        // Verificamos que los usuarios creados están en la lista
        boolean contieneUser1 = usuarios.stream().anyMatch(u -> u.username.equals("user1"));
        boolean contieneUser2 = usuarios.stream().anyMatch(u -> u.username.equals("user2"));
        assertTrue("La lista debería contener user1", contieneUser1);
        assertTrue("La lista debería contener user2", contieneUser2);
    }
    
    @Test
    public void testEstadisticas() {
        // Verificamos que el usuario de prueba existe
        assertNotNull("El usuario de prueba no debería ser null", testUser);
        assertNotNull("El ID del usuario de prueba no debería ser null", testUser.id);
        
        // Obtenemos la información del usuario
        String info = usuarioService.obtenerInformacionUsuario(testUser.id);
        
        // Verificamos la información
        assertNotNull("La información del usuario no debería ser null", info);
        assertTrue("La información debería contener el username", info.contains(testUser.username));
        assertTrue("La información debería contener el email", info.contains(testUser.email));
        assertTrue("La información debería contener el rol", info.contains(testUser.role));
    }
    
    @Test
    public void testMultipleOperaciones() {
        // Registramos usuarios
        Usuario user1 = usuarioService.registrarUsuario("user1", "pass1", "user1@test.com");
        Usuario user2 = usuarioService.registrarUsuario("user2", "pass2", "user2@test.com");
        
        // Verificamos que los usuarios se registraron correctamente
        assertNotNull("user1 no debería ser null", user1);
        assertNotNull("user2 no debería ser null", user2);
        assertNotNull("El ID de user1 no debería ser null", user1.id);
        assertNotNull("El ID de user2 no debería ser null", user2.id);
        
        // Verificamos login
        boolean login1 = usuarioService.login("user1", "pass1");
        boolean login2 = usuarioService.login("user2", "pass2");
        
        assertTrue("Login de user1 debería ser exitoso", login1);
        assertTrue("Login de user2 debería ser exitoso", login2);
        
        // Verificamos que podemos obtener la información de ambos usuarios
        String info1 = usuarioService.obtenerInformacionUsuario(user1.id);
        String info2 = usuarioService.obtenerInformacionUsuario(user2.id);
        
        assertNotNull("Info de user1 no debería ser null", info1);
        assertNotNull("Info de user2 no debería ser null", info2);
        assertTrue("Info de user1 debería contener el username", info1.contains("user1"));
        assertTrue("Info de user2 debería contener el username", info2.contains("user2"));
    }
} 