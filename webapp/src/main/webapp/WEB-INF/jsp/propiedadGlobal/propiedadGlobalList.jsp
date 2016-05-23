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

        <div class="row">
            <div class="col-xs-12">
                <ol class="breadcrumb">
                    <c:import url="../modulos/migadepan.jsp"/>
                    <li class="active"><i class="fa fa-list-ul"></i> <spring:message
                            code="propiedadGlobal.propiedadesGlobales"/></li>
                </ol>
            </div>
        </div><!-- /.row -->

        <c:import url="../modulos/mensajes.jsp"/>

        <div class="row">
            <div class="col-xs-12">

                <div class="panel panel-success">

                    <div class="panel-heading">
                        <a class="btn btn-success btn-xs pull-right" href="<c:url value="/propiedadGlobal/new"/>"
                           role="button"><span class="fa fa-plus"></span> <spring:message code="propiedadGlobal.nuevo"/></a>
                        <h3 class="panel-title"><i class="fa fa-list"></i> <strong><spring:message
                                code="propiedadGlobal.listado"/></strong></h3>
                    </div>

                    <div class="panel-body">

                        <c:import url="../modulos/mensajes.jsp"/>

                        <c:if test="${empty listado}">
                            <div class="alert alert-warning alert-dismissable">
                                <button type="button" class="close" data-dismiss="alert"
                                        aria-hidden="true">&times;</button>
                                <spring:message code="regweb.listado.vacio"/> <strong><spring:message
                                    code="propiedadGlobal.propiedadGlobal"/></strong>
                            </div>
                        </c:if>

                        <c:if test="${not empty listado}">

                            <div class="alert-grey">
                                <c:if test="${paginacion.totalResults == 1}">
                                    <spring:message code="regweb.resultado"/>
                                    <strong>${paginacion.totalResults}</strong> <spring:message
                                        code="propiedadGlobal.propiedadGlobal"/>
                                </c:if>
                                <c:if test="${paginacion.totalResults > 1}">
                                    <spring:message code="regweb.resultados"/>
                                    <strong>${paginacion.totalResults}</strong> <spring:message
                                        code="propiedadGlobal.propiedadesGlobales"/>
                                </c:if>

                                <p class="pull-right"><spring:message code="regweb.pagina"/>
                                    <strong>${paginacion.currentIndex}</strong> de ${paginacion.totalPages}</p>
                            </div>

                            <div class="table-responsive">
                                <table class="table table-bordered table-hover table-striped tablesorter">
                                    <colgroup>
                                        <col>
                                        <col>
                                        <col>
                                        <col width="100">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th><spring:message code="propiedadGlobal.clave"/></th>
                                        <th><spring:message code="propiedadGlobal.descripcion"/></th>
                                        <th><spring:message code="propiedadGlobal.valor"/></th>
                                        <th class="center"><spring:message code="regweb.acciones"/></th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="propiedadGlobal" items="${listado}">
                                        <tr>
                                            <td>${propiedadGlobal.clave}</td>
                                            <td>${propiedadGlobal.descripcion}</td>
                                            <td>${propiedadGlobal.valor}</td>
                                            <td class="center">
                                                <a class="btn btn-warning btn-sm"
                                                   href="<c:url value="/propiedadGlobal/${propiedadGlobal.id}/edit"/>"
                                                   title="<spring:message code="regweb.editar"/>"><span
                                                        class="fa fa-pencil"></span></a>
                                                <a class="btn btn-danger btn-sm" onclick='javascript:confirm("<c:url value="/propiedadGlobal/${propiedadGlobal.id}/delete"/>","<spring:message code="regweb.confirmar.eliminacion" htmlEscape="true"/>")' href="javascript:void(0);" title="<spring:message code="regweb.eliminar"/>"><span class="fa fa fa-eraser"></span></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>

                                <!-- Paginacion -->
                                <c:import url="../modulos/paginacion.jsp">
                                    <c:param name="entidad" value="propiedadGlobal"/>
                                </c:import>

                            </div>

                        </c:if>

                    </div>
                    <%--Fin panel-body--%>

                </div>
            </div>
        </div>


    </div>
</div> <!-- /container -->

<c:import url="../modulos/pie.jsp"/>


</body>
</html>