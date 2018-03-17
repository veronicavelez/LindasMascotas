
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.SexosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Sexos;
import co.com.lindasmascotas.services.SexosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SexosImpl implements SexosSvc{

    @Override
    public List<Sexos> listarSexos() {
        SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());

        return ctrl.findSexosEntities();
    }

    @Override
    public List<Sexos> crear(Sexos s) {
         SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(s);
        } catch (Exception ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarSexos();
    }

    @Override
    public List<Sexos> editar(Sexos s) {
        SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());
        Sexos sexoActual = ctrl.findSexos(s.getIdSexo());

        sexoActual.setNombreSexo(s.getNombreSexo());

        try {
            ctrl.edit(sexoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarSexos();
    }

    @Override
    public List<Sexos> eliminar(Sexos s) {
         SexosJpaController ctrl = new SexosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(s.getIdSexo());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SexosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarSexos();
    }
    
}
