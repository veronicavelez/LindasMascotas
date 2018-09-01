
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.TiposSangreJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposSangre;
import co.com.lindasmascotas.services.TiposSangreSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposSangreImpl implements TiposSangreSvc {

    @Override
    public Response listarTiposSangre() {
           TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());
           Response res = new Response();
           
           try {
               List<TiposSangre> list = ctrl.findTiposSangreEntities();
               
               res.setStatus(true);
               res.setData(list);
           } catch (Exception e){
               res.setStatus(false);
               res.setMessage("Ha ocurrido un error, intente más tarde.");
           }

        return res;
    }
    
    
    @Override
    public Response crear(TiposSangre ts) {
        Response res = new Response();
          TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

        try {
            
            ts.setNombreTipoSangre(ts.getNombreTipoSangre().toUpperCase());
            ctrl.create(ts);
            
            res = listarTiposSangre();
        } catch (Exception ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposSangreEntities());
        }

        return res;
    }
    

    @Override
    public Response editar(TiposSangre ts){
        Response res = new Response();
         TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

          TiposSangre tiposangreActual = ctrl.findTiposSangre(ts.getIdTipoSangre());

        tiposangreActual.setNombreTipoSangre(ts.getNombreTipoSangre());

        try {
            ctrl.edit(tiposangreActual);
            
            res = listarTiposSangre();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposSangreEntities());
        } catch (Exception ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposSangreEntities());
        }

        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarTiposSangre();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposSangreEntities());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposSangreEntities());
        }

        return res;
        
    }
    
}
