<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8">
      <title>Foros</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
      <link rel="stylesheet" href="../css/style.css">
      <link rel="stylesheet" href="../css/listadosAdmin.css">
    </head>

    <body>

      <jsp:include page="cabecera.jsp"></jsp:include>
      <div class="row text-left">
        <div class="col-4">
          <a class="btn btn-outline-secondary m-1" href="registrarForo">Nuevo Foro</a><br>
        </div>
      </div>


      <div class="row">
        <div class="col-8 mx-auto">
          <form action="listarForos">
            <div class="input-group mb-3 mx-auto">
              <span class="input-group-text" id="basic-addon1">Nombre:</span>
              <input type="text" name="nombre" value="${nombre}" class="form-control" placeholder="Nombre"
                aria-label="Nombre" aria-describedby="basic-addon1" />
              <input type="submit" value="BUSCAR" class="btn btn-primary" />
            </div>
          </form>
        </div>
      </div>


      <div class="row d-flex justify-content-start m-1 d-flex d-grid gap-2" id="paginacion">
        <div class="col-2">
          <c:if test="${ anterior >= 0 }">
            <a href="listarForos?comienzo=${anterior}&foro=${nombre}"
              class="btn btn-outline-primary btn-sm">anterior</a>
          </c:if>
          <c:if test="${ anterior < 0 }">
            <a href="listarForos?comienzo=${anterior}&foro=${nombre}"
              class="btn btn-outline-primary disabled">anterior</a>
          </c:if>
        </div>
        <div class="col-2">
          <c:if test="${siguiente < total}">
            <a href="listarForos?comienzo=${siguiente}&foro=${nombre}"
              class="btn btn-outline-primary btn-sm">siguiente</a>
          </c:if>
          <c:if test="${ siguiente > total}">
            <a href="listarForos?comienzo=${siguiente}&foro=${nombre}"
              class="btn btn-outline-primary disabled">siguiente</a>
          </c:if>
        </div>

      </div>
      </div>
      <div class="row d-flex justify-content-start m-1 ">
        <div class="col-4 d-flex justify-content-start align-items-center">
          <h4>Total de foros: </h4><span class="badge rounded-pill bg-primary  ">${total}</span>
        </div>

      </div>



      <c:forEach var="foro" items="${info}">
        <div class="row d-flex justify-content-center m-3">
          <div class="col-lg-6 co-md-6 col-sm-6 ">
            <div class="card">
              <img class="fotoForo mx-auto my-3" src="../subidas/${foro.id}.jpg" />
              <div class="card-body">
                <h3 class="card-title">${foro.nombre}</h3>
              </div>

              <ul class="list-group list-group-flush ">
                <li class="list-group-item">descripcion: ${foro.descripcion} </li>
                <li class="list-group-item">fecha: ${foro.fechaCreacion} </li>
                <li class="list-group-item">id: ${foro.id}<br></li>
              </ul>

              <div class="card-body">
                <a href="editarForo?id=${foro.id}" class="btn btn-outline-primary">Editar</a>
                <a href="borrarForo?id=${foro.id}" class="btn btn-outline-danger" onclick="">Borrar</a>
              </div>

            </div>
          </div>
        </div>
        </div>
        </div>
      </c:forEach>

      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
    </body>

    </html>