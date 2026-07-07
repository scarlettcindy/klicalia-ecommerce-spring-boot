package com.klicalia.ecommerce_webapp_m6.config;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import com.klicalia.ecommerce_webapp_m6.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Map;

@ControllerAdvice
public class CarritoModelAdvice {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @ModelAttribute("cantidadCarrito")
    public int cantidadCarrito(Principal principal, HttpSession session) {
        if (principal != null) {
            Usuario usuario = usuarioRepository.findByEmail(principal.getName()).orElse(null);
            if (usuario != null) {
                return carritoService.listar(usuario).stream()
                        .mapToInt(item -> item.getCantidad())
                        .sum();
            }
        }

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute("carritoInvitado");
        if (carrito == null) {
            return 0;
        }
        return carrito.values().stream().mapToInt(Integer::intValue).sum();
    }
}