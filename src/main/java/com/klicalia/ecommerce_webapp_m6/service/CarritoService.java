package com.klicalia.ecommerce_webapp_m6.service;

import com.klicalia.ecommerce_webapp_m6.model.ItemCarrito;
import com.klicalia.ecommerce_webapp_m6.model.ItemCarritoInvitado;
import com.klicalia.ecommerce_webapp_m6.model.Producto;
import com.klicalia.ecommerce_webapp_m6.model.Usuario;
import com.klicalia.ecommerce_webapp_m6.repository.ItemCarritoRepository;
import com.klicalia.ecommerce_webapp_m6.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CarritoService {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public void agregar(Usuario usuario, Integer idProducto) {
        agregar(usuario, idProducto, 1);
    }

    public void agregar(Usuario usuario, Integer idProducto, int cantidadAAgregar) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + idProducto));

        ItemCarrito item = itemCarritoRepository
                .findByUsuarioIdAndProductoId(usuario.getId(), idProducto)
                .orElse(null);

        if (item == null) {
            item = new ItemCarrito(usuario, producto, cantidadAAgregar);
        } else {
            item.setCantidad(item.getCantidad() + cantidadAAgregar);
        }

        itemCarritoRepository.save(item);
    }

    public List<ItemCarrito> listar(Usuario usuario) {
        return itemCarritoRepository.findByUsuarioId(usuario.getId());
    }

    public int calcularTotal(Usuario usuario) {
        return listar(usuario).stream()
                .mapToInt(ItemCarrito::getSubtotal)
                .sum();
    }

    public void eliminar(Usuario usuario, Integer idItem) {
        ItemCarrito item = itemCarritoRepository.findById(idItem)
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado: " + idItem));

        if (!item.getUsuario().getId().equals(usuario.getId())) {
            throw new SecurityException("Este carrito no pertenece al usuario actual");
        }

        itemCarritoRepository.delete(item);
    }

    public List<ItemCarritoInvitado> detalleInvitado(Map<Integer, Integer> carrito) {
        List<ItemCarritoInvitado> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : carrito.entrySet()) {
            productoRepository.findById(entry.getKey())
                    .ifPresent(producto -> resultado.add(new ItemCarritoInvitado(producto, entry.getValue())));
        }
        return resultado;
    }

    public int totalInvitado(Map<Integer, Integer> carrito) {
        return detalleInvitado(carrito).stream()
                .mapToInt(ItemCarritoInvitado::getSubtotal)
                .sum();
    }
    public void vaciar(Usuario usuario) {
        listar(usuario).forEach(itemCarritoRepository::delete);
    }
}