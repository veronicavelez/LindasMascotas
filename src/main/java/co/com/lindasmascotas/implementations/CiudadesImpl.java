package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CiudadesJpaController;
import co.com.lindasmascotas.JPAcontrollers.DepartamentosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Departamentos;
import co.com.lindasmascotas.services.CiudadesSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CiudadesImpl implements CiudadesSvc {

    @Override
    public List<Ciudades> listarCiudades() {
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        return ctrl.findCiudadesEntities();
    }

    @Override
    public List<Ciudades> crear(Ciudades c) {
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.create(c);
        } catch (Exception ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCiudades();
    }

    @Override
    public List<Ciudades> editar(Ciudades c) {
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        Ciudades ciudadActual = ctrl.findCiudades(c.getIdCiudad());

        ciudadActual.setNombreCiudad(c.getNombreCiudad());
        ciudadActual.setIdDpto(c.getIdDpto());

        try {
            ctrl.edit(ciudadActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCiudades();

    }

    @Override
    public List<Ciudades> eliminar(String id) {
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        try {
            ctrl.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listarCiudades();

    }

    @Override
    public List<Ciudades> ciudadesPorDpto(String id) {
        DepartamentosJpaController ctrlDpto = new DepartamentosJpaController(UPfactory.getFACTORY());
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        Departamentos d = ctrlDpto.findDepartamentos(id);
        List<Ciudades> lista = ctrl.findCiudadByDepartamento(d);

        return lista;
    }

}
