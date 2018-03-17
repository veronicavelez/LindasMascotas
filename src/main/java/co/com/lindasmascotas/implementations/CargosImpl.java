
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CargosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Cargos;
import co.com.lindasmascotas.services.CargoSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CargosImpl implements CargoSvc {

    @Override
    public List<Cargos> listarCargos() {
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());

        return ctrl.findCargosEntities();
    }

    @Override
    public List<Cargos> crear(Cargos c) {
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(c);
        } catch (Exception ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarCargos();
    }

    @Override
    public List<Cargos> editar(Cargos c) {
        CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());
        Cargos cargoActual = ctrl.findCargos(c.getIdCargo());

        cargoActual.setNombreCargo(c.getNombreCargo());

        try {
            ctrl.edit(cargoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarCargos();

    }

    @Override
    public List<Cargos> eliminar(Cargos c) {
         CargosJpaController ctrl = new CargosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(c.getIdCargo());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CargosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCargos();

    }
    
     
}
