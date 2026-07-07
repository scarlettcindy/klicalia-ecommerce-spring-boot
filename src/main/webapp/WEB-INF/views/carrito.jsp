<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Klicalia | Mi carrito</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h4 mb-0">Mi carrito</h1>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline-secondary btn-sm">Seguir comprando</a>
    </div>

    <c:set var="listaItems" value="${not empty items ? items : itemsInvitado}" />

    <c:if test="${empty listaItems}">
        <p class="text-muted">Tu carrito está vacío. <a href="${pageContext.request.contextPath}/catalogo">Explora el catálogo</a>.</p>
    </c:if>

    <c:if test="${not empty listaItems}">
        <table class="table">
            <thead>
            <tr>
                <th>Taller</th>
                <th>Precio unitario</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${listaItems}">
                <tr>
                    <td><c:out value="${item.producto.nombre}"/></td>
                    <td>$<c:out value="${item.producto.precio}"/></td>
                    <td><c:out value="${item.cantidad}"/></td>
                    <td>$<c:out value="${item.subtotal}"/></td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/carrito/eliminar" class="mb-0">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <c:choose>
                                <c:when test="${not empty items}">
                                    <input type="hidden" name="idItem" value="${item.id}">
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" name="idProducto" value="${item.producto.id}">
                                </c:otherwise>
                            </c:choose>
                            <button type="submit" class="btn btn-sm btn-outline-danger">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="text-end">
            <h4>Total: $<c:out value="${total}"/></h4>
        </div>

        <c:if test="${not empty items}">
            <div class="text-end mt-3">
                <form method="post" action="${pageContext.request.contextPath}/carrito/confirmar">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <button type="submit" class="btn btn-success">Confirmar compra y pagar con Webpay</button>
                </form>
            </div>
        </c:if>

        <c:if test="${empty items}">
            <p class="text-muted mt-3">
                <i>Estás viendo un carrito temporal. <a href="${pageContext.request.contextPath}/login">Inicia sesión</a> o
                <a href="${pageContext.request.contextPath}/register">regístrate</a> para guardarlo en tu cuenta.</i>
            </p>
        </c:if>
    </c:if>
</div>
</body>
</html>