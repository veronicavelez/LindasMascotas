
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.BarriosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Barrios;
import co.com.lindasmascotas.services.BarriosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BarriosImpl implements BarriosSvc {
    
    @Override
    public List<Barrios> listarBarrios() {
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        return ctrl.findBarriosEntities();
    }
    
    @Override
     public List<Barrios> crear(Barrios b) {
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(b);
        } catch (Exception ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarBarrios();
    }
     
     
      @Override
      public List<Barrios> editar(Barrios b) {
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        Barrios barrioActual = ctrl.findBarrios(b.getIdBarrio());

        barrioActual.setNombreBarrio(b.getNombreBarrio());

        try {
            ctrl.edit(barrioActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarBarrios();

    }
    
    @Override
    public List<Barrios> eliminar(Integer id) {
        BarriosJpaController ctrl = new BarriosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(BarriosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarBarrios();

    }
    
}
