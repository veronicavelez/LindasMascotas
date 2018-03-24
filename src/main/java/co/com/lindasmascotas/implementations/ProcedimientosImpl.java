
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ProcedimientosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Procedimientos;
import co.com.lindasmascotas.services.ProcedimientosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProcedimientosImpl implements ProcedimientosSvc {

    @Override
    public List<Procedimientos> listarProcedimientos() {
        ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());

        return ctrl.findProcedimientosEntities();
    }

    @Override
    public List<Procedimientos> crear(Procedimientos pr) {
        ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(pr);
        } catch (Exception ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarProcedimientos();

    }

    @Override
    public List<Procedimientos> editar(Procedimientos pr) {
       ProcedimientosJpaController ctrl = new ProcedimientosJpaController(UPfactory.getFACTORY());

        Procedimientos procedActual = ctrl.findProcedimientos(pr.getIdProcedimiento());
        
        procedActual.setNombreProcedimiento(pr.getNombreProcedimiento());
        
        try {
            ctrl.edit(procedActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProcedimientosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarProcedimientos();
    }

   
}
    
    

