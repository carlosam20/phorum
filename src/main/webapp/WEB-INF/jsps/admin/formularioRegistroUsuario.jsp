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
                    <h2>Registro de usuario</h2>
                </div>
            </div>

            <div class="registroUsuario">
                <div class="row m-1 text-center d-flex justify-content-center align-items-center">
                    <div class="col-lg-10 col-md-10 col-sm-10">
                        <springform:form modelAttribute="nuevoUsuario" action="guardarNuevoUsuario"
                            enctype="multipart/form-data">

                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-input-text"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10">
                                    <springform:input type="text" path="nombre" placeholder="Nombre"
                                        class="form form-lg form-control" pattern="^.{1,60}$" />
                                </div>
                            </div>


                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-input-text"></i>
                                </div>
                                <div class="col-sm-8 col-md-8 col-lg-8">
                                    <springform:input type="password" path="pass" class="form form-lg form-control"
                                        pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,60}$"
                                        maxlength="60" minlength="8" placeholder="Contraseña" id="pass" />
                                </div>
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-solid fa-eye" id="ojo-icon"></i>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-envelope fa-2xl"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10">
                                    <springform:input id="email" type="email" path="email"
                                        pattern="^[a-zA-Z]+@[a-zA-Z]+\\.[a-zA-Z]{3,254}+$" maxlenght="254" minlength="3"
                                        class="form form-lg form-control" placeholder="E-mail" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-calendar-days fa-2xl"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10">
                                    <springform:input id="fecha" type="date" pattern="" path="fechaCreacion"
                                        class="form form-lg form-control" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-text fa-2xl"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10">
                                    <springform:textarea id="descripcion" path="descripcion"
                                        class="form form-lg form-control" pattern="^.{1,300}$" maxlenght="300"
                                        minlenght="1" placeholder="Descripcion" />
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                    <i class="fa-regular fa-image fa-2xl"></i>
                                </div>
                                <div class="col-sm-10 col-md-10 col-lg-10">
                                    <springform:input id="imagen" path="imagen" type="file"
                                        class="form form-lg form-control" pattern="/^.+\.(png|jpg)$/" />
                                </div>
                            </div>

                            <springform:hidden path="id" />

                            <div class="row mt-2">
                                <div class="col-sm-12 col-md-12 col-lg-12 text-center mx-auto">
                                    <input id="boton_submit" class="btn btn-primary btn-lg" type="submit"
                                        value="Registrar Usuario">
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

        </html>