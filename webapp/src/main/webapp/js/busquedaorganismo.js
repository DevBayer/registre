/*
 Funciones especficas para la búsqueda de organismo y oficinas en un registro de entrada.
 En función del tipoOrganismo indicado, se realizará búsqueda de organismos u oficinas.
 */


// Muestra la informacion de los Popover de la lista de Organismos
$('a[rel=popover]').popover();

mostraInformacio = function () {
    var zIndices = new Array();
    $('div.popover, div.modal').each(function () {
        zIndices.push($(this).attr('class') + '; z-index: ' + $(this).css('z-index'));
    });
    var html = zIndices.join('<br>');
    $('.show-z-index').html(html);
};

$('body').on('hover', 'a[rel=popover]', mostraInformacio);


/*
 Función que pinta una tabla con los resultados obtenidos de la búsqueda.
 @param tipoOrganismo indica desde donde se realiza la búsqueda para poder asignar
 los resultados al select que corresponde.
 */


/**
 *
 * @param tipoOrganismo
 * @returns {string}
 */
function getUrlBusqueda(tipoOrganismo, urlServidor) {

    if (tipoOrganismo == 'OficinaOrigen') {
        return urlServidor + "/rest/busqueda/oficinas";
    } else {
        return urlServidor + "/rest/busqueda/organismos";
    }
}

/**
 *
 * @param tipoOrganismo
 * @returns {*}
 */
function getIdSelect(tipoOrganismo) {

    /* REGISTRO ENTRADA y REGISTRO SALIDA */
    // Caso de organismo Destino
    if (tipoOrganismo == 'OrganismoDestino') {
        return "#destino\\.codigo";
    }
    // Caso de oficina origen
    if (tipoOrganismo == 'OficinaOrigen') {
        return "#registroDetalle\\.oficinaOrigen\\.codigo";
    }
    // Caso administracion interesado
    if (tipoOrganismo == 'OrganismoInteresado') {
        return "#registroDetalle\\.organismoInteresado\\.codigo";
    }
    // Caso Organismo Origen de registro salida
    if (tipoOrganismo == 'OrganismoOrigen') {
        return "#origen\\.codigo";
    }

    /*LISTADOS REGISTRO ENTRADA Y SALIDA*/
    if (tipoOrganismo == 'listaRegEntrada') {
        return "#organDestinatari";
    }

    if (tipoOrganismo == 'listaRegSalida') {
        return "#organOrigen";
    }
}

/**
 *
 * @param tipoOrganismo
 * @returns {*}
 */
function getIdDenominacion(tipoOrganismo) {

    /* REGISTRO ENTRADA Y REGISTRO SALIDA */
    // Caso de organismo Destino
    if (tipoOrganismo == 'OrganismoDestino') {
        return "#destino\\.denominacion";
    }
    // Caso de oficina origen
    if (tipoOrganismo == 'OficinaOrigen') {
        return "#registroDetalle\\.oficinaOrigen\\.denominacion";
    }
    // Caso administracion interesado
    if (tipoOrganismo == 'OrganismoInteresado') {
        return "#registroDetalle\\.organismoInteresado\\.denominacion";
    }
    // Caso Organismo Origen de registro salida
    if (tipoOrganismo == 'OrganismoOrigen') {
        return "#origen\\.denominacion";
    }

    /* LISTADOS REGISTRO ENTRADA Y SALIDA */
    if (tipoOrganismo == 'listaRegEntrada') {
        return "#organDestinatariNom";
    }

    if (tipoOrganismo == 'listaRegSalida') {
        return "#organOrigenNom";
    }
}

function getIdDenominacionExterna(tipoOrganismo) {
    /* REGISTRO ENTRADA Y REGISTRO SALIDA */
    // Caso de organismo Destino
    if (tipoOrganismo == 'OrganismoDestino') {
        return "#destinoExternoDenominacion";
    }
    // Caso de oficina origen
    if (tipoOrganismo == 'OficinaOrigen') {
        return "#registroDetalle\\.oficinaOrigenExternoDenominacion";
    }
}

/**
 *
 * @param tipoOrganismo
 * @param urlServidor
 * @param idRegistroDetalle
 */
