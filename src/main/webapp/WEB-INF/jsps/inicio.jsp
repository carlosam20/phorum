<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="ISO-8859-1">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://unpkg.com/@stackoverflow/stacks/dist/css/stacks.min.css">
            <link rel="icon" type="image/svg" href="images/logo-phorum.svg">
            <link rel="stylesheet"
                href="https://fonts.sandbox.google.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="css/style.css">
        </head>


        <body>

            <!--Navbar -->
            <div class="barraNavegacion">
                <nav class="navbar navbar-expand-lg navbar-dark">
                    <div class="container-fluid">

                        <div class="navbar-brand" href="#">
                            <img src=<c:url value="images/logo-phorum.svg"></c:url> alt="Foto" width="60" height="60"
                            class="d-inline-block align-text-top"/>
                        </div>

                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02"
                            aria-expanded="false" aria-label="Toggle navigation">
                            <span class="material-symbols-outlined">menu</span>
                        </button>

                        <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                            <ul class="navbar-nav me-auto mb-3 mb-lg-0">
                                <li class="nav-item">
                                    <a class="nav-link m-3" id="enlace_home" aria-current="page" 
                                    href="#">Home</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link m-3" id="enlace_perfil" aria-current="page" 
                                    href="#">Perfil</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link m-3" id="enlace_listado_foros" aria-current="page"
                                        href="#">Foros</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link m-3" id="enlace_listado_posts" aria-current="page"
                                        href="#">Posts</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link m-3" id="enlace_logout" aria-current="page" href="#">Cerrar
                                        Sesi&oacuten</a>
                                </li>

                            </ul>
                        </div>


                        <ul class="navbar-nav mb-auto mb-lg-0">
                            <li class="nav-item">
                                <button class="btn btn-outline-primary m-3" id="enlace_identificarme"
                                    aria-current="page" href="#">Iniciar Sesi&oacuten</button>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link m-3" id="enlace_registrarme" aria-current="page"
                                    href="#">Registrar</a>
                            </li>
                            <li class="nav-item">
                                <p id="mensaje_login" class="nav-link m-3" aria-current="page"></p>
                            </li>
                        </ul>

                    </div>
                </nav>
            </div>



            <div class="" id="contenedor"></div>

            <!-- Contenedor donde cargan las vistas -->

            <div class="d-flex justify-content-center spinner-grow text-primary col-11" role="status" id="#loadingDiv">
                <span class="sr-only">Loading...</span>
            </div>

            <!-- Foooter -->
            <div class="footer-menu">

                <div class="row footer-bg">
                    <div class="row m-1">
                        <div class="col-lg-10 col-md-10 col-sm-10 justify-content-left m-1">
                            <div><img class="logo-footer" src="images/logo-phorum.svg" alt="logo-phorum" srcset="">
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-3 col-sm-3  footer-enlaces">

                        <ul class="list-group list-footer">
                            <li class="list-group-item list-footer-item"><a class="text-light" id="enlace_home">Home</a>
                            </li>
                            <li class="list-group-item list-footer-item"><a class="text-light"
                                    id="enlace_perfil">Perfil</a></li>
                            <li class="list-group-item list-footer-item"><a class="text-light"
                                    id="enlace_listado_posts">Posts</a></li>
                            <li class="list-group-item list-footer-item"><a class="text-light"
                                    id="enlace_listado_foros">Foros</a></li>
                            <li class="list-group-item list-footer-item"><a class="text-light" id="enlace_logout">Cerrar
                                    Sesi&oacuten</a></li>
                        </ul>

                    </div>

                    <div class="col-lg-3 col-sm-3  footer-enlaces">
                        <ul class="list-group m-3">
                            <li class="list-group-item list-footer-item"><a class="text-light"
                                    id="enlace_identificarme">Iniciar Sesi&oacuten</a></li>
                            <li class="list-group-item list-footer-item"><a class="text-light"
                                    id="enlace_registrarme">Registrarse</a></li>
                        </ul>
                    </div>

                    <div class="col-lg-4 col-sm-4  footer-imagen justify-content-center">
                        <img class="imagen-footer" src="images/footer-team.png" alt="footer equipo" />
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
            <script type="text/javascript" src="js/carga_plantilla.js"></script>
            <script src="https://unpkg.com/sweetalert@2.1.2/dist/sweetalert.min.js"></script>
            <script type="text/javascript" src="js/validaciones.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
            <script type="text/javascript" src="js/main.js"></script>
            <script src="https://kit.fontawesome.com/485aa9f350.js" crossorigin="anonymous"></script>

        </body>

        </html>