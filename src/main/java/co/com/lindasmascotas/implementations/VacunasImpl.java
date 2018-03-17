
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.VacunasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Vacunas;
import co.com.lindasmascotas.services.VacunasSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VacunasImpl implements VacunasSvc {

    @Override
    public List<Vacunas> listarVacunas() {
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());

        return ctrl.findVacunasEntities();
    }

    @Override
    public List<Vacunas> crear(Vacunas v) {
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.create(v);
        } catch (Exception ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarVacunas();
    }

    @Override
    public List<Vacunas> editar(Vacunas v) {
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
        
        Vacunas vacunaActual = ctrl.findVacunas(v.getIdVacuna());

        vacunaActual.setNombreVacuna(v.getNombreVacuna());

        try {
            ctrl.edit(vacunaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarVacunas();
    }

    @Override
    public List<Vacunas> eliminar(Vacunas v) {
         VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(v.getIdVacuna());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarVacunas();
    }
    
}
