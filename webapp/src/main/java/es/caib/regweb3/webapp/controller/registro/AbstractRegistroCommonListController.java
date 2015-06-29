package es.caib.regweb3.webapp.controller.registro;

import es.caib.regweb3.model.CatComunidadAutonoma;
import es.caib.regweb3.model.CatNivelAdministracion;
import es.caib.regweb3.model.Entidad;
import es.caib.regweb3.persistence.ejb.*;
import es.caib.regweb3.utils.RegwebConstantes;
import es.caib.regweb3.webapp.controller.BaseController;
import es.caib.regweb3.webapp.scan.ScannerManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * @author anadal
 *
 */
@Controller
public abstract class AbstractRegistroCommonListController extends BaseController {

  
    @EJB(mappedName = "regweb3/CatComunidadAutonomaEJB/local")
    public CatComunidadAutonomaLocal catComunidadAutonomaEjb;

    @EJB(mappedName = "regweb3/CatNivelAdministracionEJB/local")
    public CatNivelAdministracionLocal catNivelAdministracionEjb;

    @EJB(mappedName = "regweb3/CatProvinciaEJB/local")
    public CatProvinciaLocal catProvinciaEjb;

    @EJB(mappedName = "regweb3/PersonaEJB/local")
    public PersonaLocal personaEjb;

    @EJB(mappedName = "regweb3/ModeloReciboEJB/local")
    public ModeloReciboLocal modeloReciboEjb;

    @EJB(mappedName = "regweb3/TrazabilidadEJB/local")
    public TrazabilidadLocal trazabilidadEjb;

    @EJB(mappedName = "regweb3/LopdEJB/local")
    public LopdLocal lopdEjb;

    @EJB(mappedName = "regweb3/AnexoEJB/local")
    public AnexoLocal anexoEjb;

    @EJB(mappedName = "regweb3/CatPaisEJB/local")
    public CatPaisLocal catPaisEjb;

  
    @ModelAttribute("comunidadesAutonomas")
    public List<CatComunidadAutonoma> comunidadesAutonomas() throws Exception {
      return catComunidadAutonomaEjb.getAll();
    }

    @ModelAttribute("nivelesAdministracion")
    public List<CatNivelAdministracion> nivelesAdministracion() throws Exception {
      return catNivelAdministracionEjb.getAll();
    }


    @ModelAttribute("estados")
    public Long[] estados(HttpServletRequest request) throws Exception {
      if(getEntidadActiva(request).getSir()){
          return RegwebConstantes.ESTADOS_REGISTRO_SIR;
      }else {
          return RegwebConstantes.ESTADOS_REGISTRO;
      }
    }


    public void initAnexos(Entidad entidad, Model model, HttpServletRequest request, Long registroID) throws Exception {
    Integer tipusScan = 0;
    if (entidad.getTipoScan() != null && !"".equals(entidad.getTipoScan())) {
      tipusScan = Integer.parseInt(entidad.getTipoScan());
    }
    //      Integer tipusScan = 2;
    boolean teScan = ScannerManager.teScan(tipusScan);
    model.addAttribute("teScan", teScan);
    if (teScan) {

      model.addAttribute("iframe_anexos_height",
          AnexoController.BASE_IFRAME_HEIGHT + ScannerManager.getMinHeight(request, tipusScan, registroID));
    } else {
      model.addAttribute("iframe_anexos_height",
          AnexoController.BASE_IFRAME_HEIGHT + AnexoController.FILE_TAB_HEIGHT);
    }
    }
  

  
  
}