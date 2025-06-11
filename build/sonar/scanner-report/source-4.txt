package com.proyecto.repository;

import com.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // ⚠️ Consulta SQL vulnerable a inyección
    @Query(value = "SELECT * FROM usuarios WHERE username = :username", nativeQuery = true)
    Usuario findByUsernameInseguro(@Param("username") String username);
    
    // ⚠️ Consulta con retardo intencional
    @Query(value = "SELECT * FROM usuarios WHERE role = :role", nativeQuery = true)
    List<Usuario> findByRoleWithDelay(@Param("role") String role);
    
    // ⚠️ Método que mezcla responsabilidades
    default List<Usuario> findAndProcessUsers(String role) {
        try {
            // ⚠️ Retardo intencional
            Thread.sleep(1000);
            
            // ⚠️ Lógica de negocio en el repositorio
            List<Usuario> usuarios = findByRoleWithDelay(role);
            for (Usuario usuario : usuarios) {
                usuario.validarYActualizarUsuario();
            }
            return usuarios;
        } catch (InterruptedException e) {
            return null;
        }
    }
    
    // ⚠️ Consulta ineficiente
    @Query(value = "SELECT * FROM usuarios WHERE LOWER(username) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(email) LIKE LOWER(CONCAT('%', :search, '%'))", nativeQuery = true)
    List<Usuario> searchUsersInefficient(@Param("search") String search);
} 