function organismoBusqueda(tipoOrganismo, urlServidor, idRegistroDetalle) {

    // obtenemos los valores del formulario para realizar la búsqueda.
    var denominacion = $('#denominacion' + tipoOrganismo).val();
    var codigoOrganismo = $('#codigoOrganismo' + tipoOrganismo).val();
    var codNivelAdministracion = $('#codNivelAdministracion' + tipoOrganismo).val();
    var codComunidadAutonoma = $('#codComunidadAutonoma' + tipoOrganismo).val();
    var provincia = $('#provincia' + tipoOrganismo).val();

    //La localidad es una dupla (codigoLocalidad - codigoEntidadGeografica
    var localidad = $('#localidad' + tipoOrganismo).val();


    // indica si queremos obtener aquellos organismos que son unidad Raiz
    var unidadRaiz = false;
    // indica que queremos obtener de la búsqueda de organismos aquellos que tienen oficinas
    //de registro
    var conOficinas = false;

    // Variables configurables en función del tipo de organismo indicado.
    var url = getUrlBusqueda(tipoOrganismo, urlServidor);
    // Caso administracion interesado
    if (tipoOrganismo == 'OrganismoInteresado') {
        unidadRaiz = $('#unidadRaiz' + tipoOrganismo).prop('checked');
    }

    // Inicializamos el div del resultado de busqueda.
    $('#resultadosbusqueda' + tipoOrganismo).empty();
    $('#resultadosbusqueda' + tipoOrganismo).html('');
    $('#resultadosbusqueda' + tipoOrganismo).attr("display:none");
    $('#arbol' + tipoOrganismo).html("");
    if (tipoOrganismo != 'OficinaOrigen') {
        $('#tab' + tipoOrganismo + ' a:last').hide();
    }


    // Definimos la tabla que contendrá los resultados.
    var idTablaResultados = "tresultadosbusqueda" + tipoOrganismo;
    var table = $('<table id="' + idTablaResultados + '"></table>').addClass('paginated table table-bordered table-hover table-striped tablesorter ');
    table.append('<colgroup><col width="310"><col width="310"><col width="125"><col width="100"></colgroup>');


    // table.append('<thead><tr><th>'+tradorganismo['organismo.denominacion']+'</th><th>'+tradorganismo['organismo.superior']+'</th><th>'+tradorganismo['regweb.acciones']+'</th></tr></thead><tbody></tbody>');
    table.append('<thead><tr><th>' + tradorganismo['organismo.denominacion'] + '</th><th>' + tradorganismo['organismo.raiz'] + '</th><th>' + tradorganismo['organismo.localidad'] + '</th><th>' + tradorganismo['regweb.acciones'] + '</th></tr></thead><tbody></tbody>');

    //Mostram la imatge de reload
    $('#reloadorg' + tipoOrganismo).show();
    // realizamos la busqueda en dir3 mediante REST.
    $.ajax({
        async: false,
        crossDomain: true,
        url: url,
        type: 'GET',
        dataType: 'json',
        data: {
            codigo: codigoOrganismo,
            denominacion: denominacion,
            codNivelAdministracion: codNivelAdministracion,
            codComunidadAutonoma: codComunidadAutonoma,
            conOficinas: conOficinas,
            unidadRaiz: unidadRaiz,
            provincia: provincia,
            localidad: localidad
        },
        success: function (result) {

            $('#resultadosbusqueda' + tipoOrganismo).css('display', 'block');
            var len = result.length;
            for (var i = 0; i < len; i++) {
                var denominacion = '';
                var codigo = '';


                // Sustituimos comillas simples y dobles para evitar problemas en la llamada
                // a la función posterior
                denominacion = normalizarTexto(result[i].denominacion);
                codigo = result[i].codigo;


                //var title = $('#organismo_raiz').val()+": "+result[i].raiz;
                var title = result[i].raiz;
                // el elemento superior puede ser null, en ese caso le ponemos un texto por defecto
                var superior = $('#organismo_superior').val() + ": " + result[i].superior;
                if (result[i].superior == null) {
                    superior = tradorganismo['organismo.superior.vacio'];
                }


                // definimos el contenido de la tabla en función de los resultados de la busqueda.
                var organigramaTab = '#organigrama' + tipoOrganismo;
                if (tipoOrganismo == 'OrganismoInteresado') {
                    var linea = "<tr><td style=\"text-align:left;\"><label rel=\"popover\" class=\"no-bold text-gris\" style=\"cursor: pointer;\" title=\"" + superior + "\">" + result[i].denominacion + " - " + result[i].codigo + "</label></td><td style=\"text-align:left;\"> " + title + "</td><td style=\"text-align:left;\"> " + result[i].localidad + "</td><td class=\"center\"><a class=\"btn btn-warning btn-sm\" title=\"Seleccionar\" onclick=\"addAdministracionInteresadosModal('" + codigo + "','" + denominacion + "','Administración','" + tipoOrganismo + "','" + idRegistroDetalle + "')\"><span class=\"fa fa-hand-o-right\"></span></a><a class=\"btn btn-success btn-sm\" title=\"Arbol\" onclick=\"mostrarArbol('" + codigo + "','" + urlServidor + "','" + tipoOrganismo + "','" + idRegistroDetalle + "')\"><span class=\"fa fa-sitemap\"></span></a></td></tr>";
                } else if (tipoOrganismo == 'OrganismoDestino' || tipoOrganismo == 'listaRegEntrada' || tipoOrganismo == 'listaRegSalida') {
                    var linea = "<tr><td style=\"text-align:left;\"><label rel=\"popover\" class=\"no-bold text-gris\" style=\"cursor: pointer;\" title=\"" + superior + "\">" + result[i].denominacion + " - " + result[i].codigo + "</label></td><td style=\"text-align:left;\"> " + title + "</td><td style=\"text-align:left;\"> " + result[i].localidad + "</td><td class=\"center\"><a class=\"btn btn-warning btn-sm\" title=\"Seleccionar\" onclick=\"asignarOrganismo('" + codigo + "','" + denominacion + "','" + tipoOrganismo + "')\"><span class=\"fa fa-hand-o-right\"></span></a><a class=\"btn btn-success btn-sm\" title=\"Arbol\" onclick=\"mostrarArbol('" + codigo + "','" + urlServidor + "','" + tipoOrganismo + "','" + idRegistroDetalle + "')\"><span class=\"fa fa-sitemap\"></span></a></td></tr>";
                } else if (tipoOrganismo == 'OficinaOrigen') {
                    var linea = "<tr><td style=\"text-align:left;\"><label rel=\"popover\" class=\"no-bold text-gris\" style=\"cursor: pointer;\" title=\"" + superior + "\">" + result[i].codigo + " - " + result[i].denominacion + "</label></td><td style=\"text-align:left;\"> " + title + "</td><td style=\"text-align:left;\"> " + result[i].localidad + "</td><td class=\"center\"><a class=\"btn btn-warning btn-sm\" title=\"Seleccionar\" onclick=\"asignarOrganismo('" + codigo + "','" + denominacion + "','" + tipoOrganismo + "')\"><span class=\"fa fa-hand-o-right\"></span></a></td></tr>";
                }

                table.append(linea);
            }
            // Ocultamos imagen reload
            $('#reloadorg' + tipoOrganismo).hide();
            // Se muestra solo si hay resultados
            if (len != 0) {
                $('#resultadosbusqueda' + tipoOrganismo).attr("display:block");
                var paginacion = '';
                paginacion += "<div class='alert-grey'>";

                if (len == 1) {
                    paginacion += tradorganismo['regweb.resultado'] + ' <strong>' + len + '</strong>&nbsp;' + tradorganismo['interesado.resultado'];
                }
                if (len > 1) {
                    paginacion += tradorganismo['regweb.resultados'] + ' <strong>' + len + '</strong>&nbsp;' + tradorganismo['interesado.resultados'];
                }
                paginacion += '<div/>';

                $('#resultadosbusqueda' + tipoOrganismo).append(paginacion);
                $('#resultadosbusqueda' + tipoOrganismo).append(table);

            } else if (len == 0) {
                //$('#resultadosbusqueda'+tipoOrganismo).attr("display:none");
                $('#resultadosbusqueda' + tipoOrganismo).html('<br/><div class="alert alert-warning" style="text-align:left;">' + tradorganismo['regweb.noresultados'] + '</div>');
            }

            // mostramos el listado paginado.
            $('#tresultadosbusqueda' + tipoOrganismo, 'td').each(function (i) {
                $(this).text(i + 1);
            });

            $('#tresultadosbusqueda' + tipoOrganismo).each(function () {

                var currentPage = 0;
                var numPerPage = 10;
                var $table = $(this);
                $table.bind('repaginate', function () {
                    $table.find('tbody tr').hide().slice(currentPage * numPerPage, (currentPage + 1) * numPerPage).show();
                });
                $table.trigger('repaginate');
                var numRows = $table.find('tbody tr').length;
                var numPages = Math.ceil(numRows / numPerPage);
                //  var $pager = $('<div class="pager"></div>');
                var $pager = $('<ul class="pagination pagination-sm"></ul>');
                for (var page = 0; page < numPages; page++) {
                    var numero = page + 1;
                    $('<li><a href="javascript:void(0);">' + numero + '</a></li>').bind('click', {
                        newPage: page
                    }, function (event) {
                        currentPage = event.data['newPage'];
                        $table.trigger('repaginate');
                        $(this).addClass('active').siblings().removeClass('active');
                    }).appendTo($pager);
                }
                //$pager.insertBefore($table).find('li:first').addClass('active');
                $pager.insertAfter($table).find('li:first').addClass('active');

            });

        }


    });

}


