<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <title><spring:message code="regweb.titulo"/></title>
    <c:import url="../modulos/imports.jsp"/>
</head>

<body>

<c:import url="../modulos/menu.jsp"/>

<div class="row-fluid container main">

    <div class="well well-white">

        <!-- Miga de pan -->
        <div class="row">
            <div class="col-xs-12">
                <ol class="breadcrumb">
                    <li><a href="<c:url value="/inici"/>" ><i class="fa fa-power-off"></i> <spring:message code="regweb.inicio"/></a></li>
                    <li><a href="<c:url value="/organismo/list"/>" ><i class="fa fa-globe"></i> ${entidad.nombre}</a></li>
                    <li><a href="<c:url value="/entidad/usuarios"/>" ><i class="fa fa-list-ul"></i> <spring:message code="organismo.usuarios"/></a></li>
                    <li class="active"><i class="fa fa-pencil-square-o"></i><strong><spring:message code="usuario.modificar.permisos"/> a ${entidad.nombre}</strong></li>
                </ol>
            </div>
        </div><!-- Fin miga de pan -->


        <div class="row">
            <div class="col-xs-12">

                <div class="panel panel-success">

                    <div class="panel-heading">
                        <h3 class="panel-title"><i class="fa fa-pencil-square-o"></i>
                            <strong>
                                <c:if test="${permisoLibroUsuarioForm.usuarioEntidad != null}">
                                    <spring:message code="usuario.modificar.permisos"/> ${permisoLibroUsuarioForm.usuarioEntidad.usuario.nombreCompleto}
                                </c:if>
                                <c:if test="${permisoLibroUsuarioForm.usuarioEntidad == null}">
                                    <spring:message code="usuario.asignar.permisos"/>
                                </c:if>
                            </strong>
                        </h3>
                    </div>

                    <!-- Formulario -->
                    <div class="panel-body">

                        <form:form modelAttribute="permisoLibroUsuarioForm" method="post" cssClass="form-horizontal">
                            <form:hidden path="usuarioEntidad.id"/>
                            <div class="form-group col-xs-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                            <tr>
                                                <th style="text-align:center;"><spring:message code="libro.libros"/></th>
                                                <c:forEach var="permiso" items="${permisos}" varStatus="status">
                                                    <th style="text-align:center;" onclick="seleccionarTodo('${status.index}','${fn:length(permisoLibroUsuarioForm.permisoLibroUsuarios)}');"><spring:message code="permiso.nombre.${permiso}" /></th>
                                                </c:forEach>
                                            </tr>
                                        </thead>
                                        <tbody>

                                            <c:if test="${empty libros}">
                                                <tr>
                                                    <td colspan="8"><spring:message code="permisos.libro.ninguno"/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${not empty libros}">
                                                <c:forEach var="libro" items="${libros}">
                                                    <tr>
                                                        <td>${libro.nombreCompleto}</td>
                                                        <c:forEach var="plus" items="${permisoLibroUsuarioForm.permisoLibroUsuarios}" varStatus="status">
                                                            <c:if test="${libro.id == plus.libro.id}">
                                                                <form:hidden path="permisoLibroUsuarios[${status.index}].id"/>
                                                                <form:hidden path="permisoLibroUsuarios[${status.index}].libro.id"/>
                                                                <form:hidden path="permisoLibroUsuarios[${status.index}].permiso"/>
                                                                <td style="text-align:center;"><form:checkbox path="permisoLibroUsuarios[${status.index}].activo"/></td>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tr>
                                                </c:forEach>
                                            </c:if>
                                        </tbody>

                                    </table>
                                </div>

                            </div>


                    </div>


                </div>
                    <!-- Botonera -->
                    <c:if test="${not empty libros}">
                        <input type="submit" value="<spring:message code="regweb.guardar"/>" onclick="" class="btn btn-warning btn-sm"/>
                    </c:if>
                    <input type="button" value="<spring:message code="regweb.cancelar"/>" onclick="goTo('<c:url value="/entidad/usuarios"/>')" class="btn btn-sm">
                    <!-- Fin Botonera -->
                </form:form>
            </div>
        </div>

    </div>
</div> <!-- /container -->

<c:import url="../modulos/pie.jsp"/>

<script type="text/javascript">

    function seleccionarTodo(columna, filas){
        columna = parseInt(columna);
        filas = filas / 7;
        var len = parseInt(filas);

        for ( var i = 0; i < len; i++) {
            var nombre = "#permisoLibroUsuarios"+columna+"\\.activo1";

            if($(nombre).prop('checked')){

                $(nombre).prop('checked', false);
            }else {
                $(nombre).prop('checked', true);
            }
            columna = columna + 7;
        }
    }

</script>

</body>
</html>