
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.TiposContratoJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposContrato;
import co.com.lindasmascotas.services.TiposContratoSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposContratoImpl implements TiposContratoSvc {

    @Override
    public Response listarTiposContrato() {
         TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());
         Response res = new Response();
         
         try {
             
             List<TiposContrato> list = ctrl.findTiposContratoEntities();
             
             res.setStatus(true);
             res.setData(list);
         } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
         }
         

        return res;
    }
    

    @Override
    public Response crear(TiposContrato tc) {
        Response res = new Response();
          TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());

        try {
            tc.setNombreContrato(tc.getNombreContrato().toUpperCase());
            ctrl.create(tc);
            res = listarTiposContrato();
        } catch (Exception ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposContratoEntities());
        }
        return res;
    }
    

    @Override
    public Response editar(TiposContrato tc) {
        Response res = new Response();
        TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());
        
        TiposContrato tiposcontratoActual = ctrl.findTiposContrato(tc.getIdTipoContrato());

        tiposcontratoActual.setNombreContrato(tc.getNombreContrato());

        try {
            tc.setNombreContrato(tc.getNombreContrato().toUpperCase());
            ctrl.edit(tiposcontratoActual);
            
            res = listarTiposContrato();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposContratoEntities());
        } catch (Exception ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposContratoEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
          TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarTiposContrato();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposContratoEntities());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposContratoEntities());
        }

        return res;

    }
}
