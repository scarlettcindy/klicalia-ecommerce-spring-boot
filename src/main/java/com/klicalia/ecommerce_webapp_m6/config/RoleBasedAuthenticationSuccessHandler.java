package com.klicalia.ecommerce_webapp_m6.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

public class RoleBasedAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {

        boolean esAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        String destino = esAdmin ? "/admin/products" : "/catalogo";

        getRedirectStrategy().sendRedirect(request, response, destino);
    }
}