package es.caib.regweb3.webapp.view;

import es.caib.regweb3.utils.RegwebConstantes;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Fundació BIT.
 * User: jpernia
 * Date: 9/5/14
 */

public class LibroRegistroExcel extends AbstractExcelView {

    protected final Logger log = Logger.getLogger(getClass());

    /**
     * Retorna el mensaje traducido según el idioma del usuario
     * @param key
     * @return
     */
    protected String getMessage(String key){
        return I18NUtils.tradueix(key);
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Obtenemos mapas y arrays de valores
        Long tipo = (Long) model.get("tipo");
        String fechaInicio = (String) model.get("fechaInicio");
        String fechaFin = (String) model.get("fechaFin");
        Set<String> campos = (Set<String>) model.get("campos");
        ArrayList<ArrayList<String>> registrosLibro = (ArrayList<ArrayList<String>>) model.get("registrosLibro");

        HSSFSheet sheet = workbook.createSheet("REGWEB3");
        sheet.setFitToPage(true);

        //Estilo título
        HSSFCellStyle titulo;
        org.apache.poi.ss.usermodel.Font tituloFuente = workbook.createFont();
        tituloFuente.setFontHeightInPoints((short)18);
        tituloFuente.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        titulo = workbook.createCellStyle();
        titulo.setAlignment(CellStyle.ALIGN_CENTER);
        titulo.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titulo.setFont(tituloFuente);
        //Estilo parametros de busqueda
        HSSFCellStyle paramCerca;
        org.apache.poi.ss.usermodel.Font paramCercaFuente = workbook.createFont();
        paramCercaFuente.setFontHeightInPoints((short)12);
        paramCercaFuente.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        paramCerca = workbook.createCellStyle();
        paramCerca.setAlignment(CellStyle.ALIGN_CENTER);
        paramCerca.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        paramCerca.setFont(paramCercaFuente);


        //Estilo cabecera
        HSSFCellStyle cabecera;
        org.apache.poi.ss.usermodel.Font cabeceraFuente = workbook.createFont();
        cabeceraFuente.setFontHeightInPoints((short)10);
        cabeceraFuente.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        cabecera = workbook.createCellStyle();
        cabecera.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cabecera.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cabecera.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cabecera.setBorderBottom(CellStyle.BORDER_THIN);
        cabecera.setBorderTop(CellStyle.BORDER_THIN);
        cabecera.setBorderLeft(CellStyle.BORDER_THIN);
        cabecera.setBorderRight(CellStyle.BORDER_THIN);
        cabecera.setFont(cabeceraFuente);

        //Estilo tabla
        HSSFCellStyle fila;
        org.apache.poi.ss.usermodel.Font filaFuente = workbook.createFont();
        filaFuente.setFontHeightInPoints((short)8);
        fila = workbook.createCellStyle();
        fila.setBorderBottom(CellStyle.BORDER_THIN);
        fila.setBorderTop(CellStyle.BORDER_THIN);
        fila.setBorderLeft(CellStyle.BORDER_THIN);
        fila.setBorderRight(CellStyle.BORDER_THIN);
        fila.setFont(filaFuente);

        // Creamos las filas para el encabezado
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25);
        HSSFCell tittleCell = titleRow.createCell(0);
        HSSFRow tipusRow = sheet.createRow(2);
        tipusRow.setHeightInPoints(15);
        HSSFCell tipusCell = tipusRow.createCell(0);
        HSSFRow fechaInicioRow = sheet.createRow(3);
        fechaInicioRow.setHeightInPoints(15);
        HSSFCell fechaInicioCell = fechaInicioRow.createCell(0);
        HSSFRow fechaFinRow = sheet.createRow(4);
        fechaFinRow.setHeightInPoints(15);
        HSSFCell fechaFinCell = fechaFinRow.createCell(0);

        String tipoRegistro = null;
        if(tipo.equals(RegwebConstantes.INFORME_TIPO_REGISTRO_ENTRADA)){
            tipoRegistro = getMessage("informe.entrada");
        }else if(tipo.equals(RegwebConstantes.INFORME_TIPO_REGISTRO_SALIDA)){
            tipoRegistro = getMessage("informe.salida");
        }

        //Título
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$G$1"));
        tittleCell.setCellValue(getMessage("informe.llibreRegistres.informe"));
        tittleCell.setCellStyle(titulo);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$G$2"));
        tipusCell.setCellValue(getMessage("informe.tipo") + ": " + tipoRegistro);
        tipusCell.setCellStyle(paramCerca);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$3:$G$3"));
        fechaInicioCell.setCellValue(getMessage("informe.fechaInicio") + ": " + fechaInicio);
        fechaInicioCell.setCellStyle(paramCerca);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$4:$G$4"));
        fechaFinCell.setCellValue(getMessage("informe.fechaFin") + ": " + fechaFin);
        fechaFinCell.setCellStyle(paramCerca);
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$G$5"));


