package com.klicalia.ecommerce_webapp_m6.config;

import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import com.klicalia.ecommerce_webapp_m6.service.CarritoService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoService carritoService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                .requestMatchers(
                	    "/", "/register", "/login",
                	    "/styles.css", "/admin.css", "/hero.css", "/scroll.js",
                	    "/Logo-completo.png", "/medium-shot-kid-taking-notes.jpg",
                	    "/arrangement-different-letters-speech-therapy-sessions.jpg",
                	    "/matematicas-taller.png",
                	    "/close-up-mom-helping-son-paint-eggs.jpg",
                	    "/ingles-taller.png",
                	    "/carrito", "/carrito/agregar", "/carrito/eliminar",
                	    "/pago/retorno"
                	).permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/pago/retorno"))
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(new RoleBasedAuthenticationSuccessHandler(usuarioRepository, carritoService))
                .permitAll()
            );

        return http.build();
    }
}