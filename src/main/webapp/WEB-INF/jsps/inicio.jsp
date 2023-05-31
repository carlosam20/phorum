<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="ISO-8859-1">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link href="https://unpkg.com/@primer/css@^20.2.4/dist/primer.css" rel="stylesheet" />
                <link rel="stylesheet"
                    href="https://fonts.sandbox.google.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet" href="css/style.css">

                <!-- icons -->
                <script src="https://kit.fontawesome.com/485aa9f350.js" crossorigin="anonymous"></script>
            </head>


            <body>

                <!--Navbar -->
                <div class="barraNavegacion">
                    <nav class="navbar navbar-expand-lg navbar-dark">
                        <div class="container-fluid">

                            <div class="navbar-brand">
                                <img src="images/logo-phorum.svg" id="logo-nav">
                            </div>

                            <i class="fa-solid fa-bars fa-2xl collapsed" class=" navbar-toggler" type="button"
                                data-bs-toggle="collapse" data-bs-target="#navbarToggler" aria-controls="navbarToggler"
                                aria-expanded="false" aria-label="Toggle navigation" id="menuIcon"></i>

                            <div class="collapse navbar-collapse mx-auto" id="navbarToggler">
                                <ul class="navbar-nav d-flex justify-content-evenly">
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_home" aria-current="page">Home</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_perfil" aria-current="page">Perfil</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_listado_foros" aria-current="page">Foros</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_listado_posts" aria-current="page">Posts</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_logout" aria-current="page">Cerrar
                                            Sesi&oacuten</a>
                                    </li>
                                </ul>

                                <ul class="navbar-nav d-flex justify-content-center">
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_identificarme" aria-current="page">Iniciar
                                            Sesi&oacuten</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link m-2" id="enlace_registrarme"
                                            aria-current="page">Registrar</a>
                                    </li>
                                    <li class="nav-item d-flex align-items-center">
                                        <p class="text-light m-2" id="mensaje_login" aria-current="page"></p>
                                    </li>
                                </ul>

                            </div>




                        </div>
                    </nav>
                </div>


                <!-- Contenedor donde cargan las vistas -->
                <div class="" id="contenedor"></div>

                <!-- Foooter -->
                <div class="footer-menu">

                    <div class="row m-1">
                        <div class="col-lg-12 col-md-12 col-sm-12 justify-content-left m-1">
                            <img class="logo-footer" src="images/logo-phorum.svg" alt="logo-phorum" srcset="">
                        </div>
                    </div>

                    <div class="row m-1 footer-bg">

                        <div class="col-lg-12 col-md-12 col-sm-12 ">

                            <div class="row">
                                <div class="col-lg-6 col-md-6 col-sm-4 ">
                                    <ul class="list-group list-footer">
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_home_footer">Home</a>
                                        </li>
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_perfil_footer">Perfil</a></li>
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_listado_posts_footer">Posts</a></li>
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_listado_foros_footer">Foros</a></li>
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_logout_footer">Cerrar
                                                Sesi&oacuten</a></li>
                                    </ul>
                                </div>

                                <div class="col-lg-4 col-md-4 col-sm-0  footer-imagen justify-content-center">
                                    <img class="imagen-footer" src="images/footer-team.png" alt="footer equipo" />
                                </div>

                                <div class="col-lg-4 col-md-4 col-sm-4">
                                    <ul class="list-group list-footer">
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_identificarme_footer">Iniciar Sesi&oacuten</a></li>
                                        <li class="list-group-item list-footer-item"><a class="text-light"
                                                id="enlace_registrarme_footer">Registrarse</a></li>
                                        <li class="list-group-item list-footer-item">
                                            <p class="text-light">
                                                @phorum
                                            </p>
                                        </li>
                                    </ul>
                                </div>

                            </div>
                        </div>





                    </div>

                </div>


                <!-- JavaScript -->

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                    crossorigin="anonymous"></script>
                <script type="text/javascript" src="librerias_javascript/jquery.js"></script>
                <script type="text/javascript" src="librerias_javascript/mustache.js"></script>
                <script type="text/javascript" src="librerias_javascript/js.cookie.min.js"></script>
                <script src="https://unpkg.com/sweetalert@2.1.2/dist/sweetalert.min.js"></script>
                <script type="text/javascript" src="js/validaciones.js"></script>
                <script type="text/javascript" src="js/navbarIconoMobile.js"></script>
                <script type="text/javascript" src="js/cargarPlantillas.js"></script>
                <script type="text/javascript" src="js/main.js"></script>
            </body>

            </html>