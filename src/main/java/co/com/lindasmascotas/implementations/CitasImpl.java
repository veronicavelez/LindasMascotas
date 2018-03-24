
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CitasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.services.CitasSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.internal.helper.Helper;


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
        Calendar fechaCita = Calendar.getInstance();
        fechaCita.setTime(citaActual.getFechaCita());
        boolean res = validarHoraCita(fechaCita);
        
        try {
            
            ctrl.edit(citaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCitas();
    }

    private boolean validarHoraCita(Calendar fechaCita) {        
        Calendar fechaActual = Calendar.getInstance();
        
        if (fechaActual.equals(fechaCita)){
            
            if ((fechaCita.get(Calendar.HOUR) - fechaActual.get(Calendar.HOUR)) > 4) {
                return true;
            }else{
                return false;
            }
        
        } else {
            return true;
        
        }
    }
    
}
