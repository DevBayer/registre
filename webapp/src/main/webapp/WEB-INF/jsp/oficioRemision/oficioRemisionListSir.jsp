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
                    <li><a href="<c:url value="/inici"/>"><i class="fa fa-globe"></i> ${oficinaActiva.denominacion}</a></li>
                    <li class="active"><i class="fa fa-list-ul"></i> <strong><spring:message code="oficioRemision.oficiosRemision"/></strong></li>
                </ol>
            </div>
        </div><!-- /.row -->

        <c:import url="../modulos/mensajes.jsp"/>

        <!-- BUSCADOR -->

        <div class="row">

            <div class="col-xs-12">

                <div class="panel panel-info">
                
                
                            <c:if test="${paginacion != null}">

                                <div class="row">
                                    <div class="col-xs-12">

                                        <c:if test="${empty paginacion.listado}">
                                            <div class="alert alert-warning alert-dismissable">
                                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                                <spring:message code="regweb.busqueda.vacio"/> <strong><spring:message code="oficioRemision.oficioRemision"/></strong>
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty paginacion.listado}">
<%--  
                                            <div class="alert-grey">
                                                <c:if test="${paginacion.totalResults == 1}">
                                                    <spring:message code="regweb3.resultado"/> <strong>${paginacion.totalResults}</strong> <spring:message code="oficioRemision.oficioRemision"/>
                                                </c:if>
                                                <c:if test="${paginacion.totalResults > 1}">
                                                    <spring:message code="regweb3.resultados"/> <strong>${paginacion.totalResults}</strong> <spring:message code="oficioRemision.oficiosRemision"/>
                                                </c:if>

                                                <p class="pull-right"><spring:message code="regweb3.pagina"/> <strong>${paginacion.currentIndex}</strong> de ${paginacion.totalPages}</p>
                                            </div>
                                            
                                            --%>

                                            <div class="table-responsive">

                                                <table class="table table-bordered table-hover table-striped tablesorter">
                                                    <colgroup>
                                                        <col width="80">
                                                        <col>
                                                        <col>
                                                        <col>
                                                        <col>
                                                        <col>
                                                        <col width="51">
                                                    </colgroup>
                                                    <thead>
                                                        <tr>
                                                            <th><spring:message code="oficioRemision.numeroOficio"/></th>
                                                            <th><spring:message code="oficioRemision.fecha"/></th>
                  <%--                                          <th><spring:message code="oficioRemision.libro.corto"/></th>--%>
                                                            <th><spring:message code="oficioRemision.oficina"/></th>
                                                            <th><spring:message code="oficioRemision.organismoDestino"/></th>
                                                            <th><spring:message code="oficioRemision.numero.re"/></th>
                                                            <th><spring:message code="oficioRemision.estado"/></th>
                                                            <th class="center"><spring:message code="regweb.acciones"/></th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <c:forEach var="oficioRemision" items="${paginacion.listado}" varStatus="status">
                                                            <tr>
                                                                <td><fmt:formatDate value="${oficioRemision.fecha}" pattern="yyyy"/> / ${oficioRemision.numeroOficio}</td>
                                                                <td><fmt:formatDate value="${oficioRemision.fecha}" pattern="dd/MM/yyyy"/></td>
                                                                <%--<td>${oficioRemision.libro.nombreCompleto}</td>--%>
                                                                <td>${oficioRemision.oficina.denominacion}</td>
                                                                <td>${(empty oficioRemision.organismoDestinatario)? oficioRemision.destinoExternoDenominacion : oficioRemision.organismoDestinatario.denominacion}</td>
                                                                <td>${fn:length(oficioRemision.registrosEntrada)}</td>
                                                                <td>
                                                                    <span class="label ${(oficioRemision.estado == 2)?'label-success':'label-danger'}">
                                                                      <spring:message code="oficioRemision.estado.${oficioRemision.estado}"/>
                                                                      <c:if test="${not empty oficioRemision.fechaEstado && oficioRemision.estado != 0}">
                                                                          - <fmt:formatDate value="${oficioRemision.fechaEstado}" pattern="dd/MM/yyyy HH:mm:ss"/>
                                                                      </c:if>
                                                                    </span>
                                                                </td>

                                                                <td class="center">
                                                                    <a class="btn btn-info btn-sm" href="<c:url value="/oficioRemision/${oficioRemision.id}/detalle"/>" title="Detalle"><span class="fa fa-eye"></span></a>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>

                                                <!-- Paginacion -->
                                                <c:import url="../modulos/paginacionBusqueda.jsp">
                                                    <c:param name="entidad" value="oficioRemision"/>
                                                </c:import>

                                            </div>

                                        </c:if>

                                    </div>
                                </div>

                            </c:if>


                        </div>
                </div>
            </div>
        </div>

        <!-- FIN BUSCADOR -->




    </div>
</div> <!-- /container -->

<c:import url="../modulos/pie.jsp"/>


</body>
</html>