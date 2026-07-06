package com.klicalia.ecommerce_webapp_m6.controller;

import com.klicalia.ecommerce_webapp_m6.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/register")
    public String mostrarFormulario() {
        return "register";
    }

    @PostMapping("/register")
    public String registrar(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String password,
            Model model) {

        if (nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank()
                || email == null || email.isBlank()
                || password == null || password.isBlank()) {
            model.addAttribute("errores", "Todos los campos son obligatorios.");
            return "register";
        }

        if (usuarioService.existeEmail(email)) {
            model.addAttribute("errores", "Ese email ya está registrado.");
            return "register";
        }

        usuarioService.registrarCliente(nombre, apellido, email, password);

        return "redirect:/login?registrado";
    }
}