/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.DetalleTurnosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.DetalleTurnos;
import co.com.lindasmascotas.services.DetalleTurnosSvc;
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
public class DetalleTurnosImpl implements DetalleTurnosSvc {

    @Override
    public Response listarDetalleTurnos() {
        DetalleTurnosJpaController ctrl = new DetalleTurnosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try{
            List<DetalleTurnos> list = ctrl.findDetalleTurnosEntities();
            
            res.setStatus(true);
            res.setData(list);
        }catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        
        return res;
    }

    @Override
    public Response crear(DetalleTurnos d) {
        Response res = new Response();
        DetalleTurnosJpaController ctrl = new DetalleTurnosJpaController(UPfactory.getFACTORY());
        
        try{
            
            ctrl.create(d);
            
            res = listarDetalleTurnos();
        } catch (Exception ex) {
            Logger.getLogger(DetalleTurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDetalleTurnosEntities());
        }
        
        return res;
    }

    @Override
    public Response editar(DetalleTurnos d) {
        Response res = new Response();
        DetalleTurnosJpaController ctrl = new DetalleTurnosJpaController(UPfactory.getFACTORY());
        DetalleTurnos detalleturnoActual  = ctrl.findDetalleTurnos(d.getIdDetalleTurno());
        
        detalleturnoActual.setDias(d.getDias());
        detalleturnoActual.setHoraInicial(d.getHoraInicial());
        detalleturnoActual.setHoraFinal(d.getHoraFinal());
        
        try{
            ctrl.edit(detalleturnoActual);
            
            res = listarDetalleTurnos();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleTurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDetalleTurnosEntities());
        } catch (Exception ex) {
            Logger.getLogger(DetalleTurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDetalleTurnosEntities());
        }
        
        return res;
        
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        DetalleTurnosJpaController ctrl = new DetalleTurnosJpaController(UPfactory.getFACTORY());
        
        try{
            ctrl.destroy(id);
            
            res = listarDetalleTurnos();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(DetalleTurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDetalleTurnosEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleTurnosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findDetalleTurnosEntities());
        }
        
        return res;
    }
    
}
