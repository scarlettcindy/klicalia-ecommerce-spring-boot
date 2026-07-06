<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Klicalia | Catálogo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="h4 mb-0">Catálogo de productos</h1>
        <form method="post" action="${pageContext.request.contextPath}/logout" class="mb-0">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit" class="btn btn-outline-secondary btn-sm">Salir</button>
        </form>
    </div>
     <form method="get" action="${pageContext.request.contextPath}/catalogo" class="row g-2 mb-4">
        <div class="col-auto">
        <div class="col-auto">
    <input type="text" name="texto" class="form-control" placeholder="Buscar por nombre..." value="${textoBuscado}">
</div>
            <select name="idCategoria" class="form-select">
                <option value="">Todas las categorías</option>
                <c:forEach var="cat" items="${categorias}">
                    <option value="${cat.id}" ${cat.id == idCategoriaSeleccionada ? 'selected' : ''}>
                        <c:out value="${cat.nombre}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Buscar</button>
        </div>
    </form>

    <table class="table">
        <thead>
        <tr>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Precio</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${productos}">
            <tr>
                <td><c:out value="${p.nombre}"/></td>
                <td><c:out value="${p.categoria.nombre}"/></td>
                <td>$<c:out value="${p.precio}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>