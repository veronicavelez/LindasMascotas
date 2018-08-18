package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.PaisesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Paises;
import co.com.lindasmascotas.services.PaisesSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Veronica
 */
public class PaisesImpl implements PaisesSvc {

    @Override
    public Response listarPaises() {
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Paises> list = ctrl.findPaisesEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        } 
        return res;       
    }

    @Override
    public Response crear(Paises p) {
        Response res = new Response();
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(p);
            
            res = listarPaises();
        } catch (Exception ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPaisesEntities());
        }
        return res;
    }

    @Override
    public Response editar(Paises p) {
        Response res = new Response();
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());
        Paises paisActual = ctrl.findPaises(p.getIdPais());

        paisActual.setNombrePais(p.getNombrePais());

        try {
            ctrl.edit(paisActual);
            
            res = listarPaises();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPaisesEntities());
        } catch (Exception ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPaisesEntities());
        }
        
        return res;
    }

    @Override
    public Response eliminar(String id) {
        Response res = new Response();
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarPaises();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPaisesEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPaisesEntities());
        }

        return res;
    }

}
