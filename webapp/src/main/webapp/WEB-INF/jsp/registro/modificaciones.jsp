<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<%--CONFIGURACIONES SEGÚN EL TIPO DE REGISTRO--%>
<c:if test="${param.tipoRegistro == 'entrada'}">
    <c:set var="color" value="info"/>
</c:if>
<c:if test="${param.tipoRegistro == 'salida'}">
    <c:set var="color" value="danger"/>
</c:if>

<div class="col-xs-8 pull-right">
    <div class="panel panel-${color}">

        <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-pencil-square-o"></i> <strong><spring:message code="regweb.modificaciones"/></strong></h3>
        </div>

        <div class="panel-body">
            <div class="table-responsive">

                <table id="historicos" class="table table-bordered table-hover table-striped">
                    <colgroup>
                        <col>
                        <c:if test="${isAdministradorLibro}"> <col> </c:if>
                        <col>
                        <col width="100">
                    </colgroup>
                    <thead>
                    <tr>
                        <th><spring:message code="historicoEntrada.fecha"/></th>
                        <c:if test="${isAdministradorLibro}"> <th><spring:message code="historicoEntrada.usuario"/></th> </c:if>
                        <th><spring:message code="historicoEntrada.modificacion"/></th>
                        <th><spring:message code="historicoEntrada.estado"/></th>
                        <th class="center"><spring:message code="regweb.acciones"/></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="historico" items="${historicos}">
                        <tr>
                            <td><fmt:formatDate value="${historico.fecha}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
                            <c:if test="${isAdministradorLibro}"> <td>${historico.usuario.nombreCompleto}</td> </c:if>
                            <td>${historico.modificacion}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${historico.estado == 1}">
                                        <span class="label label-success"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 2}">
                                        <span class="label label-warning"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 3}">
                                        <span class="label label-info"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 4 || registro.estado == 5}">
                                        <span class="label label-default"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 6}">
                                        <span class="label label-primary"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 7}">
                                        <span class="label label-primary"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                    <c:when test="${historico.estado == 8}">
                                        <span class="label label-danger"><spring:message code="registro.estado.${historico.estado}" /></span>
                                    </c:when>
                                </c:choose>

                            </td>

                            <%--BOTÓN COMPARAR--%>
                            <td class="center">

                                <%--REGISTRO ENTRADA--%>
                                <c:if test="${param.tipoRegistro == 'entrada'}">
                                    <c:if test="${not empty historico.registroEntradaOriginal}">
                                        <a data-toggle="modal" role="button" href="#modalCompararRegistros" onclick="comparaRegistros('${historico.id}')" class="btn btn-warning btn-sm">Comparar</a>
                                    </c:if>
                                    <c:if test="${empty historico.registroEntradaOriginal}">
                                        <a href="javascript:void(0);" class="btn btn-warning disabled btn-sm">Comparar</a>
                                    </c:if>
                                </c:if>

                                        <%--REGISTRO SALIDA--%>
                                <c:if test="${param.tipoRegistro == 'salida'}">
                                    <c:if test="${not empty historico.registroSalidaOriginal}">
                                        <a data-toggle="modal" role="button" href="#modalCompararRegistros" onclick="comparaRegistros('${historico.id}')" class="btn btn-warning btn-sm">Comparar</a>
                                    </c:if>
                                    <c:if test="${empty historico.registroSalidaOriginal}">
                                        <a href="javascript:void(0);" class="btn btn-warning disabled btn-sm">Comparar</a>
                                    </c:if>
                                </c:if>

                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>

        </div>
    </div>

</div>

<c:if test="${param.tipoRegistro == 'entrada'}">
    <c:import url="../registroEntrada/comparaRegistros.jsp"/>
</c:if>
<c:if test="${param.tipoRegistro == 'salida'}">
    <c:import url="../registroSalida/comparaRegistros.jsp"/>
</c:if>


