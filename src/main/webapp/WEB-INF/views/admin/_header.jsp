<nav class="navbar navbar-admin">
  <div class="container-fluid px-4 d-flex justify-content-between align-items-center">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/products">
      <img src="${pageContext.request.contextPath}/Logo-completo.png" alt="Klicalia">
    </a>
    <div class="d-flex align-items-center gap-3">
      <span class="badge-admin">Panel de administración</span>
      <a href="${pageContext.request.contextPath}/catalogo" class="small">Ver catálogo</a>
      <form method="post" action="${pageContext.request.contextPath}/logout" class="d-inline mb-0">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit" class="btn btn-outline-secondary btn-sm">Salir</button>
      </form>
    </div>
  </div>
</nav>