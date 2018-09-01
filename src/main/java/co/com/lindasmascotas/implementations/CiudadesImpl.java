package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CiudadesJpaController;
import co.com.lindasmascotas.JPAcontrollers.CiudadesJpaController;
import co.com.lindasmascotas.JPAcontrollers.DepartamentosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Ciudades;
import co.com.lindasmascotas.entities.Departamentos;
import co.com.lindasmascotas.services.CiudadesSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CiudadesImpl implements CiudadesSvc {

    @Override
    public Response listarCiudades() {
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Ciudades> list = ctrl.findCiudadesEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        } 
        return res;       
    }

    @Override
    public Response crear(Ciudades d) {
        Response res = new Response();
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());

        try {
            
            d.setNombreCiudad(d.getNombreCiudad().toUpperCase());
            ctrl.create(d);
            
            res = listarCiudades();
        } catch (Exception ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCiudadesEntities());
        }
        return res;
    }

    @Override
    public Response editar(Ciudades c) {
        Response res = new Response();
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());        
        Ciudades ciudadActual = ctrl.findCiudades(c.getIdCiudad());

        ciudadActual.setNombreCiudad(c.getNombreCiudad());
        ciudadActual.setIdDpto(c.getIdDpto());
        
        try {
            ctrl.edit(ciudadActual);
            
            res = listarCiudades();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCiudadesEntities());
        } catch (Exception ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCiudadesEntities());
        }
                
        return res;
    }

    @Override
    public Response eliminar(String id) {
        Response res = new Response();
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(id);
            
            res = listarCiudades();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCiudadesEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CiudadesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCiudadesEntities());
        }
        return res;
    }

    @Override
    public Response ciudadesPorDpto(String id) {
        Response res = new Response();
        DepartamentosJpaController ctrlDpto = new DepartamentosJpaController(UPfactory.getFACTORY());
        CiudadesJpaController ctrl = new CiudadesJpaController(UPfactory.getFACTORY());
        
        try {
            Departamentos d = ctrlDpto.findDepartamentos(id);
            List<Ciudades> lista = ctrl.findCiudadByDepartamento(d);
            
            res.setStatus(true);
            res.setData(lista);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        
        return res;
    }

}
