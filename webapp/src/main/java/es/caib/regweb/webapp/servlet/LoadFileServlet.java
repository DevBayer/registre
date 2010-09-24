/*
 * LoadFileServlet.java
 *
 * Created on 16 de agosto de 2009, 10:32
 */

package es.caib.regweb.webapp.servlet;


import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import es.caib.regweb.logic.interfaces.ModeloOficioFacade;
import es.caib.regweb.logic.util.ModeloOficioFacadeUtil;
import es.caib.regweb.model.ModeloOficio;

/**
 * @created 18 / Enero / 2007
 * @web.servlet name="LoadFileServlet"
 *    description="Carga fichero"
 * @web.servlet-mapping url-pattern="/app/LoadFileServlet"
 */
public class LoadFileServlet extends HttpServlet
{

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
   public void doGet( HttpServletRequest req, HttpServletResponse resp )
       throws ServletException, IOException {
      doPost( req, resp );
   }

   /**
    * @param req
    * @param resp
    * @exception ServletException
    * @exception IOException
    */
   public void doPost( HttpServletRequest req, HttpServletResponse resp )
       throws ServletException, IOException {

      byte[] file = null;

        req.setCharacterEncoding("UTF-8");
 
		try {		
		//Cercam el EJB d'accés al repositori de models d'oficis
        ModeloOficioFacade mof = ModeloOficioFacadeUtil.getHome().create();				

    	    
         String nom = req.getParameter("nom");
         
         ModeloOficio mod = mof.leer(nom);
         
         file = mod.getDatos();
         //String ctyp = mod.getContentType();
         //resp.setContentType( ctyp );

			resp.setHeader("Content-Disposition", "attachment; filename="+nom.replaceAll(" ", "")+".rtf");
            //resp.setContentType("application/rtf");
            resp.setContentType("application/x-download");
			//resp.addHeader("Cache-Control ","No-cache; No-store; Must-revalidate; Proxy-revalidate");
			resp.setHeader( "Pragma", "public" );
			resp.setHeader("Cache-control", "must-revalidate");

         //if (req.getParameter("download")!=null) 
//         if (ctyp.indexOf("image")==-1 && ctyp.indexOf("pdf")==-1)
//            resp.setHeader( "Content-Disposition", "attachment; filename=" + nom );
//         
         ServletOutputStream sos = resp.getOutputStream();
         sos.write(file);
         sos.flush();
         sos.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


   }
}