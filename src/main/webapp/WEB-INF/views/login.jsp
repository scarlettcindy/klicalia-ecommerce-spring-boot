<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Klicalia | Iniciar sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-klicalia">
  <div class="container-fluid px-4">
    <a class="navbar-brand logo-brand" href="${pageContext.request.contextPath}/">
      <img src="${pageContext.request.contextPath}/Logo-completo.png" alt="Klicalia">
    </a>
  </div>
</nav>
<div class="container py-4" style="max-width: 480px;">
    <h1 class="h4 mb-4">Iniciar sesión</h1>

    <c:if test="${param.registrado != null}">
        <div class="alert alert-success">Cuenta creada con éxito. Ya puedes iniciar sesión.</div>
    </c:if>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger">Email o contraseña incorrectos.</div>
    </c:if>

    <div class="card-panel">
        <form method="post" action="${pageContext.request.contextPath}/login">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="text" name="username" class="form-control" required autofocus>
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Ingresar</button>
        </form>

        <p class="text-center mt-3">
            ¿No tienes cuenta? <a href="${pageContext.request.contextPath}/register">Regístrate</a>
        </p>
    </div>
</div>
</body>
</html>