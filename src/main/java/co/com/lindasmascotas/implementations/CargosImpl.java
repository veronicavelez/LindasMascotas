
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CargosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Cargos;
import co.com.lindasmascotas.services.CargoSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CargosImpl implements CargoSvc {

    @Override
    public Response listarCargos() {
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Cargos> list = ctrl.findCargosEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        }catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        
        return res;
    }

    @Override
    public Response crear(Cargos c) {
        Response res = new Response();
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(c);
            
            res = listarCargos();
        } catch (Exception ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCargosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Cargos c) {
        Response res = new Response();
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());
        Cargos cargoActual = ctrl.findCargos(c.getIdCargo());

        cargoActual.setNombreCargo(c.getNombreCargo());

        try {
            ctrl.edit(cargoActual);
            
            res = listarCargos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCargosEntities());
        } catch (Exception ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCargosEntities());
        }
        return res;

    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
         CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarCargos();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCargosEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCargosEntities());
        }

        return res;

    }
    
     
}
