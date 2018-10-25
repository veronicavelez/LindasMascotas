/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.TurnosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Turnos;
import co.com.lindasmascotas.services.TurnosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ISABEL MEDINA
 */
public class TurnosImpl implements TurnosSvc{

    @Override
    public Response listarTurnos() {
        TurnosJpaController ctrl = new TurnosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try{
            List<Turnos> list  = ctrl.findTurnosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        return res;
    }

    @Override
    public Response crear(Turnos t) {
        Response res = new Response();
        TurnosJpaController ctrl = new TurnosJpaController(UPfactory.getFACTORY());
        
        try{
            
            t.setNombreTurno(t.getNombreTurno().toUpperCase());
            
            ctrl.create(t);
            
            res = listarTurnos();
        }catch (Exception ex){
            Logger.getLogger(TurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosEntities());
        }
        
        return res;

    }

    @Override
    public Response editar(Turnos t) {
        Response res = new Response();
        TurnosJpaController ctrl = new TurnosJpaController(UPfactory.getFACTORY());
        Turnos turnosActual = ctrl.findTurnos(t.getIdTurno());
        
        turnosActual.setNombreTurno(t.getNombreTurno());
        
        try{
            t.setNombreTurno(t.getNombreTurno().toUpperCase());
            ctrl.edit(turnosActual);
            
            res = listarTurnos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosEntities());
        } catch (Exception ex) {
            Logger.getLogger(TurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosEntities());
        }
        
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        TurnosJpaController ctrl = new TurnosJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(id);
            
            res = listarTurnos();
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosEntities());
        }
        return res;

    }
    
   
    
    
    
    
}
