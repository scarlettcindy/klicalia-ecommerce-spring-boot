<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Klicalia | Resultado del pago</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5" style="max-width: 560px;">

    <c:if test="${cancelado == true}">
        <div class="alert alert-warning">Cancelaste el pago en Webpay. Tu carrito sigue guardado, puedes intentar de nuevo cuando quieras.</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <c:if test="${not empty orden}">
        <c:choose>
            <c:when test="${exitoso}">
                <div class="alert alert-success">
                    <h4 class="alert-heading">¡Pago exitoso!</h4>
                    <p>Tu orden #<c:out value="${orden.id}"/> quedó pagada correctamente.</p>
                    <p class="mb-0">Total pagado: $<c:out value="${orden.total}"/></p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger">
                    <h4 class="alert-heading">El pago no pudo completarse</h4>
                    <p>Tu orden #<c:out value="${orden.id}"/> quedó marcada como fallida. No se realizó ningún cobro.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </c:if>

    <a href="${pageContext.request.contextPath}/catalogo" class="btn btn-outline-secondary">Volver al catálogo</a>
</div>
</body>
</html>