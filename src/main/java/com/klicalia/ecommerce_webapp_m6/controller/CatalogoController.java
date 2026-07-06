package com.klicalia.ecommerce_webapp_m6.controller;

import com.klicalia.ecommerce_webapp_m6.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/catalogo")
    public String verCatalogo(@RequestParam(required = false) String texto,
                               @RequestParam(required = false) Integer idCategoria,
                               Model model) {

        model.addAttribute("productos", productoService.listar(texto, idCategoria));
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("textoBuscado", texto);
        model.addAttribute("idCategoriaSeleccionada", idCategoria);
        return "catalogo";
    }
}