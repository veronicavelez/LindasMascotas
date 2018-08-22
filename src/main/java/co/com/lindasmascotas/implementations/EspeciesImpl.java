
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.EspeciesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.services.EspeciesSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EspeciesImpl implements EspeciesSvc{

    @Override
    public Response listarEspecies() {
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
         try {
            List<Especies> list = ctrl.findEspeciesEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        } 
        return res;
    }

    @Override
    public Response crear(Especies e) {
        Response res = new Response();
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(e);
            
            res = listarEspecies();
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEspeciesEntities());
        }
        return res;
    }

    @Override
    public Response editar(Especies e) {
        Response res = new Response();
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());
        Especies especieActual = ctrl.findEspecies(e.getIdEspecie());

        especieActual.setNombreEspecie(e.getNombreEspecie());

        try {
            ctrl.edit(especieActual);
            
            res = listarEspecies();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEspeciesEntities());
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEspeciesEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarEspecies();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEspeciesEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEspeciesEntities());
        }

        return res;
    }
    
}
