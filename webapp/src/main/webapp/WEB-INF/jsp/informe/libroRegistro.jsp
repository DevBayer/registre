<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/modulos/includes.jsp" %>

<!DOCTYPE html>
<html lang="ca">
<head>
    <title><spring:message code="regweb.titulo"/></title>
    <c:import url="../modulos/imports.jsp"/>
    <script type="text/javascript" src="<c:url value="/js/busquedaorganismo.js"/>"></script>
</head>

<body>

<c:import url="../modulos/menu.jsp"/>

<div class="row-fluid container main">

    <div class="well well-white">

        <div class="row">
            <div class="col-xs-12">
                <ol class="breadcrumb">
                    <c:import url="../modulos/migadepan.jsp"/>
                    <li class="active"><i class="fa fa-list-ul"></i> <spring:message code="informe.libroRegistro"/></li>
                </ol>
            </div>
        </div><!-- /.row -->


        <!-- BUSCADOR -->
            <div class="row">

                <div class="col-xs-12">

                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-search"></i><spring:message code="informe.libroRegistro"/> </h3>
                        </div>
                        <div class="panel-body">
                            <form:form modelAttribute="informeLibroBusquedaForm" method="post" cssClass="form-horizontal" name="informeLibroBusquedaForm" onsubmit="return validaFormulario(this)">
                                <div class="row">
                                    <div class="form-group col-xs-6 pad-left">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="tipo"><span class="text-danger">*</span> <spring:message code="informe.tipoLibro"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right">
                                            <form:select path="tipo" cssClass="chosen-select" onchange="actualizarLibros(this.selectedIndex)">
                                                <form:option value="1" default="default"><spring:message code="informe.entrada"/></form:option>
                                                <form:option value="2"><spring:message code="informe.salida"/></form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-6  pad-left">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="formato"><span class="text-danger">*</span> <spring:message code="regweb.formato"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right">
                                            <form:select path="formato" cssClass="chosen-select">
                                                <form:option value="pdf" default="default"><spring:message code="regweb.formato.pdf" /></form:option>
                                                <form:option value="excel"><spring:message code="regweb.formato.excel"/></form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-6 pad-left libros1">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="libros"><span class="text-danger">*</span> <spring:message code="registroEntrada.libro"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right" id="libr">
                                            <c:if test="${fn:length(libros) eq 1}">
                                                <form:select path="libros" items="${libros}" itemValue="id" itemLabel="libroOrganismo" cssClass="chosen-select" multiple="false"/>
                                            </c:if>
                                            <c:if test="${fn:length(libros) gt 1}">
                                                <spring:message code="informe.libros" var="varLibros"/>
                                                <form:select data-placeholder="${varLibros}" path="libros" items="${libros}" itemValue="id" itemLabel="libroOrganismo" cssClass="chosen-select" multiple="true"/>
                                            </c:if>
                                            <span id="librosErrors"></span>
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-6 pad-left campos1">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="campos"><span class="text-danger">*</span> <spring:message code="regweb.campos"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right" id="campos">
                                            <spring:message code="informe.campos" var="varCampos"/>
                                            <form:select data-placeholder="${varCampos}" multiple="true" cssClass="chosen-select" id="campos" path="campos" name="campos">
                                                <form:option value="llibr" selected="selected"><spring:message code="registroEntrada.libro"/></form:option>
                                                <form:option value="ofici" selected="selected"><spring:message code="registroEntrada.oficina"/></form:option>
                                                <form:option value="anyRe" selected="selected"><spring:message code="registroEntrada.anyRegistro"/></form:option>
                                                <form:option value="data" selected="selected"><spring:message code="registroEntrada.dataRegistre"/></form:option>
                                                <form:option value="numRe" selected="selected"><spring:message code="registroEntrada.numeroRegistro"/></form:option>
                                                <form:option value="extra" selected="selected"><spring:message code="registroEntrada.extracto"/></form:option>
                                                <form:option value="tipAs" selected="selected"><spring:message code="registroEntrada.tipoAsunto"/></form:option>
                                                <form:option value="nomIn" selected="selected"><spring:message code="registroEntrada.interesados"/></form:option>
                                                <form:option value="orgOr" selected="selected"><spring:message code="registroEntrada.oficinaOrigen"/></form:option>
                                                <form:option value="numOr" selected="selected"><spring:message code="registroEntrada.numeroRegistroOrigen"/></form:option>
                                                <form:option value="datOr" selected="selected"><spring:message code="registroEntrada.dataOrigen"/></form:option>
                                                <form:option value="orgDe" selected="selected"><spring:message code="registroEntrada.destinoOrigen"/></form:option>
                                                <form:option value="docFi" selected="selected"><spring:message code="registroEntrada.documentacionFisica"/></form:option>
                                                <form:option value="idiom" selected="selected"><spring:message code="registroEntrada.idioma"/></form:option>
                                                <form:option value="obser" selected="selected"><spring:message code="registroEntrada.observaciones"/></form:option>
                                                <form:option value="estat" selected="selected"><spring:message code="registroEntrada.estado"/></form:option>
                                                <form:option value="exped" selected="selected"><spring:message code="registroEntrada.expediente"/></form:option>
                                                <form:option value="codAs"><spring:message code="registroEntrada.codigoAsunto"/></form:option>
                                                <form:option value="refEx"><spring:message code="registroEntrada.referenciaExterna"/></form:option>
                                                <form:option value="trans"><spring:message code="registroEntrada.transporte"/></form:option>
                                                <form:option value="numTr"><spring:message code="registroEntrada.numTransporte"/></form:option>
                                            </form:select>
                                            <span id="camposErrors"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-xs-6 pad-left">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="fechaInicio"><span class="text-danger">*</span> <spring:message code="informe.fechaInicio"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right" id="fechaInicio">
                                            <div class="input-group date no-pad-right">
                                                <form:input type="text" cssClass="form-control" path="fechaInicio" maxlength="10" placeholder="dd/mm/yyyy" name="fechaInicio"/>
                                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                                            </div>
                                            <span class="errors"></span>
                                        </div>
                                    </div>
                                    <div class="form-group col-xs-6  pad-left">
                                        <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                            <form:label path="fechaFin"><span class="text-danger">*</span> <spring:message code="informe.fechaFin"/></form:label>
                                        </div>
                                        <div class="col-xs-9 no-pad-right" id="fechaFin">
                                            <div class="input-group date no-pad-right">
                                                <form:input type="text" cssClass="form-control" path="fechaFin" maxlength="10" placeholder="dd/mm/yyyy" name="fechaFin"/>
                                                <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                                            </div>
                                            <span class="errors"></span>
                                        </div>
                                    </div>
                                </div>

                                <%--Comprueba si debe mostrar las opciones desplegadas o no--%>
                                <c:if test="${empty informeLibroBusquedaForm.interessatDoc && empty informeLibroBusquedaForm.interessatNom && empty informeLibroBusquedaForm.organDestinatari && empty informeLibroBusquedaForm.observaciones && empty informeLibroBusquedaForm.usuario && !informeLibroBusquedaForm.anexos}">
                                  <div id="demo" class="collapse">
                                </c:if>
                                <c:if test="${not empty informeLibroBusquedaForm.interessatDoc || not empty informeLibroBusquedaForm.interessatNom || not empty informeLibroBusquedaForm.organDestinatari || not empty informeLibroBusquedaForm.observaciones || not empty informeLibroBusquedaForm.usuario || informeLibroBusquedaForm.anexos}">
                                  <div id="demo" class="collapse in">
                                </c:if>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.numeroRegistro"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input path="numeroRegistroFormateado" cssClass="form-control"/> <form:errors path="numeroRegistroFormateado" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.extracto"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input path="extracto" cssClass="form-control" maxlength="200" /> <form:errors path="extracto" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.nombreInteresado"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input  path="interessatNom" cssClass="form-control" maxlength="255"/>
                                                <form:errors path="interessatNom" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="interesado.apellido1"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input path="interessatLli1" cssClass="form-control" maxlength="255"/>
                                                <form:errors path="interessatLli1" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="interesado.apellido2"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input path="interessatLli2" cssClass="form-control" maxlength="255"/>
                                                <form:errors path="interessatLli2" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.docInteresado"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input  path="interessatDoc" cssClass="form-control" maxlength="17"/>
                                                <form:errors path="interessatDoc" cssClass="help-block" element="span"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.estado"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:select path="estado" cssClass="chosen-select">
                                                    <form:option value="-1" label="..."/>
                                                    <c:forEach var="estado" items="${estados}">
                                                        <form:option value="${estado}"><spring:message code="registro.estado.${estado}"/></form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>

                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.anexos"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:checkbox path="anexos"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registro.oficinaRegistro"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:select path="idOficina" cssClass="chosen-select">
                                                    <form:option value="-1" label="..."/>
                                                    <c:forEach var="oficinaRegistro" items="${oficinasRegistro}">
                                                        <form:option value="${oficinaRegistro.id}">${oficinaRegistro.denominacion}</form:option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>

                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label" id="orgDest"><spring:message code="registroEntrada.organDestinatari"/></div>
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label hidden"  id="orgOrig"><spring:message code="registroEntrada.organismoOrigen"/></div>
                                            <div class="col-xs-7 no-pad-right">
                                                <form:select path="organDestinatari" cssClass="chosen-select" onchange="actualizarOrganDestinatariNom(${organismo.denominacion})">
                                                    <form:option value="" label="..."/>
                                                    <c:forEach items="${organosDestino}" var="organismo">
                                                        <option value="${organismo.codigo}" <c:if test="${registroEntradaBusqueda.organDestinatari == organismo.codigo}">selected="selected"</c:if>>${organismo.denominacion}</option>
                                                    </c:forEach>
                                                </form:select>
                                                <form:errors path="organDestinatari" cssClass="help-block" element="span"/>
                                                <form:hidden path="organDestinatariNom"/>
                                            </div>
                                            <div class="col-xs-2 boto-panel">
                                                <a data-toggle="modal" role="button" href="#modalBuscadorlistaRegEntrada"
                                                   onclick="inicializarBuscador('#codNivelAdministracionlistaRegEntrada','#codComunidadAutonomalistaRegEntrada','#provincialistaRegEntrada','#localidadlistaRegEntrada','${oficina.organismoResponsable.nivelAdministracion.codigoNivelAdministracion}', '${oficina.organismoResponsable.codAmbComunidad.codigoComunidad}', 'listaRegEntrada' );"
                                                   class="btn btn-success btn-sm"><spring:message code="regweb.buscar"/></a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.observaciones"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:input path="observaciones" class="form-control" type="text" value=""/>
                                            </div>
                                        </div>
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="usuario.usuario"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:select path="usuario" class="chosen-select">
                                                    <form:option value="">...</form:option>
                                                    <c:forEach items="${usuariosEntidad}" var="usuarioEntidad">
                                                        <option value="${usuarioEntidad.usuario.identificador}" <c:if test="${usuario == usuarioEntidad.usuario.identificador}">selected="selected"</c:if>>${usuarioEntidad.usuario.identificador}</option>
                                                    </c:forEach>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="form-group col-xs-6 pad-left">
                                            <div class="col-xs-3 pull-left etiqueta_regweb control-label">
                                                <spring:message code="registroEntrada.tipoAsunto"/>
                                            </div>
                                            <div class="col-xs-9 no-pad-right">
                                                <form:select path="idTipoAsunto" cssClass="chosen-select">
                                                    <form:option value="-1" label="..."/>
                                                    <form:options items="${tiposAsunto}" itemValue="id"
                                                                  itemLabel="traducciones['${pageContext.response.locale}'].nombre"/>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="row pad-bottom15 pad-right-75">
                                    <a class="btn btn-success btn-xs pull-right masOpciones-success" data-toggle="collapse" data-target="#demo">
                                            <%--Comprueba si debe mostrar mas opciones o menos--%>
                                        <c:if test="${empty registroEntradaBusqueda.registroEntrada.oficina.id && empty registroEntradaBusqueda.interessatDoc && empty registroEntradaBusqueda.interessatNom && empty registroEntradaBusqueda.organDestinatari && empty registroEntradaBusqueda.observaciones && empty registroEntradaBusqueda.usuario && !registroEntradaBusqueda.anexos}">
                                            <span class="fa fa-plus"></span> <spring:message code="regweb.busquedaAvanzada"/>
                                        </c:if>
                                        <c:if test="${not empty registroEntradaBusqueda.registroEntrada.oficina.id || not empty registroEntradaBusqueda.interessatDoc || not empty registroEntradaBusqueda.interessatNom || not empty registroEntradaBusqueda.organDestinatari || not empty registroEntradaBusqueda.observaciones || not empty registroEntradaBusqueda.usuario || registroEntradaBusqueda.anexos}">
                                            <span class="fa fa-minus"></span> <spring:message code="regweb.busquedaAvanzada"/>
                                        </c:if>
                                    </a>
                                </div>


                                <div class="form-group col-xs-12">
                                    <button type="submit" class="btn btn-warning"><spring:message code="regweb.buscar"/></button>
                                </div>

                                <c:set var="errorInicio"><spring:message code="error.fechaInicio.posterior"/></c:set>
                                <input id="error1" type="hidden" value="${errorInicio}"/>
                                <c:set var="errorFin"><spring:message code="error.fechaFin.posterior"/></c:set>
                                <input id="error2" type="hidden" value="${errorFin}"/>
                                <c:set var="errorInicioFin"><spring:message code="error.fechaInicioFin.posterior"/></c:set>
                                <input id="error3" type="hidden" value="${errorInicioFin}"/>

                            </form:form>
                    </div>
                </div>
            </div>
        </div>
    <!-- FIN BUSCADOR-->

    <!-- Importamos el codigo jsp del modal del formulario para realizar la búsqueda de organismos Destino o Origen
        Mediante el archivo "busquedaorganismo.js" se implementa dicha búsqueda -->

        <c:import url="../registro/buscadorOrganismosOficinasREPestanas.jsp">
            <c:param name="tipo" value="listaRegEntrada"/>
        </c:import>

    <%--<c:if test="${tipo == 2}">--%>
        <%--<c:import url="../registro/buscadorOrganismosOficinasREPestanas.jsp">--%>
            <%--<c:param name="tipo" value="listaRegSalida"/>--%>
        <%--</c:import>--%>
    <%--</c:if>--%>


