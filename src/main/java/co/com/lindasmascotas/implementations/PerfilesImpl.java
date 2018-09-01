
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.PerfilesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Perfiles;
import co.com.lindasmascotas.services.PerfilesSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PerfilesImpl implements PerfilesSvc {

    @Override
    public Response listarPerfiles() {
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Perfiles> list = ctrl.findPerfilesEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

        return res;
    }

    @Override
    public Response crear(Perfiles p) {
        Response res = new Response();
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());

        try {
            
            p.setNombrePerfil(p.getNombrePerfil().toUpperCase());
            
            ctrl.create(p);
            
            res = listarPerfiles();
        } catch (Exception ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPerfilesEntities());
        }
        return res;
    }

    @Override
    public Response editar(Perfiles p) {
        Response res = new Response();
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());
        Perfiles perfilActual = ctrl.findPerfiles(p.getIdPerfil());

        perfilActual.setNombrePerfil(p.getNombrePerfil());

        try {
            ctrl.edit(perfilActual);
            
            res = listarPerfiles();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPerfilesEntities());
        } catch (Exception ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPerfilesEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarPerfiles();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPerfilesEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPerfilesEntities());
        }

        return res;
    }
    
}