/* Función que limpia el formulario de búsqueda y vacia la tabla de resultados de la búsqueda
 * @param tipoOrganismo, coletilla para los id de los formularios, ya que se importa el mismo jsp varias veces
 * */
function limpiarFormularioBusqueda(tipoOrganismo) {
    clearForm("#organismoREBusquedaForm" + tipoOrganismo);
    $('#resultadosbusqueda' + tipoOrganismo).empty();
    $('#resultadosbusqueda' + tipoOrganismo).html('');
    $('#resultadosbusqueda' + tipoOrganismo).attr("display:none");
    $('#reloadorg' + tipoOrganismo).hide();
}

/**
 *
 * @param selectNivelAdministracion ID Select
 * @param selectComunidadAutonoma ID Select
 * @param selectProvincia ID Select
 * @param selectLocalidad ID Select
 * @param idNivelAdministracion Identificador
 * @param idComunidadAutonoma
 * @param idProvincia
 * @param idLocalidad
 * @param tipoOrganismo
 */
function inicializarBuscador(selectNivelAdministracion, selectComunidadAutonoma, selectProvincia, selectLocalidad, idNivelAdministracion, idComunidadAutonoma, tipoOrganismo) {

    $('#reloadorg' + tipoOrganismo).hide();
    $(selectNivelAdministracion).val(idNivelAdministracion);
    $(selectNivelAdministracion).trigger("chosen:updated");
    $(selectComunidadAutonoma).val(idComunidadAutonoma);
    $(selectComunidadAutonoma).trigger("chosen:updated");
    $(selectProvincia).attr("disabled", "disabled");
    $(selectLocalidad).attr("disabled", "disabled");
    actualizarProvinciaDestinatarios(tipoOrganismo);
    $('#arbol' + tipoOrganismo).html("");
    $('#tab' + tipoOrganismo + ' a:first').tab('show');
    if (tipoOrganismo != 'OficinaOrigen') {
        $('#tab' + tipoOrganismo + ' a:last').hide();
    }
    $('#resultadosbusqueda' + tipoOrganismo).empty();
}