</div>
</div> <!-- /container -->

<c:import url="../modulos/pie.jsp"/>


<!-- Modifica los Libros según el tipo de Registro elegido -->
<script type="text/javascript">

function actualizarLibros(tipo){
    <c:url var="obtenerLibros" value="/informe/obtenerLibros" />
    actualizarLibrosTodos('${obtenerLibros}','#libros',$('#tipo option:selected').val(),$('#libros option:selected').val(),true);
    // Mostram el camp segons el tipus de registre que cercam
    if(tipo==0){
        $("#orgDest").removeClass("hidden");
        $("#orgOrig").addClass("hidden");
    }
    if(tipo==1){
        $("#orgDest").addClass("hidden");
        $("#orgOrig").removeClass("hidden");
    }
}

</script>

<!-- VALIDADOR DE FORMULARI -->
<script type="text/javascript">

//Valida los libros seleccionados (libros, nombre del libro)
function librosSeleccionados(select, camp) {
var variable = '';
var htmlBuit = '';
// Valor de los libros
var value = $(select).val();
var numLibros = 0;
if (value!=null && value!=""){
    // Número de los libros en el select
    numLibros = value.length;
}
// Si hay menos de un libro seleccionado, retorna error
if (numLibros<1){
    variable = "#" + camp + " span#librosErrors";
    htmlBuit = "<span id='librosErrors' class='help-block'>És obligatori elegir almanco 1 llibre</span>";
    $(variable).html(htmlBuit);
    $(variable).parents(".libros1").addClass("has-error");
    $('ul.chosen-choices').css('border-color','#a94442');
    return false;
}else{
    variable = "#" + camp + " span:contains('elegir')";
    $(variable).removeClass("help-block");
    $(variable).parents(".libros1").removeClass("has-error");
    htmlBuit = "<span id='librosErrors'></span>";
    $(variable).html(htmlBuit);
    $('ul.chosen-choices').css('border-color','#aaa');
    return true;
}
}

