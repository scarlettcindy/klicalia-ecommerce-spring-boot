package com.klicalia.ecommerce_webapp_m6.repository;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
}