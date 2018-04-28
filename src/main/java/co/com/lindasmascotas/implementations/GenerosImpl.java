package co.com.lindasmascotas.implementations;
import co.com.lindasmascotas.JPAcontrollers.GenerosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Generos;
import co.com.lindasmascotas.services.GenerosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenerosImpl implements GenerosSvc {
    
    @Override
    public List<Generos> listarGeneros() {
        GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        return ctrl.findGenerosEntities();
    }

    @Override
    public List<Generos> crear(Generos g) {
       GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(g);
        } catch (Exception ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarGeneros();
    }
    

    @Override
    public List<Generos> editar(Generos g) {
         GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        Generos generoActual = ctrl.findGeneros(g.getIdGenero());

        generoActual.setNombreGenero(g.getNombreGenero());

        try {
            ctrl.edit(generoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarGeneros();

    }


    @Override
    public List<Generos> eliminar(Integer id) {
        GenerosJpaController ctrl = new GenerosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(GenerosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarGeneros();

        
    }
    
    
}