//Valida los campos seleccionados (campos, nombre del campo)
function camposSeleccionados(select, camp) {
var variable = '';
var htmlBuit = '';
// Valor de los campos
var value = $(select).val();
var numCampos = 0;
if (value!=null && value!=""){
    // Número de los campos en el select
    numCampos = value.length;
}
// Si hay menos de dos campos seleccionados, retorna error
if (numCampos<2){
    variable = "#" + camp + " span#camposErrors";
    htmlBuit = "<span id='camposErrors' class='help-block'>És obligatori elegir almanco 2 camps</span>";
    $(variable).html(htmlBuit);
    $(variable).parents(".campos1").addClass("has-error");
    $('ul.chosen-choices').css('border-color','#a94442');
    return false;
}else{
    variable = "#" + camp + " span:contains('elegir')";
    $(variable).removeClass("help-block");
    $(variable).parents(".campos1").removeClass("has-error");
    htmlBuit = "<span id='camposErrors'></span>";
    $(variable).html(htmlBuit);
    $('ul.chosen-choices').css('border-color','#aaa');
    return true;
}
}

// Valida el formuario si las fechas Inicio y Fin son correctas, hay almenos 2 campos seleccionados, hay un Libro seleccionado
function validaFormulario(form) {
var fechaInicio = true;
var fechaFin = true;
var libros = true;
var campos = true;
var fechas = true;
// Valida el formato de Fecha de Inicio
if (!validaFecha(form.fechaInicio, 'fechaInicio')) {
    fechaInicio = false;
}
// Valida el formato de Fecha de Fin
if (!validaFecha(form.fechaFin, 'fechaFin')) {
    fechaFin = false;
}
// Si las Fechas son correctas, Valida el Fecha Inicio y Fecha Fin menor o igual que fecha actual, Fecha Inicio menor o igual que Fecha Fin
if((fechaInicio)&&(fechaFin)){
    if (!validaFechasConjuntas(form.fechaInicio, form.fechaFin, 'fechaInicio', 'fechaFin')) {
        fechas = false;
    }
}
// Valida los libros seleccionados
if (!librosSeleccionados(form.libros, 'libr')){
    libros = false;
}
// Valida los campos seleccionados
if (!camposSeleccionados(form.campos, 'campos')){
    campos = false;
}
// Si todos los campos son correctos, hace el submit
if((fechaInicio)&&(fechaFin)&&(libros)&&(campos)&&(fechas)){
    return true;
} else{
    return false;
}
}

