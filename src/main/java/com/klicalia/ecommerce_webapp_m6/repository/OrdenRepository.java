package com.klicalia.ecommerce_webapp_m6.repository;

import com.klicalia.ecommerce_webapp_m6.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    Optional<Orden> findByTokenWs(String tokenWs);
}