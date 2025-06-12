package com.proyecto.repository;

import com.proyecto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByEmail(String email);
    
    @Query("SELECT u FROM Usuario u WHERE u.role = :role")
    Usuario findByRole(@Param("role") String role);
    
    @Query("SELECT u FROM Usuario u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Usuario searchUsers(@Param("search") String search);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
} 