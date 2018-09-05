package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.DepartamentosJpaController;
import co.com.lindasmascotas.JPAcontrollers.PaisesJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Departamentos;
import co.com.lindasmascotas.entities.Paises;
import co.com.lindasmascotas.services.DepartamentosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartamentosImpl implements DepartamentosSvc {

    @Override
    public Response listarDepartamentos() {
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Departamentos> list = ctrl.findDepartamentosEntities();
            
            res.setStatus(true);
            res.setData(list);
            
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        } 
        return res;       
    }

    @Override
    public Response crear(Departamentos d) {
        Response res = new Response();
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());

        try {
            
            d.setNombreDepartamento(d.getNombreDepartamento().toUpperCase());
            ctrl.create(d);
            
            res = listarDepartamentos();
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDepartamentosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Departamentos d) {
        Response res = new Response();
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());        
        Departamentos dptoActual = ctrl.findDepartamentos(d.getIdDepartamento());
        
        dptoActual.setNombreDepartamento(d.getNombreDepartamento());
        dptoActual.setIdPais(d.getIdPais());
        
        try {
            d.setNombreDepartamento(d.getNombreDepartamento().toUpperCase());
            ctrl.edit(dptoActual);
            
            res = listarDepartamentos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDepartamentosEntities());
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDepartamentosEntities());
        }
                
        return res;
    }

    @Override
    public Response eliminar(String id) {
        Response res = new Response();
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(id);
            
            res = listarDepartamentos();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDepartamentosEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DepartamentosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDepartamentosEntities());
        }
        return res;
    }

    @Override
    public Response dptoPorPais(Paises p) {
        Response res = new Response();
        PaisesJpaController ctrlPais = new PaisesJpaController(UPfactory.getFACTORY());
        DepartamentosJpaController ctrl = new DepartamentosJpaController(UPfactory.getFACTORY());
        
        try {
            
            List<Departamentos> lista = ctrl.findDepartamentoByPais(p);
        
            res.setStatus(true);
            res.setData(lista);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
               
        return res;
    }

}
