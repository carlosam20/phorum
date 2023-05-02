<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>formularioRegistroUsuario</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="../css/style.css">
            <link rel="stylesheet" href="../css/formsAdmin.css">
        </head>

        <body>
            <div class="row text-center align-items-start">
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <h1>Registro de usuario</h1>
                </div>
            </div>

            <div class="editarUsuario my-3">
                <div class="row my-3 mx-2 text-center d-flex justify-content-center align-items-center">
                    <div class="col-lg-8 col-md-8 col-sm-8 my-3 mx-1">

                        <springform:form modelAttribute="usuario" action="guardarCambiosUsuario"
                            enctype="multipart/form-data" class="d-grid gap-4" id="formularioUsuario">

                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
                                    <i class="fa-regular fa-user fa-xl"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10 d-flex align-items-center">
                                    <springform:input type="text" path="nombre" placeholder="Nombre"
                                        class="form form-lg form-control" pattern="^.{1,60}$" />
                                </div>
                            </div>
                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="nombre" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-1 d-flex align-items-center">
                                    <i class="fa-solid fa-lock fa-xl"></i>
                                </div>
                                <div class="col-sm-9 col-md-9 col-lg-9 d-flex align-items-center">
                                    <springform:input type="password" path="pass" class="form form-lg form-control"
                                        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,60}$"
                                        maxlength="60" minlength="8" placeholder="Contraseña" id="pass" />
                                </div>
                                <div class="col-sm-2 col-md-2 col-lg-2 d-flex align-items-center">
                                    <i class="fa-solid fa-eye" id="ojo-icon"></i>
                                </div>
                            </div>
                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="pass" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
                                    <i class="fa-regular fa-envelope fa-xl"></i>
                                </div>
                                <div class="col-sm-11 col-md-11 col-lg-11 d-flex align-items-center">
                                    <springform:input id="email" type="email" path="email"
                                        pattern="^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{3,254}+$" maxlenght="254" minlength="3"
                                        class="form form-lg form-control" placeholder="E-mail" />
                                </div>
                            </div>
                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="email" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
                                    <i class="fa-regular fa-calendar-days fa-xl"></i>
                                </div>
                                <div class="col-sm-11 col-md-11 col-lg-11 d-flex align-items-center">
                                    <springform:input id="fecha" type="date" pattern="" path="fechaCreacion"
                                        class="form form-lg form-control" />
                                </div>
                            </div>
                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="fechaCreacion" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
                                    <i class="fa-regular fa-comment-dots fa-xl"></i>
                                </div>
                                <div class="col-sm-11 col-md-11 col-lg-11 d-flex align-items-center">
                                    <springform:textarea id="descripcion" path="descripcion"
                                        class="form form-lg form-control" pattern="^.{1,300}$" maxlenght="300"
                                        minlenght="1" placeholder="Descripcion" />
                                </div>
                            </div>
                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="descripcion" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-1 col-md-1 col-lg-1">
                                    <i class="fa-regular fa-image fa-xl"></i>
                                </div>
                                <div class="col-sm-11 col-md-11 col-lg-11">
                                    <springform:input id="imagen" path="imagen" type="file"
                                        class="form form-lg form-control" pattern="/^.+\.(png|jpg)$/" />
                                </div>
                            </div>

                            <div class="row  d-flex justify-content-start">
                                <div class="span-col col-10 d-flex justify-content-center">
                                    <i class="fa-solid fa-circle-exclamation m-1"></i>
                                    <springform:errors class="span-error text-start text-danger" path="imagen" />
                                </div>
                            </div>

                            <springform:hidden path="id" />

                            <div class="row mt-2 " id="botonForm">
                                <div class="col-sm-12 col-md-12 col-lg-12 text-center mx-auto">
                                    <input id="boton_submit" class="btn btn-primary btn-lg" type="submit"
                                        value="Editar Usuario">
                                </div>
                            </div>


                        </springform:form>
                    </div>
                </div>
            </div>

        </body>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/485aa9f350.js" crossorigin="anonymous"></script>
        <script src="../js/togglePass.js"></script>
        <script src="../js/spanAdmin.js"></script>


        </html>