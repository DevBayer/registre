/*
 * OficioServlet.java
 *
 * Created on 17 de agosto de 2009, 10:32
 */

package es.caib.regweb.webapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import es.caib.regweb.logic.helper.ParametrosOficioRemision;
import es.caib.regweb.logic.helper.ParametrosRegistroSalida;
import es.caib.regweb.logic.interfaces.ValoresFacade;
import es.caib.regweb.logic.util.ValoresFacadeUtil;
import es.caib.regweb.logic.interfaces.RegistroSalidaFacade;
import es.caib.regweb.logic.util.RegistroSalidaFacadeUtil;
import es.caib.regweb.logic.interfaces.OficioRemisionFacade;
import es.caib.regweb.logic.util.OficioRemisionFacadeUtil;
import es.caib.regweb.logic.interfaces.ModeloOficioFacade;
import es.caib.regweb.logic.util.ModeloOficioFacadeUtil;
import es.caib.regweb.model.ModeloOficio;

public class OficioServlet extends HttpServlet {

	/**
	 * @exception ServletException
	 */
	public void init() throws ServletException {
	}

	/**
	 * @param req
	 * @param resp
	 * @exception ServletException
	 * @exception IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @param req
	 * @param resp
	 * @exception ServletException
	 * @exception IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		OutputStream outstream = resp.getOutputStream();

		String oficina = req.getParameter("oficina");
		String numeroOficio = req.getParameter("oficio");
		String anyo = req.getParameter("any");
		String sololectura = req.getParameter("mode");

        req.setCharacterEncoding("UTF-8");

		byte[] file = null;

		try {
			// Cercam el EJB d'accés al repositori de models d'oficis
            ModeloOficioFacade mof = ModeloOficioFacadeUtil.getHome().create();				
            ValoresFacade valores = ValoresFacadeUtil.getHome().create();
            OficioRemisionFacade of = OficioRemisionFacadeUtil.getHome().create();				
            RegistroSalidaFacade rs = RegistroSalidaFacadeUtil.getHome().create();				

            ParametrosOficioRemision oficio = new ParametrosOficioRemision();
            ParametrosRegistroSalida regsal = new ParametrosRegistroSalida();

			String nom = req.getParameter("modelo");
			ModeloOficio mod = mof.leer(nom);

			oficio.setAnoOficio(anyo);
			oficio.setOficinaOficio(oficina);
			oficio.setNumeroOficio(numeroOficio);
			oficio = of.leer(oficio);

			String usuario = req.getRemoteUser();
			regsal.fijaUsuario(usuario);
			regsal.setAnoSalida(anyo);
			regsal.setoficina(oficio.getOficinaSalida());
			regsal.setNumeroSalida(oficio.getNumeroSalida());
			regsal = rs.leer(regsal);

			file = mod.getDatos();
			// String ctyp = mod.getContentType();
			// resp.setContentType( ctyp );

			InputStream is = new ByteArrayInputStream(file);
			java.util.Hashtable ht = new java.util.Hashtable();

			String nom_oficina = valores.recuperaDescripcionOficina(oficina);

			if (nom_oficina != null) ht.put("(nom_oficina_ofici)", toCp1252(nom_oficina));
			if (oficina != null) ht.put("(numero_oficina_ofici)", toCp1252(oficina));
			if (oficio.getNumeroOficio() != null) ht.put("(numero_ofici)", oficio.getNumeroOficio());
			if (oficio.getAnoOficio() != null) ht.put("(any_ofici)", oficio.getAnoOficio());
			if (oficio.getFechaOficio() != null) ht.put("(data_ofici)", oficio.getFechaOficio());
			if (nom_oficina != null) ht.put("(nom_oficina_sortida)", toCp1252(nom_oficina));
			if (oficina != null) ht.put("(numero_oficina_sortida)", toCp1252(oficina));
			if (oficio.getNumeroSalida() != null) ht.put("(numero_sortida)", oficio.getNumeroSalida());
			if (oficio.getAnoSalida() != null) ht.put("(any_sortida)", oficio.getAnoSalida());
			if (oficio.getFechaOficio() != null) ht.put("(data_sortida)", oficio.getFechaOficio());
			if (oficio.getFechaOficio() != null) ht.put("(data)", oficio.getFechaOficio());
			if (oficio.getDescripcion() != null) ht.put("(contingut)", toCp1252(oficio.getDescripcion()).replaceAll("[;]", "\\\\par"));
			if (regsal.getComentario() != null) ht.put("(extracte)", toCp1252(regsal.getComentario()));
			if (regsal.getDescripcionDestinatario() != null) ht.put("(nom_organisme_destinatari)", toCp1252(regsal.getDescripcionDestinatario()));

			if (sololectura != null && sololectura.equals("S")) {
				ht.put("(read_only)", getISOBytes("\\annotprot\\readprot\\enforceprot1\\protlevel3\\readonlyrecommended "));
			}

			CombineStream cs = new CombineStream(is, ht);
			resp.setHeader("Content-Disposition", "attachment; filename=ofici_remisio_" + numeroOficio + "-" + anyo + ".rtf");
			// resp.setContentType("application/rtf");
			resp.setContentType("application/x-download");
			// resp.addHeader("Cache-Control ","No-cache; No-store; Must-revalidate; Proxy-revalidate");
			resp.setHeader("Pragma", "public");
			resp.setHeader("Cache-control", "must-revalidate");
			int l = 0;
			while ((l = cs.read()) != -1) {
				outstream.write(l);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (outstream != null) {
				try {
					outstream.close();
				} catch (IOException e) {
					}
				outstream = null;
			}
		}

	}

	private String toCp1252(String texto) {
		if (texto == null)
			return null;

		final String conversion[][] = { { "80", "\\u20AC" },
				{ "82", "\\u201A" }, { "83", "\\u0192" }, { "84", "\\u201E" },
				{ "85", "\\u2026" }, { "86", "\\u2020" }, { "87", "\\u2021" },
				{ "88", "\\u02C6" }, { "89", "\\u2030" }, { "8A", "\\u0160" },
				{ "8B", "\\u2039" }, { "8C", "\\u0152" }, { "8E", "\\u017D" },
				{ "91", "\\u2018" }, { "92", "\\u2019" }, { "93", "\\u201C" },
				{ "94", "\\u201D" }, { "95", "\\u2022" }, { "96", "\\u2013" },
				{ "97", "\\u2014" }, { "98", "\\u02DC" }, { "99", "\\u2122" },
				{ "9A", "\\u0161" }, { "9B", "\\u203A" }, { "9C", "\\u0153" },
				{ "9E", "\\u017E" }, { "9F", "\\u0178" }, { "A0", "\\u00A0" },
				{ "A1", "\\u00A1" }, { "A2", "\\u00A2" }, { "A3", "\\u00A3" },
				{ "A4", "\\u00A4" }, { "A5", "\\u00A5" }, { "A6", "\\u00A6" },
				{ "A7", "\\u00A7" }, { "A8", "\\u00A8" }, { "A9", "\\u00A9" },
				{ "AA", "\\u00AA" }, { "AB", "\\u00AB" }, { "AC", "\\u00AC" },
				{ "AD", "\\u00AD" }, { "AE", "\\u00AE" }, { "AF", "\\u00AF" },
				{ "B0", "\\u00B0" }, { "B1", "\\u00B1" }, { "B2", "\\u00B2" },
				{ "B3", "\\u00B3" }, { "B4", "\\u00B4" }, { "B5", "\\u00B5" },
				{ "B6", "\\u00B6" }, { "B7", "\\u00B7" }, { "B8", "\\u00B8" },
				{ "B9", "\\u00B9" }, { "BA", "\\u00BA" }, { "BB", "\\u00BB" },
				{ "BC", "\\u00BC" }, { "BD", "\\u00BD" }, { "BE", "\\u00BE" },
				{ "BF", "\\u00BF" }, { "C0", "\\u00C0" }, { "C1", "\\u00C1" },
				{ "C2", "\\u00C2" }, { "C3", "\\u00C3" }, { "C4", "\\u00C4" },
				{ "C5", "\\u00C5" }, { "C6", "\\u00C6" }, { "C7", "\\u00C7" },
				{ "C8", "\\u00C8" }, { "C9", "\\u00C9" }, { "CA", "\\u00CA" },
				{ "CB", "\\u00CB" }, { "CC", "\\u00CC" }, { "CD", "\\u00CD" },
				{ "CE", "\\u00CE" }, { "CF", "\\u00CF" }, { "D0", "\\u00D0" },
				{ "D1", "\\u00D1" }, { "D2", "\\u00D2" }, { "D3", "\\u00D3" },
				{ "D4", "\\u00D4" }, { "D5", "\\u00D5" }, { "D6", "\\u00D6" },
				{ "D7", "\\u00D7" }, { "D8", "\\u00D8" }, { "D9", "\\u00D9" },
				{ "DA", "\\u00DA" }, { "DB", "\\u00DB" }, { "DC", "\\u00DC" },
				{ "DD", "\\u00DD" }, { "DE", "\\u00DE" }, { "DF", "\\u00DF" },
				{ "E0", "\\u00E0" }, { "E1", "\\u00E1" }, { "E2", "\\u00E2" },
				{ "E3", "\\u00E3" }, { "E4", "\\u00E4" }, { "E5", "\\u00E5" },
				{ "E6", "\\u00E6" }, { "E7", "\\u00E7" }, { "E8", "\\u00E8" },
				{ "E9", "\\u00E9" }, { "EA", "\\u00EA" }, { "EB", "\\u00EB" },
				{ "EC", "\\u00EC" }, { "ED", "\\u00ED" }, { "EE", "\\u00EE" },
				{ "EF", "\\u00EF" }, { "F0", "\\u00F0" }, { "F1", "\\u00F1" },
				{ "F2", "\\u00F2" }, { "F3", "\\u00F3" }, { "F4", "\\u00F4" },
				{ "F5", "\\u00F5" }, { "F6", "\\u00F6" }, { "F7", "\\u00F7" },
				{ "F8", "\\u00F8" }, { "F9", "\\u00F9" }, { "FA", "\\u00FA" },
				{ "FB", "\\u00FB" }, { "FC", "\\u00FC" }, { "FD", "\\u00FD" },
				{ "FE", "\\u00FE" }, { "FF", "\\u00FF" } };

		String temp = texto;
		for (int i = 0; i < conversion.length; i++) {
			temp = temp.replaceAll(conversion[i][1], "\\\\'" + conversion[i][0]);
		}
		return temp;
	}

	/**
	 * Converts a <CODE>String</CODE> into a <CODE>Byte</CODE> array according
	 * to the ISO-8859-1 codepage.
	 * 
	 * @param text
	 *            the text to be converted
	 * @return the conversion result
	 */

	public static final byte[] getISOBytes(String text) {
		if (text == null)
			return null;
		int len = text.length();
		byte b[] = new byte[len];
		for (int k = 0; k < len; ++k)
			b[k] = (byte) text.charAt(k);
		return b;
	}

}