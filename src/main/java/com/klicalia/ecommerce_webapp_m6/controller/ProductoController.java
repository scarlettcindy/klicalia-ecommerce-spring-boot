package com.klicalia.ecommerce_webapp_m6.controller;

import com.klicalia.ecommerce_webapp_m6.model.Categoria;
import com.klicalia.ecommerce_webapp_m6.model.Producto;
import com.klicalia.ecommerce_webapp_m6.repository.CategoriaRepository;
import com.klicalia.ecommerce_webapp_m6.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // ---------------------------------------------------------- LISTAR ----

    @GetMapping
    public String listar(@RequestParam(required = false) String texto,
                          @RequestParam(required = false) Integer idCategoria,
                          Model model) {

        model.addAttribute("productos", productoService.listar(texto, idCategoria));
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("textoBuscado", texto);
        model.addAttribute("idCategoriaSeleccionada", idCategoria);
        return "admin/productos-list";
    }

    // ------------------------------------------------------ FORM NUEVO ----

    @GetMapping("/new")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("producto", new Producto());
        model.addAttribute("modo", "crear");
        return "admin/producto-form";
    }

    // ----------------------------------------------------- FORM EDITAR ----

    @GetMapping("/edit")
    public String mostrarFormularioEditar(@RequestParam Integer id, Model model) {
        Optional<Producto> productoOpt = productoService.obtenerPorId(id);

        if (productoOpt.isEmpty()) {
            return "redirect:/admin/products?msg=no_encontrado";
        }

        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("producto", productoOpt.get());
        model.addAttribute("modo", "editar");
        return "admin/producto-form";
    }

    // ----------------------------------------------------------- CREAR ----

    @PostMapping
    public String crear(@RequestParam String nombre,
                         @RequestParam(required = false) String descripcion,
                         @RequestParam String precio,
                         @RequestParam(required = false) String idCategoria,
                         Model model) {

        String errores = validar(nombre, precio, idCategoria);

        if (errores != null) {
            prepararFormularioConError(model, errores, nombre, descripcion, precio, "crear");
            return "admin/producto-form";
        }

        Producto producto = new Producto();
        producto.setCategoria(categoriaRepository.findById(Integer.parseInt(idCategoria)).orElse(null));
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(Integer.parseInt(precio));

        productoService.crear(producto);
        return "redirect:/admin/products?msg=creado";
    }

    // ------------------------------------------------------- ACTUALIZAR ---

    @PostMapping("/update")
    public String actualizar(@RequestParam Integer id,
                              @RequestParam String nombre,
                              @RequestParam(required = false) String descripcion,
                              @RequestParam String precio,
                              @RequestParam(required = false) String idCategoria,
                              Model model) {

        String errores = validar(nombre, precio, idCategoria);

        if (errores != null) {
            prepararFormularioConError(model, errores, nombre, descripcion, precio, "editar");
            return "admin/producto-form";
        }

        Producto datos = new Producto();
        datos.setCategoria(categoriaRepository.findById(Integer.parseInt(idCategoria)).orElse(null));
        datos.setNombre(nombre);
        datos.setDescripcion(descripcion);
        datos.setPrecio(Integer.parseInt(precio));

        productoService.actualizar(id, datos);
        return "redirect:/admin/products?msg=editado";
    }

    // --------------------------------------------------------- ELIMINAR ---

    @PostMapping("/delete")
    public String eliminar(@RequestParam Integer id) {
        productoService.eliminar(id);
        return "redirect:/admin/products?msg=eliminado";
    }

    // ------------------------------------------------------- VALIDACIÓN ---

    private String validar(String nombre, String precio, String idCategoria) {
        StringBuilder errores = new StringBuilder();

        if (nombre == null || nombre.isBlank()) {
            errores.append("El nombre es obligatorio. ");
        }
        if (idCategoria == null || idCategoria.isBlank()) {
            errores.append("Debes seleccionar una categoría. ");
        }
        try {
            int p = Integer.parseInt(precio);
            if (p <= 0) {
                errores.append("El precio debe ser mayor a 0. ");
            }
        } catch (NumberFormatException e) {
            errores.append("El precio debe ser un número válido. ");
        }

        return errores.length() > 0 ? errores.toString() : null;
    }

    private void prepararFormularioConError(Model model, String errores, String nombre,
                                             String descripcion, String precio, String modo) {
        model.addAttribute("errores", errores);
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("modo", modo);

        Producto productoForm = new Producto();
        productoForm.setNombre(nombre);
        productoForm.setDescripcion(descripcion);
        try {
            productoForm.setPrecio(Integer.parseInt(precio));
        } catch (NumberFormatException ignored) {
        }
        model.addAttribute("producto", productoForm);
    }
}