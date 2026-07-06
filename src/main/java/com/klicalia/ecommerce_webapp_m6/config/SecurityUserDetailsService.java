package com.klicalia.ecommerce_webapp_m6.config;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario con email: " + email));

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()))
        );
    }
}