package com.klicalia.ecommerce_webapp_m6.controller;

import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import com.klicalia.ecommerce_webapp_m6.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class CarritoController {

    public static final String SESSION_KEY = "carritoInvitado";

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioActual(Principal principal) {
        if (principal == null) {
            return null;
        }
        return usuarioRepository.findByEmail(principal.getName()).orElse(null);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> carritoInvitado(HttpSession session) {
        Map<Integer, Integer> carrito = (Map<Integer, Integer>) session.getAttribute(SESSION_KEY);
        if (carrito == null) {
            carrito = new LinkedHashMap<>();
            session.setAttribute(SESSION_KEY, carrito);
        }
        return carrito;
    }

    @GetMapping("/carrito")
    public String verCarrito(Model model, Principal principal, HttpSession session) {
        Usuario usuario = usuarioActual(principal);

        if (usuario != null) {
            model.addAttribute("items", carritoService.listar(usuario));
            model.addAttribute("total", carritoService.calcularTotal(usuario));
        } else {
            Map<Integer, Integer> carrito = carritoInvitado(session);
            model.addAttribute("itemsInvitado", carritoService.detalleInvitado(carrito));
            model.addAttribute("total", carritoService.totalInvitado(carrito));
        }
        return "carrito";
    }

    @PostMapping("/carrito/agregar")
    public String agregar(@RequestParam Integer idProducto, Principal principal, HttpSession session,
                           jakarta.servlet.http.HttpServletRequest request) {
        Usuario usuario = usuarioActual(principal);

        if (usuario != null) {
            carritoService.agregar(usuario, idProducto);
        } else {
            carritoInvitado(session).merge(idProducto, 1, Integer::sum);
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/catalogo");
    }

    @PostMapping("/carrito/eliminar")
    public String eliminar(@RequestParam(required = false) Integer idItem,
                            @RequestParam(required = false) Integer idProducto,
                            Principal principal, HttpSession session) {
        Usuario usuario = usuarioActual(principal);

        if (usuario != null && idItem != null) {
            carritoService.eliminar(usuario, idItem);
        } else if (idProducto != null) {
            carritoInvitado(session).remove(idProducto);
        }
        return "redirect:/carrito";
    }
}