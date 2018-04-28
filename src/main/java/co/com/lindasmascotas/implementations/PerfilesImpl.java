
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.PerfilesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Perfiles;
import co.com.lindasmascotas.services.PerfilesSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PerfilesImpl implements PerfilesSvc {

    @Override
    public List<Perfiles> listarPerfiles() {
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());

        return ctrl.findPerfilesEntities();
    }

    @Override
    public List<Perfiles> crear(Perfiles p) {
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(p);
        } catch (Exception ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPerfiles();
    }

    @Override
    public List<Perfiles> editar(Perfiles p) {
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());
        Perfiles perfilActual = ctrl.findPerfiles(p.getIdPerfil());

        perfilActual.setNombrePerfil(p.getNombrePerfil());

        try {
            ctrl.edit(perfilActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPerfiles();
    }

    @Override
    public List<Perfiles> eliminar(Integer id) {
        PerfilesJpaController ctrl = new PerfilesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarPerfiles();
    }
    
}
