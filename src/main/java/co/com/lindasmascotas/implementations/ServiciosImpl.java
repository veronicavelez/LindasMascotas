
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ServiciosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.services.ServiciosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiciosImpl implements ServiciosSvc {

    @Override
    public List<Servicios> listarServicios() {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        
        return ctrl.findServiciosEntities();
    }
 
    @Override
    public List<Servicios> crear(Servicios s) {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(s);
        } catch (Exception ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarServicios();
    }

    @Override
    public List<Servicios> editar(Servicios s) {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        
        Servicios servicioActual = ctrl.findServicios(s.getIdServicio());
        
        servicioActual.setNombreServicio(s.getNombreServicio());
        servicioActual.setDescripcionServicio(s.getDescripcionServicio());
        servicioActual.setPrecioServicio(s.getPrecioServicio());
       
        
        try {
            ctrl.edit(servicioActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarServicios();
    }

    @Override
    public List<Servicios> eliminar(Servicios s) {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(s.getIdServicio());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarServicios();
    }
    
    
}
