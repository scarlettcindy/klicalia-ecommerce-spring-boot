package com.klicalia.ecommerce_webapp_m6.model;

public class ItemCarritoInvitado {

    private final Producto producto;
    private final Integer cantidad;

    public ItemCarritoInvitado(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Integer getSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}