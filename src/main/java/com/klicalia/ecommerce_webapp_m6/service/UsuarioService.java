package com.klicalia.ecommerce_webapp_m6.service;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario registrarCliente(String nombre, String apellido, String email, String passwordPlano) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(passwordPlano));
        usuario.setRol("CLIENT");
        return usuarioRepository.save(usuario);
    }

}