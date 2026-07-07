<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Klicalia | Catálogo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/hero.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-klicalia">
  <div class="container-fluid px-4">
    <a class="navbar-brand logo-brand" href="${pageContext.request.contextPath}/catalogo">
      <img src="${pageContext.request.contextPath}/Logo-completo.png" alt="Klicalia">
    </a>

    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarKlicalia" aria-controls="navbarKlicalia" aria-expanded="false" aria-label="Abrir menú">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarKlicalia">
      <div class="navbar-desktop-layout w-100">

        <ul class="navbar-nav menu-centro mb-3 mb-lg-0">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              Categorías
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/catalogo">Todas</a></li>
              <c:forEach var="cat" items="${categorias}">
                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/catalogo?idCategoria=${cat.id}"><c:out value="${cat.nombre}"/></a></li>
              </c:forEach>
            </ul>
          </li>
        </ul>

        <div class="acciones-navbar ms-auto">
          <form class="d-flex buscador-klicalia" role="search" method="get" action="${pageContext.request.contextPath}/catalogo">
            <input class="form-control me-2" type="search" name="texto" placeholder="Buscar por nombre..." value="${textoBuscado}">
            <button class="btn btn-outline-success" type="submit">Buscar</button>
          </form>

          <a href="${pageContext.request.contextPath}/carrito" class="btn-login">🛒 Carrito <c:if test="${cantidadCarrito > 0}">(${cantidadCarrito})</c:if></a>

          <form method="post" action="${pageContext.request.contextPath}/logout" class="mb-0">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button type="submit" class="btn-login">Salir</button>
          </form>
        </div>

      </div>
    </div>
  </div>
</nav>

<section class="talleres-klicalia py-5">
  <div class="container">

    <div class="text-center seccion-encabezado mb-5">
      <span class="seccion-etiqueta">CATÁLOGO</span>
      <h2>
        <c:choose>
          <c:when test="${not empty idCategoriaSeleccionada}">Talleres en esta categoría</c:when>
          <c:otherwise>Explora nuestros talleres</c:otherwise>
        </c:choose>
      </h2>
      <p>Estos son los talleres cargados de verdad en la base de datos de Klicalia.</p>
    </div>

    <c:if test="${empty productos}">
      <p class="text-center text-muted">No se encontraron talleres con ese criterio de búsqueda.</p>
    </c:if>

    <div class="row g-4">
      <c:forEach var="p" items="${productos}" varStatus="i">
        <c:choose>
          <c:when test="${i.index % 4 == 0}"><c:set var="colorClase" value="lila" /></c:when>
          <c:when test="${i.index % 4 == 1}"><c:set var="colorClase" value="verde" /></c:when>
          <c:when test="${i.index % 4 == 2}"><c:set var="colorClase" value="celeste" /></c:when>
          <c:otherwise><c:set var="colorClase" value="amarillo" /></c:otherwise>
        </c:choose>

        <c:choose>
          <c:when test="${p.categoria.nombre == 'Lectoescritura'}"><c:set var="imagenTaller" value="arrangement-different-letters-speech-therapy-sessions.jpg" /></c:when>
          <c:when test="${p.categoria.nombre == 'Matematicas'}"><c:set var="imagenTaller" value="matematicas-taller.png" /></c:when>
          <c:when test="${p.categoria.nombre == 'Arte'}"><c:set var="imagenTaller" value="close-up-mom-helping-son-paint-eggs.jpg" /></c:when>
          <c:when test="${p.categoria.nombre == 'Idiomas'}"><c:set var="imagenTaller" value="ingles-taller.png" /></c:when>
          <c:otherwise><c:set var="imagenTaller" value="Logo-completo.png" /></c:otherwise>
        </c:choose>

        <div class="col-lg-3 col-md-6">
          <div class="card taller-card h-100">
            <img src="${pageContext.request.contextPath}/${imagenTaller}" class="card-img-top" alt="${p.nombre}">
            <div class="card-body">
              <span class="badge-taller badge-${colorClase}"><c:out value="${p.categoria.nombre}"/></span>
              <h5 class="card-title"><c:out value="${p.nombre}"/></h5>
              <p class="card-text"><c:out value="${p.descripcion}"/></p>
              <div class="precio-box">
                <span class="precio-actual">$<c:out value="${p.precio}"/></span>
              </div>
            </div>
            <div class="card-footer taller-footer">
              <form method="post" action="${pageContext.request.contextPath}/carrito/agregar" class="mb-0">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="hidden" name="idProducto" value="${p.id}">
                <button type="submit" class="btn-agregar-carrito">Agregar al carrito</button>
              </form>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>

  </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>