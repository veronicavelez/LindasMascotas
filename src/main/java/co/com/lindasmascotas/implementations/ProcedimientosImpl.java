
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ProcedimientosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.services.ProcedimientosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProcedimientosImpl implements ProcedimientosSvc {

    @Override
    public Response listarProcedimientos() {
        ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Procedimientos> list = ctrl.findProcedimientosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        return res;
    }

    @Override
    public Response crear(Procedimientos pr) {
        Response res = new Response();
        ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());

        try {
            pr.setNombreProcedimiento(pr.getNombreProcedimiento().toUpperCase());
            pr.setDescripcion(pr.getDescripcion().toUpperCase());
            
            ctrl.create(pr);
            
            res = listarProcedimientos();
        } catch (Exception ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProcedimientosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Procedimientos pr) {
        Response res = new Response();
       ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());

        Procedimientos procedActual = ctrl.findProcedimientos(pr.getIdProcedimiento());
        
        procedActual.setNombreProcedimiento(pr.getNombreProcedimiento());
        procedActual.setPeso(pr.getPeso());
        
        try {
            pr.setNombreProcedimiento(pr.getNombreProcedimiento().toUpperCase());
            pr.setDescripcion(pr.getDescripcion().toUpperCase());
            ctrl.edit(procedActual);
            
            res = listarProcedimientos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProcedimientosEntities());
        } catch (Exception ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findProcedimientosEntities());
        }
        
        return res;
    }

   
}
    
    