        //////////////Registros////////////////
        // Creamos la fila para la cabecera
        int rowNum = 6;
        HSSFRow header = sheet.createRow(rowNum++);
        header.setHeightInPoints(15);

        // Columnas
        String[] columnas = new String[campos.size()];
        int h = 0;

        if(campos.size()!=0){

            for (String valorCamp : campos) {
                if (valorCamp.equals("codAs")) {
                    columnas[h] = getMessage("informe.codigoAsunto");
                    h = h + 1;
                }else if (valorCamp.equals("anyRe")) {
                    columnas[h] = getMessage("informe.anoRegistro");
                    h = h + 1;
                }else if (valorCamp.equals("estat")) {
                    columnas[h] = getMessage("informe.estado");
                    h = h + 1;
                }else if (valorCamp.equals("exped")) {
                    columnas[h] = getMessage("informe.expediente");
                    h = h + 1;
                }else if (valorCamp.equals("extra")) {
                    columnas[h] = getMessage("informe.extracto");
                    h = h + 1;
                }else if (valorCamp.equals("datOr")) {
                    columnas[h] = getMessage("informe.fechaOrigen");
                    h = h + 1;
                }else if (valorCamp.equals("numRe")) {
                    columnas[h] = getMessage("informe.numeroRegistro");
                    h = h + 1;
                }else if (valorCamp.equals("ofici")) {
                    columnas[h] = getMessage("informe.oficina");
                    h = h + 1;
                }else if (valorCamp.equals("tipAs")) {
                    columnas[h] = getMessage("informe.tipoAsunto");
                    h = h + 1;
                }else if (valorCamp.equals("obser")) {
                    columnas[h] = getMessage("informe.observaciones");
                    h = h + 1;
                }else if (valorCamp.equals("llibr")) {
                    columnas[h] = getMessage("informe.libro");
                    h = h + 1;
                }else if (valorCamp.equals("data")) {
                    columnas[h] = getMessage("informe.fechaRegistro");
                    h = h + 1;
                }else if (valorCamp.equals("docFi")) {
                    columnas[h] = getMessage("informe.documentacionFisica");
                    h = h + 1;
                }else if (valorCamp.equals("orgDe")) {
                    if (tipo.equals(RegwebConstantes.INFORME_TIPO_REGISTRO_ENTRADA)) {
                        columnas[h] = getMessage("informe.organismoDestino");
                        h = h + 1;
                    }
                    if (tipo.equals(RegwebConstantes.INFORME_TIPO_REGISTRO_SALIDA)) {
                        columnas[h] = getMessage("informe.organismoOrigen");
                        h = h + 1;
                    }
                }else if (valorCamp.equals("idiom")) {
                    columnas[h] = getMessage("informe.idioma");
                    h = h + 1;
                }else if (valorCamp.equals("refEx")) {
                    columnas[h] = getMessage("informe.referenciaExterna");
                    h = h + 1;
                }else if (valorCamp.equals("trans")) {
                    columnas[h] = getMessage("informe.transporte");
                    h = h + 1;
                }else if (valorCamp.equals("numTr")) {
                    columnas[h] = getMessage("informe.numeroTransporte");
                    h = h + 1;
                }else if (valorCamp.equals("orgOr")) {
                    columnas[h] = getMessage("informe.oficinaOrigen");
                    h = h + 1;
                }else if (valorCamp.equals("numOr")) {
                    columnas[h] = getMessage("informe.numeroRegistroOrigen");
                    h = h + 1;
                }else if (valorCamp.equals("nomIn")) {
                    columnas[h] = getMessage("informe.interesados");
                    h = h + 1;
                }
            }
        }

        for (int i = 0; i < columnas.length; i++){
            HSSFCell columna = header.createCell(i);
            columna.setCellValue(columnas[i]);
            columna.setCellStyle(cabecera);
        }

        // Filas
        if(registrosLibro.size()>0){
            for (ArrayList<String> aRegistrosLibro : registrosLibro) {
                HSSFRow row = sheet.createRow(rowNum++);
                for (int g = 0; g < aRegistrosLibro.size(); g++) {
                    row.createCell(g).setCellValue(aRegistrosLibro.get(g));
                }
                // Aplicamos el estilo a las celdas
                for (int g = 0; g < aRegistrosLibro.size(); g++) {
                    row.getCell(g).setCellStyle(fila);
                }
            }
        } else{
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(getMessage("informe.registros.vacio"));
            row.getCell(0).setCellStyle(fila);
        }


        // Ajustamos el ancho de cada columna a su contenido
        for (int i = 0; i < columnas.length; i++) {
            sheet.autoSizeColumn(i);
        }

        String nombreFichero = getMessage("informe.nombreFichero.libroRegistro") + tipoRegistro +".xls";

        // Cabeceras Response
        response.setHeader("Content-Disposition","attachment; filename="+nombreFichero);
        response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
    }

}