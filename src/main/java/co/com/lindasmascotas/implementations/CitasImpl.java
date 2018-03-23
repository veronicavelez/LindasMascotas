
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CitasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.services.CitasSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CitasImpl implements CitasSvc {

    @Override
    public List<Citas> listarCitas() {
         CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        return ctrl.findCitasEntities();
    }

    @Override
    public List<Citas> crear(Citas c) {
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(c);
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCitas();
    }

    @Override
    public List<Citas> editar(Citas c) {
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        Citas citaActual = ctrl.findCitas(c.getIdCita());

        citaActual.setFechaCita(c.getFechaCita());

        try {
            ctrl.edit(citaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCitas();
    }

    @Override
    public List<Citas> Cancelar(Citas c) {
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        Citas citaActual = ctrl.findCitas(c.getIdCita());

        citaActual.setFechaCita(c.getFechaCita());

        try {
            ctrl.edit(citaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCitas();
    }
    
}