/* Función que asigna el valor seleccionado de la búsqueda al select correspondiente
 * y cierra el modal de la búsqueda */
function asignarOrganismo(codigo, denominacion, tipoOrganismo) {

    var idSelect = getIdSelect(tipoOrganismo);
    var idDenominacion = getIdDenominacion(tipoOrganismo);
    var idDenominacionExterna = getIdDenominacionExterna(tipoOrganismo);
    var anadir = true;
    var idModal = "#modalBuscador" + tipoOrganismo;
    /* Miramos si el organismo ya existe en el select, si existe lo seleccionamos,
     si no lo indicamos con la variable anadir para añadirlo posteriormente */
    $(idSelect + " option").each(function () {

        if ($(this).val() == codigo) {
            anadir = false;
            $(this).prop("selected", true);
        }

    });
    // Si no existe lo añadimos al select
    if (anadir) {
        var html = '';
        var selected = 'selected="selected"';
        html += '<option ' + selected + ' value="' + codigo + '">'
            + denominacion + '</option>';

        $(idSelect).append(html);
        $(idDenominacion).val(denominacion);
        if (idDenominacionExterna != null) {
            $(idDenominacionExterna).val(denominacion);
        }
    }
    $(idSelect).trigger("chosen:updated");
    $(idModal).modal('hide');
    limpiarFormularioBusqueda(tipoOrganismo);
}

/**
 * Añade una nueva fila con la Persona Juridica a la tabla de interesados
 * @param codigoDir3
 * @param denominacion
 * @param tipo
 * @param representante
 * @param tipoOrganismo
 */
function addAdministracionInteresadosModal(codigoDir3, denominacion, tipo, tipoOrganismo, idRegistroDetalle) {

    var idModal = "#modalBuscador" + tipoOrganismo;
    var denominacionCodificada = encodeURI(denominacion);

    $.ajax({
        url: urlAddOrganismoInteresado,
        type: 'GET',
        dataType: 'json',
        data: {codigoDir3: codigoDir3, denominacion: denominacionCodificada, idRegistroDetalle: idRegistroDetalle},
        contentType: 'application/json',

        success: function (result) {
            addOrganismoInteresadoHtml(codigoDir3, denominacion, tipo, idRegistroDetalle, false);
        }
    });

    $(idModal).modal('hide');

}

