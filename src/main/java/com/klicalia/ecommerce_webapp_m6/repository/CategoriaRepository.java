package com.klicalia.ecommerce_webapp_m6.repository;

import com.klicalia.ecommerce_webapp_m6.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByOrderByNombreAsc();
}