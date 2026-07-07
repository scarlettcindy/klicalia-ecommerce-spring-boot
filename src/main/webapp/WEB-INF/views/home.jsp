<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Klicalia | Recursos educativos para niños</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/hero.css">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-klicalia">
  <div class="container-fluid px-4">
    <a class="navbar-brand logo-brand" href="${pageContext.request.contextPath}/">
      <img src="${pageContext.request.contextPath}/Logo-completo.png" alt="Klicalia">
    </a>
    <div class="acciones-navbar ms-auto">
      <a href="${pageContext.request.contextPath}/carrito" class="btn-login">🛒 Carrito <c:if test="${cantidadCarrito > 0}">(${cantidadCarrito})</c:if></a>
      <a href="${pageContext.request.contextPath}/login" class="btn-login">Iniciar sesión</a>
      <a href="${pageContext.request.contextPath}/register" class="btn-registro">Registrarse</a>
    </div>
  </div>
</nav>

<section class="hero-klicalia">
  <div class="container">
    <div class="row align-items-center g-5">
      <div class="col-lg-6">
        <div class="hero-contenido">
          <span class="hero-badge">Aprender a un klic</span>
          <h1>Recursos educativos digitales para aprender y crear</h1>
          <p>Descubre talleres y materiales pensados para niños y adolescentes, en una plataforma visual y fácil de usar.</p>
          <div class="hero-acciones">
            <a href="${pageContext.request.contextPath}/catalogo" class="btn hero-btn-principal">Explorar catálogo</a>
            <a href="${pageContext.request.contextPath}/login" class="btn hero-btn-secundario">Iniciar sesión</a>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="hero-imagen-box">
          <img src="${pageContext.request.contextPath}/medium-shot-kid-taking-notes.jpg" alt="Klicalia recursos educativos" class="img-fluid hero-imagen">
        </div>
      </div>
    </div>
  </div>
</section>

<section class="talleres-klicalia py-5">
  <div class="container">
    <div class="text-center seccion-encabezado mb-5">
      <span class="seccion-etiqueta">TALLERES</span>
      <h2>Explora nuestros talleres y recursos</h2>
      <p>Descubre materiales, talleres y actividades diseñados para apoyar distintas etapas del aprendizaje.</p>
    </div>

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

<section class="planes-klicalia py-5">
  <div class="container">
    <div class="text-center seccion-encabezado mb-5">
      <h2>Elige tu plan</h2>
      <p>Accede a recursos, packs y materiales educativos según tus necesidades. Encuentra la opción ideal para aprender, enseñar y crear.</p>
    </div>

    <div class="row g-4 justify-content-center">
      <div class="col-lg-4 col-md-6">
        <div class="plan-card h-100">
          <div class="plan-header">
            <h3>$0</h3>
            <span>Plan gratuito</span>
          </div>
          <div class="plan-body">
            <ul class="plan-lista">
              <li>Acceso a recursos básicos</li>
              <li>Material gratuito seleccionado</li>
              <li>Descargas limitadas</li>
              <li>Exploración de categorías</li>
              <li>Soporte básico</li>
            </ul>
          </div>
          <div class="plan-footer">
            <a href="${pageContext.request.contextPath}/register" class="btn-plan">Comenzar</a>
          </div>
        </div>
      </div>

      <div class="col-lg-4 col-md-6">
        <div class="plan-card plan-destacado h-100">
          <div class="plan-header">
            <h3>$5.990</h3>
            <span>Plan premium</span>
          </div>
          <div class="plan-body">
            <ul class="plan-lista">
              <li>Acceso a más recursos</li>
              <li>Packs exclusivos</li>
              <li>Descargas ampliadas</li>
              <li>Contenido actualizado</li>
              <li>Soporte preferente</li>
            </ul>
          </div>
          <div class="plan-footer">
            <a href="${pageContext.request.contextPath}/register" class="btn-plan">Elegir plan</a>
          </div>
        </div>
      </div>

      <div class="col-lg-4 col-md-6">
        <div class="plan-card h-100">
          <div class="plan-header">
            <h3>$9.990</h3>
            <span>Plan Pro</span>
          </div>
          <div class="plan-body">
            <ul class="plan-lista">
              <li>Acceso completo a recursos</li>
              <li>Packs premium ilimitados</li>
              <li>Materiales exclusivos</li>
              <li>Clases particulares</li>
              <li>Soporte avanzado</li>
            </ul>
          </div>
          <div class="plan-footer">
            <a href="${pageContext.request.contextPath}/register" class="btn-plan">Elegir plan</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<section class="articulos-klicalia py-5">
  <div class="container">
    <div class="text-center seccion-encabezado mb-5">
      <span class="seccion-etiqueta">Y ALGO MÁS...</span>
      <h2>¿Por qué elegir Klicalia?</h2>
      <p>Descubre cómo nuestros recursos y materiales pueden apoyar el aprendizaje de forma más clara, práctica y creativa.</p>
    </div>

    <div class="row g-4">
      <div class="col-lg-4 col-md-6">
        <article class="articulo-card h-100">
          <h3>Materiales listos para usar</h3>
          <p>Klicalia reúne actividades, fichas y recursos digitales pensados para facilitar el trabajo de familias y estudiantes.</p>
        </article>
      </div>
      <div class="col-lg-4 col-md-6">
        <article class="articulo-card h-100">
          <h3>Diseño visual y accesible</h3>
          <p>La plataforma busca ofrecer una experiencia ordenada, cercana y moderna, para que encontrar recursos sea simple e intuitivo.</p>
        </article>
      </div>
      <div class="col-lg-4 col-md-6">
        <article class="articulo-card h-100">
          <h3>Apoyo a distintas etapas</h3>
          <p>Desde preescolar hasta niveles más avanzados, Klicalia organiza sus contenidos para responder a distintas necesidades educativas.</p>
        </article>
      </div>
    </div>
  </div>
</section>

<footer class="footer-klicalia">
  <div class="container">
    <div class="row gy-4">
      <div class="col-lg-4 col-md-6">
        <h4>Klicalia</h4>
        <p>Recursos educativos digitales para aprender y crear en un entorno moderno, visual y accesible.</p>
      </div>
      <div class="col-lg-4 col-md-6">
        <h5>Redes</h5>
        <ul class="footer-lista">
          <li><a href="#">Instagram</a></li>
          <li><a href="#">Facebook</a></li>
          <li><a href="#">Pinterest</a></li>
        </ul>
      </div>
      <div class="col-lg-4 col-md-12">
        <h5>Contacto</h5>
        <p class="footer-correo"><a href="mailto:contacto@klicalia.com">contacto@klicalia.com</a></p>
      </div>
    </div>
    <div class="footer-copy">
      <p>© 2026 Klicalia. Todos los derechos reservados.</p>
    </div>
  </div>
</footer>

<button id="btn-arriba" class="btn-scroll-top">↑</button>

<script src="${pageContext.request.contextPath}/scroll.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>