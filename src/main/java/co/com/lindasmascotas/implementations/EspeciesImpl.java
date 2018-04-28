
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.EspeciesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.services.EspeciesSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EspeciesImpl implements EspeciesSvc{

    @Override
    public List<Especies> listarEspecies() {
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());

        return ctrl.findEspeciesEntities();
    }

    @Override
    public List<Especies> crear(Especies e) {
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(e);
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarEspecies();
    }

    @Override
    public List<Especies> editar(Especies e) {
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());
        Especies especieActual = ctrl.findEspecies(e.getIdEspecie());

        especieActual.setNombreEspecie(e.getNombreEspecie());

        try {
            ctrl.edit(especieActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarEspecies();
    }

    @Override
    public List<Especies> eliminar(Integer id) {
        EspeciesJpaController ctrl = new EspeciesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarEspecies();
    }
    
}
