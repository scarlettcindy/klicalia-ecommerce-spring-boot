package com.klicalia.ecommerce_webapp_m6.controller;

import com.klicalia.ecommerce_webapp_m6.model.Orden;
import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.UsuarioRepository;
import com.klicalia.ecommerce_webapp_m6.service.PagoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/carrito/confirmar")
    public String confirmarCompra(Principal principal, Model model, HttpServletRequest request) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        try {
            String returnUrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath() + "/pago/retorno";

            PagoService.InicioPago inicio = pagoService.iniciarPago(usuario, returnUrl);
            model.addAttribute("token", inicio.token);
            model.addAttribute("url", inicio.url);
            return "redirigir-pago";
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo iniciar el pago: " + e.getMessage());
            return "carrito";
        }
    }

    @RequestMapping(value = "/pago/retorno", method = {RequestMethod.GET, RequestMethod.POST})
    public String retorno(@RequestParam(required = false) String token_ws,
                           Model model) {

        if (token_ws == null) {
            model.addAttribute("cancelado", true);
            return "pago-resultado";
        }

        try {
            Orden orden = pagoService.confirmarPago(token_ws);
            model.addAttribute("orden", orden);
            model.addAttribute("exitoso", "PAGADA".equals(orden.getEstado()));
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo confirmar el pago: " + e.getMessage());
        }

        return "pago-resultado";
    }
}