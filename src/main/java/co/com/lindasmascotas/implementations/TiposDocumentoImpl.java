
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.TiposDocumentoJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposDocumento;
import co.com.lindasmascotas.services.TiposDocumentoSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposDocumentoImpl implements TiposDocumentoSvc {

    @Override
    public Response listarTiposDocumento() {
         Response res = new Response();
         TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());
        
         try {
             List<TiposDocumento> list = ctrl.findTiposDocumentoEntities();
             
             res.setStatus(true);
             res.setData(list);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
         
         return res;
    }

    @Override
    public Response crear(TiposDocumento td) {
        Response res = new Response();
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());

        try {
            
            td.setNombreTipo(td.getNombreTipo().toUpperCase());
            ctrl.create(td);
            
            res = listarTiposDocumento();
        } catch (Exception ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposDocumentoEntities());
        }
        return res;
    }

    @Override
    public Response editar(TiposDocumento td) {
        Response res = new Response();
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());
        TiposDocumento tipoDocActual = ctrl.findTiposDocumento(td.getIdTipoDoc());

        tipoDocActual.setNombreTipo(td.getNombreTipo());

        try {
            ctrl.edit(tipoDocActual);
          
            res = listarTiposDocumento();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposDocumentoEntities());
        } catch (Exception ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposDocumentoEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarTiposDocumento();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposDocumentoEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTiposDocumentoEntities());
        }

        return res;
    }
    
}
