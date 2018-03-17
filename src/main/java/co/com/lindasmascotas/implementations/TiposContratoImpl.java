
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.TiposContratoJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposContrato;
import co.com.lindasmascotas.services.TiposContratoSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposContratoImpl implements TiposContratoSvc {

    @Override
    public List<TiposContrato> listarTiposContrato() {
         TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());

        return ctrl.findTiposContratoEntities();
    }
    

    @Override
    public List<TiposContrato> crear(TiposContrato tc) {
          TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(tc);
        } catch (Exception ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarTiposContrato();
    }
    

    @Override
    public List<TiposContrato> editar(TiposContrato tc) {
        TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());
        
        TiposContrato tiposcontratoActual = ctrl.findTiposContrato(tc.getIdTipoContrato());

        tiposcontratoActual.setNombreContrato(tc.getNombreContrato());

        try {
            ctrl.edit(tiposcontratoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarTiposContrato();
    }

    @Override
    public List<TiposContrato> eliminar(TiposContrato tc) {
          TiposContratoJpaController ctrl = new TiposContratoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(tc.getIdTipoContrato());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposContratoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarTiposContrato();

    }
    
}
