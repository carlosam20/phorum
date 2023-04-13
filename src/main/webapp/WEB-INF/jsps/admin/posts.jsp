<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
    <!DOCTYPE html>
    <html>

    <head>
      <meta charset="UTF-8">
      <title>Post</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
      <link rel="stylesheet" href="../css/style.css">
      <link rel="stylesheet" href="../css/listadosAdmin.css">
    </head>

    <body>

      <jsp:include page="cabecera.jsp"></jsp:include>
      <a class="btn btn-outline-secondary m-3" href="registrarPost">Nuevo post</a><br>


      <form action="listarPosts">

        <div class="input-group mb-3">
          <span class="input-group-text" id="basic-addon1">Marca:</span>
          <!--  <input type="text"  placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">-->
          <input type="text" name="nombre" value="${nombre}" class="form-control" placeholder="Nombre Post"
            aria-label="Post" aria-describedby="basic-addon1" />
          <input type="submit" value="BUSCAR" class="btn btn-primary" />
        </div>

      </form>



      <div class="mx-auto">
        paginacion: <br>
        total de post: ${total} <br>
        <c:if test="${ anterior >= 0 }">
          <a href="listarPost?comienzo=${anterior}&nombre=${nombre}" class="btn btn-outline-primary">anterior</a>
        </c:if>


        <c:if test="${siguiente < total}">
          <a href="listarPost?comienzo=${siguiente}&nombre=${nombre}" class="btn btn-outline-primary">siguiente</a>
        </c:if>

      </div>




      <c:forEach var="post" items="${info}">
        <div class="row d-flex justify-content-center m-3">
          <div class="col-lg-6 co-md-6 col-sm-6 ">
            <div class="card">
              <img class="fotoForo mx-auto my-3" src="../subidasPost/${post.id}.jpg" /><br>
              <div class="card-body">
                <h5 class="card-title">${post.nombre} ${post.fechaCreacion }</h5>
              </div>

              <ul class="list-group list-group-flush">
                <li class="list-group-item">descripcion: ${post.descripcion} </li>
                <li class="list-group-item">nombre foro: ${post.foro.nombre}</li>
                <li class="list-group-item">foro id: ${post.foro.id}</li>
              </ul>

              <div class="card-body">
                <a href="editarPost?id=${post.id}" class="btn btn-outline-primary">Editar</a>
                <a href="borrarPost?id=${post.id}" class="btn btn-outline-danger">Borrar</a>
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