package com.klicalia.ecommerce_webapp_m6.service;

import cl.transbank.webpay.webpayplus.WebpayPlus;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCommitResponse;
import cl.transbank.webpay.webpayplus.responses.WebpayPlusTransactionCreateResponse;
import com.klicalia.ecommerce_webapp_m6.model.*;
import com.klicalia.ecommerce_webapp_m6.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PagoService {

    // Credenciales públicas de PRUEBA de Transbank (ambiente de integración,
    // las mismas para todos los desarrolladores, no son secretas)
    private static final String COMMERCE_CODE = "597055555532";
    private static final String API_KEY = "579B532A7440BB0C9079DED94D31EA1615BACEB56610332264630D42D0A36B1C";

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private OrdenRepository ordenRepository;

    public static class InicioPago {
        public final String token;
        public final String url;
        public InicioPago(String token, String url) {
            this.token = token;
            this.url = url;
        }
    }

    public InicioPago iniciarPago(Usuario usuario, String returnUrl) throws Exception {
        List<ItemCarrito> items = carritoService.listar(usuario);
        if (items.isEmpty()) {
            throw new IllegalStateException("El carrito está vacío");
        }

        int total = carritoService.calcularTotal(usuario);

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setTotal(total);
        orden.setEstado("PENDIENTE");
        orden.setFecha(LocalDateTime.now());
        orden.setBuyOrder("KLI" + System.currentTimeMillis());
        orden.setSessionId(UUID.randomUUID().toString());

        for (ItemCarrito item : items) {
            DetalleOrden detalle = new DetalleOrden();
            detalle.setOrden(orden);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getProducto().getPrecio());
            detalle.setSubtotal(item.getSubtotal());
            orden.getDetalles().add(detalle);
        }

        ordenRepository.save(orden);

        WebpayPlus.Transaction transaction = WebpayPlus.Transaction.buildForIntegration(COMMERCE_CODE, API_KEY);

        WebpayPlusTransactionCreateResponse response = transaction.create(
                orden.getBuyOrder(), orden.getSessionId(), total, returnUrl);

        orden.setTokenWs(response.getToken());
        ordenRepository.save(orden);

        return new InicioPago(response.getToken(), response.getUrl());
    }

    public Orden confirmarPago(String token) throws Exception {
        Orden orden = ordenRepository.findByTokenWs(token)
                .orElseThrow(() -> new IllegalStateException("Orden no encontrada para el token: " + token));

        WebpayPlus.Transaction transaction = WebpayPlus.Transaction.buildForIntegration(COMMERCE_CODE, API_KEY);
        WebpayPlusTransactionCommitResponse response = transaction.commit(token);

        boolean exitoso = response.getResponseCode() == 0 && "AUTHORIZED".equals(response.getStatus());
        orden.setEstado(exitoso ? "PAGADA" : "FALLIDA");
        ordenRepository.save(orden);

        if (exitoso) {
            carritoService.vaciar(orden.getUsuario());
        }

        return orden;
    }
}