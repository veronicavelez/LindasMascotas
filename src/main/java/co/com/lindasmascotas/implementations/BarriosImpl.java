
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.BarriosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Barrios;
import co.com.lindasmascotas.services.BarriosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BarriosImpl implements BarriosSvc {
    
    @Override
    public Response listarBarrios() {
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Barrios> list = ctrl.findBarriosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        return res;

    }
    
    @Override
     public Response crear(Barrios b) {
         Response res = new Response();
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        try {
            b.setNombreBarrio(b.getNombreBarrio().toUpperCase());
            
            ctrl.create(b);
            
            res = listarBarrios();
        } catch (Exception ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findBarriosEntities());
        }

        return res;
    }
     
     
      @Override
      public Response editar(Barrios b) {
        Response res = new Response();
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());
        Barrios barrioActual = ctrl.findBarrios(b.getIdBarrio());

        barrioActual.setNombreBarrio(b.getNombreBarrio());

        try {
            ctrl.edit(barrioActual);
            
            res = listarBarrios();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findBarriosEntities());
        } catch (Exception ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findBarriosEntities());
        }

        return res;

    }
    
    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarBarrios();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findBarriosEntities());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findBarriosEntities());
        }

        return res;

    }
    
}
