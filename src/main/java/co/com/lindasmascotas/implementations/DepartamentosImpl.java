package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.DepartamentosJpaController;
import co.com.lindasmascotas.JPAcontrollers.PaisesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Departamentos;
import co.com.lindasmascotas.entities.Paises;
import co.com.lindasmascotas.services.DepartamentosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartamentosImpl implements DepartamentosSvc {

    @Override
    public List<Departamentos> listarDepartamentos() {
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());

        return ctrl.findDepartamentosEntities();
    }

    @Override
    public List<Departamentos> crear(Departamentos d) {
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(d);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarDepartamentos();
    }

    @Override
    public List<Departamentos> editar(Departamentos d) {
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        
        Departamentos dptoActual = ctrl.findDepartamentos(d.getIdDepartamento());
        
        dptoActual.setNombreDepartamento(d.getNombreDepartamento());
        dptoActual.setIdPais(d.getIdPais());
        
        try {
            ctrl.edit(dptoActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarDepartamentos();
    }

    @Override
    public List<Departamentos> eliminar(String id) {
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarDepartamentos();
    }

    @Override
    public List<Departamentos> dptoPorPais(String id) {
        PaisesJpaController ctrlPais = new PaisesJpaController(UPfactory.getFACTORY());
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        
        Paises p = ctrlPais.findPaises(id);
        List<Departamentos> lista = new ArrayList<Departamentos>();
        
        lista = ctrl.findDepartamentoByPais(p);
        
        return lista;
    }

}
