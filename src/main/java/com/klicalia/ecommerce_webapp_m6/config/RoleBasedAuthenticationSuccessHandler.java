package com.klicalia.ecommerce_webapp_m6.config;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import com.klicalia.ecommerce_webapp_m6.service.CarritoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

public class RoleBasedAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final CarritoService carritoService;

    public RoleBasedAuthenticationSuccessHandler(UsuarioRepository usuarioRepository, CarritoService carritoService) {
        this.usuarioRepository = usuarioRepository;
        this.carritoService = carritoService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Map<Integer, Integer> carritoInvitado =
                    (Map<Integer, Integer>) session.getAttribute("carritoInvitado");

            if (carritoInvitado != null && !carritoInvitado.isEmpty()) {
                Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElse(null);
                if (usuario != null) {
                    carritoInvitado.forEach((idProducto, cantidad) ->
                            carritoService.agregar(usuario, idProducto, cantidad));
                }
                session.removeAttribute("carritoInvitado");
            }
        }

        boolean esAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(rol -> rol.equals("ROLE_ADMIN"));

        String destino = esAdmin ? "/admin/products" : "/catalogo";

        getRedirectStrategy().sendRedirect(request, response, destino);
    }
}