/**
 *
 * @param organismo
 * @param urlServidor
 * @param tipoOrganismo
 * @param idRegistroDetalle
 */
function mostrarArbol(organismo, urlServidor, tipoOrganismo, idRegistroDetalle) {

    $('#arbol' + tipoOrganismo).html("");
    var url = urlServidor + "/rest/organigrama";
    var html = '';
    $.ajax({
        async: false,
        crossDomain: true,
        url: url,
        type: 'GET',
        dataType: 'json',
        data: {codigo: organismo},
        success: function (result) {
            var denominacion = normalizarTexto(result.nombre);
            var nodoactual = result.id + ' - ' + result.nombre;
            var raizCodigo = result.raiz.split(" - ")[0];
            var raizDenominacion = normalizarTexto(result.raiz.split(" - ")[1]);
            var superiorCodigo = result.superior.split(" - ")[0];
            var superiorDenominacion = normalizarTexto(result.superior.split(" - ")[1]);
            if (raizCodigo != result.id) {
                html += '<ul>';
                if (tipoOrganismo == 'OrganismoInteresado') {
                    html += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"addAdministracionInteresadosModal('" + raizCodigo + "','" + raizDenominacion + "','Administración','" + tipoOrganismo + "','" + idRegistroDetalle + "')\">" + raizDenominacion + " - " + raizCodigo + "</span>";
                } else {
                    html += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"asignarOrganismo('" + raizCodigo + "','" + raizDenominacion + "','" + tipoOrganismo + "')\">" + raizDenominacion + " - " + raizCodigo + "</span>";
                }
            }
            if (raizCodigo != superiorCodigo) {
                html += '<ul>';
                if (tipoOrganismo == 'OrganismoInteresado') {
                    html += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"addAdministracionInteresadosModal('" + superiorCodigo + "','" + superiorDenominacion + "','Administración','" + tipoOrganismo + "','" + idRegistroDetalle + "')\">" + superiorDenominacion + " - " + superiorCodigo + "</span>";
                } else {
                    html += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"asignarOrganismo('" + superiorCodigo + "','" + superiorDenominacion + "','" + tipoOrganismo + "')\">" + superiorDenominacion + " - " + superiorCodigo + "</span>";
                }

            }
            html += '<ul>';
            html += '<li>';
            if (tipoOrganismo == 'OrganismoInteresado') {
                html += "<span class=\"badge-arbre btn-primary\" id=\"entidad\" onclick=\"addAdministracionInteresadosModal('" + result.id + "','" + denominacion + "','Administración','" + tipoOrganismo + "','" + idRegistroDetalle + "')\">" + result.nombre + " - " + result.id + "</span>";
            } else {
                html += "<span class=\"badge-arbre btn-primary\" id=\"entidad\" onclick=\"asignarOrganismo('" + result.id + "','" + denominacion + "','" + tipoOrganismo + "')\">" + result.nombre + " - " + result.id + "</span>";
            }

            //imprimir los hijos
            var hijoslen = result.hijos.length;

            if (hijoslen > 0) {
                html += pintarHijos(result.hijos, tipoOrganismo, idRegistroDetalle);
            }
            html += '</li>';
            html += '</ul>';
            html += '</li>';
            html += '</ul>';
            html += '</li>';
            html += '</ul>';
            $('#arbol' + tipoOrganismo).html(html);
        }

    });
    $('#tab' + tipoOrganismo + ' a:last').show();
    $('#tab' + tipoOrganismo + ' a:last').tab('show');

}


function pintarHijos(hijos, tipoOrganismo, idRegistroDetalle) {

    var htmlp = '';

    htmlp += '<ul>';
    for (var i = 0; i < hijos.length; i++) {
        var denominacion = normalizarTexto(hijos[i].nombre);
        if (tipoOrganismo == 'OrganismoInteresado') {
            htmlp += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"addAdministracionInteresadosModal('" + hijos[i].id + "','" + denominacion + "','Administración','" + tipoOrganismo + "','" + idRegistroDetalle + "')\">" + hijos[i].nombre + " - " + hijos[i].id + "</span>";
        } else {
            htmlp += "<li> <span class=\"badge-arbre btn-primary\" onclick=\"asignarOrganismo('" + hijos[i].id + "','" + denominacion + "','" + tipoOrganismo + "')\">" + hijos[i].nombre + " - " + hijos[i].id + "</span>";
        }
        if (hijos[i].hijos.length > 0) {
            htmlp += pintarHijos(hijos[i].hijos, tipoOrganismo, idRegistroDetalle);
        }
    }
    htmlp += '</ul>';

    return htmlp;
}
