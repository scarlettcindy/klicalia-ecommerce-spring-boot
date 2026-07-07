package com.klicalia.ecommerce_webapp_m6.repository;

import com.klicalia.ecommerce_webapp_m6.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {

    List<ItemCarrito> findByUsuarioId(Integer idUsuario);

    Optional<ItemCarrito> findByUsuarioIdAndProductoId(Integer idUsuario, Integer idProducto);

    void deleteByUsuarioId(Integer idUsuario);
}