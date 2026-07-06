package com.klicalia.ecommerce_webapp_m6.service;

import com.klicalia.ecommerce_webapp_m6.model.Categoria;
import com.klicalia.ecommerce_webapp_m6.model.Producto;
import com.klicalia.ecommerce_webapp_m6.repository.CategoriaRepository;
import com.klicalia.ecommerce_webapp_m6.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Producto> listar(String texto, Integer idCategoria) {
        boolean hayTexto = texto != null && !texto.isBlank();
        boolean hayCategoria = idCategoria != null;

        if (hayTexto && hayCategoria) {
            return productoRepository.findByActivoTrueAndCategoriaIdAndNombreContainingIgnoreCaseOrderByNombreAsc(idCategoria, texto);
        } else if (hayCategoria) {
            return productoRepository.findByActivoTrueAndCategoriaIdOrderByNombreAsc(idCategoria);
        } else if (hayTexto) {
            return productoRepository.findByActivoTrueAndNombreContainingIgnoreCaseOrderByNombreAsc(texto);
        } else {
            return productoRepository.findByActivoTrueOrderByNombreAsc();
        }
    }

    public Optional<Producto> obtenerPorId(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto crear(Producto producto) {
        producto.setActivo(true);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Integer id, Producto datos) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        producto.setCategoria(datos.getCategoria());
        producto.setNombre(datos.getNombre());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());
        return productoRepository.save(producto);
    }

    public void eliminar(Integer id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAllByOrderByNombreAsc();
    }
}