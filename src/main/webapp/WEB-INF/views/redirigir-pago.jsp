<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Redirigiendo a Webpay...</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-5 text-center">
    <h1 class="h4 mb-4">Estás a punto de pagar con Webpay</h1>
    <p class="text-muted mb-4">Haz clic en el botón para continuar a la pasarela de pago de Transbank (ambiente de pruebas).</p>
    <form action="${url}" method="POST">
        <input type="hidden" name="token_ws" value="${token}">
        <button type="submit" class="btn btn-primary btn-lg">Pagar con Webpay</button>
    </form>
</div>
</body>
</html>