
package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.EspeciesJpaController;
import co.com.lindasmascotas.JPAcontrollers.RazasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Especies;
import co.com.lindasmascotas.entities.Razas;
import co.com.lindasmascotas.services.RazasSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RazasImpl implements RazasSvc {
    
     @Override
    public List<Razas> listarRazas() {
         RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        return ctrl.findRazasEntities();
    }
    
    @Override
     public List<Razas> crear(Razas r) {
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(r);
        } catch (Exception ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarRazas();
    }
     
     
      @Override
      public List<Razas> editar(Razas r) {
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

          Razas razaActual = ctrl.findRazas(r.getIdRaza());

        razaActual.setNombreRaza(r.getNombreRaza());

        try {
            ctrl.edit(razaActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarRazas();

    }
    
    @Override
    public List<Razas> eliminar(Integer id) {
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(RazasImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarRazas();
        
        
    }    

    @Override
    public List<Razas> razasPorEspecie(Integer id) {
        EspeciesJpaController ctrlEspecie = new EspeciesJpaController(UPfactory.getFACTORY());
        RazasJpaController ctrl = new RazasJpaController(UPfactory.getFACTORY());
        
        Especies e = ctrlEspecie.findEspecies(id);
        List<Razas> lista = ctrl.findRazasByEspecies(e);
        
        return lista;
    }
    
    
}
