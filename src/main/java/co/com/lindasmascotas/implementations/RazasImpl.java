
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.EspeciesJpaController;
import co.com.lindasmascotas.JPAcontrollers.RazasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.entities.Razas;
import co.com.lindasmascotas.services.RazasSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RazasImpl implements RazasSvc {
    
     @Override
    public Response listarRazas() {
         RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());
         Response res = new Response();

         
         try {
            List<Razas> list = ctrl.findRazasEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        } 
        return res;
    }
    
    @Override
     public Response crear(Razas r) {
        Response res = new Response();
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(r);
            
            res = listarRazas();
        } catch (Exception ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findRazasEntities());
        }

        return res;
    }
     
     
      @Override
      public Response editar(Razas r) {
        Response res = new Response();
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        Razas razaActual = ctrl.findRazas(r.getIdRaza());

        razaActual.setNombreRaza(r.getNombreRaza());
        
        try {
            ctrl.edit(razaActual);
            
            res = listarRazas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findRazasEntities());
        } catch (Exception ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findRazasEntities());
        }

        return res;

    }
    
    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
            
            res = listarRazas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findRazasEntities());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findRazasEntities());
        }

        return res;
        
        
    }    

    @Override
    public Response razasPorEspecie(Integer id) {
        Response res = new Response();
        EspeciesJpaController ctrlEspecie = new EspeciesJpaController(UPfactory.getFACTORY());
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());
        
        try {
            Especies e = ctrlEspecie.findEspecies(id);
            List<Razas> lista = ctrl.findRazasByEspecies(e);
            
            res.setStatus(true);
            res.setData(lista);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
       
        return res;
    }
    
    
}
