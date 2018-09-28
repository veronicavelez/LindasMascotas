
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.SexosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Sexos;
import co.com.lindasmascotas.services.SexosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SexosImpl implements SexosSvc{

    @Override
    public Response listarSexos() {
        SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            
            List<Sexos> list = ctrl.findSexosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

        return res;
    }

    @Override
    public Response crear(Sexos s) {
        Response res = new Response();
         SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());

        try {
            
            s.setNombreSexo(s.getNombreSexo().toUpperCase());
            
            ctrl.create(s);
            
            res = listarSexos();
        } catch (Exception ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findSexosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Sexos s) {
        Response res = new Response();
        SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());
        Sexos sexoActual = ctrl.findSexos(s.getIdSexo());

        sexoActual.setNombreSexo(s.getNombreSexo());

        try {
            
            s.setNombreSexo(s.getNombreSexo().toUpperCase());
            
            ctrl.edit(sexoActual);
            
            res = listarSexos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findSexosEntities());
        } catch (Exception ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findSexosEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
         SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarSexos();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findSexosEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findSexosEntities());
        }

        return res;
    }
    
}
