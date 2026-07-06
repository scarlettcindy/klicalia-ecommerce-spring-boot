<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Klicalia | Crear cuenta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.css">
</head>
<body>

<div class="container py-5" style="max-width: 480px;">

    <h1 class="h4 mb-4">Crear cuenta</h1>

    <c:if test="${not empty errores}">
        <div class="alert alert-danger"><c:out value="${errores}"/></div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/register">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <div class="mb-3">
            <label class="form-label">Nombre</label>
            <input type="text" name="nombre" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Apellido</label>
            <input type="text" name="apellido" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Contraseña</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-klicalia w-100">Crear cuenta</button>

        <p class="mt-3 text-center">
            ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
        </p>
    </form>

</div>

</body>
</html>