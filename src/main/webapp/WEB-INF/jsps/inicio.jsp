<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://unpkg.com/@stackoverflow/stacks/dist/css/stacks.min.css">
<link rel="stylesheet" href="https://fonts.sandbox.google.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="icon" type="image/svg" href="images/logo-phorum.svg">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">

</head>
<body>


<!--Navbar -->
<div class="barraNavegacion">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
        
            <a class="navbar-brand" href="#">
            	<img src=<c:url value="images/logo-phorum.svg"></c:url> alt="Foto" width="60" height="60" class="d-inline-block align-text-top"/>
            </a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                <span class="material-symbols-outlined">menu</span>
            </button>

            <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul class="navbar-nav me-auto mb-3 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_home" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_perfil" aria-current="page" href="#">Perfil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_listado_foros" aria-current="page" href="#">Foros</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_listado_posts" aria-current="page" href="#">Posts</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_logout" aria-current="page" href="#">Cerrar Sesi&oacuten</a>
                    </li>
                                        
                </ul>
            </div>


             <ul class="navbar-nav mb-auto mb-lg-0">
                 <li class="nav-item">
                     <a class="btn btn-outline-primary m-3 bg-light" id="enlace_identificarme" aria-current="page" href="#">Iniciar Sesi&oacuten</a>
                 </li>
                 
                 <li class="nav-item">
                     <a class="nav-link m-3" id="enlace_registrarme" aria-current="page" href="#">Registrar</a>
                 </li>
                 <li class="nav-item">
                        <p  id="mensaje_login" class="nav-link m-3" aria-current="page"></p>
                        <!-- <p  id="id_login" class="nav-link m-3"aria-current="page"></p>  -->
                  </li>
             </ul>

        </div>
    </nav>
</div>


<!-- Contenedor donde cargan las vistas -->
<div id="contenedor"></div>


<!-- Foooter -->
  

    <div class="text-bg-secondary footer-menu ">
        
        <div class="row">
        
       <div class=" col-lg-4 col-sm-4   footer-enlaces">
       
        <img class="logo-footer" src="images/logo-phorum.svg" alt="logo-phorum" srcset="">
            <a class="text-light">Home</a>
            <a class="text-light">Perfil</a>
            <a class="text-light">Posts</a>
            <a class="text-light">Foros</a>
            <a class="text-light">Cerrar Sesión</a>
        </div>
        
        <div class=" col-lg-4 col-sm-4  footer-enlaces">
            <a class="text-light">Iniciar Sesión</a>
            <a class="text-light">Registrarse</a>
            
        </div>

        <div class="col-lg-4 col-sm-4  footer-imagen">
            <img class="imagen-footer"src="images/footer-team.png" alt="footer equipo"/>
        </div>

    	</div>
        
</div>
    
<div class="row m-3">
    <div class=" col-lg-12 col-sm-12 footer-copy text-center">
        <h2 class="text-light">@phorum</h2>
    </div>


</div>


<!-- JavaScript -->      

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script type="text/javascript" src="librerias_javascript/jquery.js"></script>
<script type="text/javascript" src="librerias_javascript/mustache.js"></script>
<script type="text/javascript" src="librerias_javascript/js.cookie.min.js"></script>
<script type="text/javascript" src="js/carga_plantilla.js"></script>
<script src="https://unpkg.com/@stackoverflow/stacks/dist/js/stacks.min.js"></script>
<script src="https://unpkg.com/sweetalert@2.1.2/dist/sweetalert.min.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/main.js"></script>

</body>
</html>