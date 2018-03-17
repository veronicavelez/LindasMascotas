
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.TiposDocumentoJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TiposDocumento;
import co.com.lindasmascotas.services.TiposDocumentoSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TiposDocumentoImpl implements TiposDocumentoSvc {

    @Override
    public List<TiposDocumento> listarTiposDocumento() {
         TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());

        return ctrl.findTiposDocumentoEntities();
    }

    @Override
    public List<TiposDocumento> crear(TiposDocumento td) {
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(td);
        } catch (Exception ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarTiposDocumento();
    }

    @Override
    public List<TiposDocumento> editar(TiposDocumento td) {
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());
        TiposDocumento tipoDocActual = ctrl.findTiposDocumento(td.getIdTipoDoc());

        tipoDocActual.setNombreTipo(td.getNombreTipo());

        try {
            ctrl.edit(tipoDocActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarTiposDocumento();
    }

    @Override
    public List<TiposDocumento> eliminar(TiposDocumento td) {
        TiposDocumentoJpaController ctrl = new TiposDocumentoJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(td.getIdTipoDoc());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TiposDocumentoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarTiposDocumento();
    }
    
}