</script>

<script type="text/javascript">
function actualizarLibrosTodos(url, idSelect, seleccion, valorSelected, todos){
    var html = '';
    if(seleccion != '-1'){
        jQuery.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            data: { id: seleccion },
            contentType: 'application/json',
            success: function(result) {
                if(todos){html = '';}
                var len = result.length;
                var selected='';
                for ( var i = 0; i < len; i++) {
                    selected='';
                    if(result.length == 1){
                        selected = 'selected="selected"';
                    }
                    html += '<option '+selected+' value="' + result[i].id + '">'
                    + result[i].libroOrganismo + '</option>';
                }
                html += '</option>';

                if(len != 0){
                    $(idSelect).html(html);
                    $(idSelect).attr("disabled",false).trigger("chosen:updated");
                }else if(len==0){
                    var html='';
                    $(idSelect).html(html);
                    $(idSelect).attr("disabled",true).trigger("chosen:updated");
                }
            }
        });

    }
}
</script>

<!-- Cambia la imagen de la búsqueda avanzada-->
<script>
    var traduccion = new Array();
    traduccion['regweb.busquedaAvanzada'] = "<spring:message code='regweb.busquedaAvanzada' javaScriptEscape='true' />";

    $(function(){
        $("#demo").on("hide.bs.collapse", function(){
            $(".masOpciones-success").html('<span class="fa fa-plus"></span> ' + traduccion['regweb.busquedaAvanzada']);
        });
        $("#demo").on("show.bs.collapse", function(){
            $(".masOpciones-success").html('<span class="fa fa-minus"></span> ' + traduccion['regweb.busquedaAvanzada']);
        });
    });
</script>

<!-- Cambia el valor del Nombre del Organismo Destinatario-->
<script>
    function actualizarOrganDestinatariNom(denominacio){
        $("#organDestinatariNom").html(denominacio);
    }
</script>

</body>
</html>