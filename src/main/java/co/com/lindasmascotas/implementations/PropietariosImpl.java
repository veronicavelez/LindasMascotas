
package co.com.lindasmascotas.implementations;


import co.com.lindasmascotas.JPAcontrollers.PropietariosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.services.PropietariosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropietariosImpl implements PropietariosSvc{

    @Override
    public List<Propietarios> listarPropietarios() {
    PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());

     return ctrl.findPropietariosEntities();
    }

    @Override
    public List<Propietarios> crear(Propietarios p) {

    PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(p);
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPropietarios();
    }

    @Override
    public List<Propietarios> editar(Propietarios p) {
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY()); 
        Propietarios propietarioActual = ctrl.findPropietarios(p.getIdPropietario());
        
        propietarioActual.setNombrePropietario(p.getNombrePropietario());
        
        try {
            ctrl.edit(p);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarPropietarios();
    }

    /*Pendiente validar */
    @Override
    public List<Propietarios> eliminar(Propietarios p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    
}
