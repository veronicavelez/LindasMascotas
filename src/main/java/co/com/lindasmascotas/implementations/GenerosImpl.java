package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.GenerosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Generos;
import co.com.lindasmascotas.services.GenerosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenerosImpl implements GenerosSvc {
    
    @Override
    public Response listarGeneros() {
        GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Generos> list = ctrl.findGenerosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

        return res;
    }

    @Override
    public Response crear(Generos g) {
        Response res = new Response();
       GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(g);
            
            res = listarGeneros();
        } catch (Exception ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findGenerosEntities());
        }

        return res;
    }
    

    @Override
    public Response editar(Generos g) {
        Response res = new Response();
         GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        Generos generoActual = ctrl.findGeneros(g.getIdGenero());

        generoActual.setNombreGenero(g.getNombreGenero());

        try {
            ctrl.edit(generoActual);
            
            res = listarGeneros();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findGenerosEntities());
        } catch (Exception ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findGenerosEntities());
        }

        return res;

    }


    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarGeneros();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findGenerosEntities());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findGenerosEntities());
        }

        return res;
    }
}
