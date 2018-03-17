package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.PaisesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Paises;
import co.com.lindasmascotas.services.PaisesSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Veronica
 */
public class PaisesImpl implements PaisesSvc {

    @Override
    public List<Paises> listarPaises() {
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());

        return ctrl.findPaisesEntities();
    }

    @Override
    public List<Paises> crear(Paises p) {
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(p);
        } catch (Exception ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPaises();
    }

    @Override
    public List<Paises> editar(Paises p) {
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());
        Paises paisActual = ctrl.findPaises(p.getIdPais());

        paisActual.setNombrePais(p.getNombrePais());

        try {
            ctrl.edit(paisActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPaises();
    }

    @Override
    public List<Paises> eliminar(Paises p) {
        PaisesJpaController ctrl = new PaisesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(p.getIdPais());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PaisesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarPaises();
    }

}
