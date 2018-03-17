
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.TiposSangreJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposSangre;
import co.com.lindasmascotas.services.TiposSangreSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposSangreImpl implements TiposSangreSvc {

    @Override
    public List<TiposSangre> listarTiposSangre() {
           TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

        return ctrl.findTiposSangreEntities();
    }
    
    
    @Override
    public List<TiposSangre> crear(TiposSangre ts) {
          TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(ts);
        } catch (Exception ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarTiposSangre();
    }
    

    @Override
    public List<TiposSangre> editar(TiposSangre ts) {
         TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

          TiposSangre tiposangreActual = ctrl.findTiposSangre(ts.getIdTipoSangre());

        tiposangreActual.setNombreTipoSangre(ts.getNombreTipoSangre());

        try {
            ctrl.edit(tiposangreActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarTiposSangre();
    }

    @Override
    public List<TiposSangre> eliminar(TiposSangre ts) {
        TiposSangreJpaController ctrl = new TiposSangreJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(ts.getIdTipoSangre());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposSangreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarTiposSangre();
        
    }
    
}
