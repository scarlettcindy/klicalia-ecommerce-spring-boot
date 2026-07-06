package com.klicalia.ecommerce_webapp_m6.repository;

import com.klicalia.ecommerce_webapp_m6.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByActivoTrueOrderByNombreAsc();

    List<Producto> findByActivoTrueAndCategoriaIdOrderByNombreAsc(Integer idCategoria);

    List<Producto> findByActivoTrueAndNombreContainingIgnoreCaseOrderByNombreAsc(String texto);

    List<Producto> findByActivoTrueAndCategoriaIdAndNombreContainingIgnoreCaseOrderByNombreAsc(Integer idCategoria, String texto);
}