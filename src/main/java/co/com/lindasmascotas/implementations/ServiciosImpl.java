
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ServiciosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.services.ServiciosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiciosImpl implements ServiciosSvc {

    @Override
    public Response listarServicios() {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            
            List<Servicios> list = ctrl.findServiciosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        
        return res;
    }
 
    @Override
    public Response crear(Servicios s) {
        Response res = new Response();
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());

        try {
            
            s.setNombreServicio(s.getNombreServicio().toUpperCase());
            s.setDescripcionServicio(s.getDescripcionServicio().toUpperCase());
            
            ctrl.create(s);
            
            res = listarServicios();
        } catch (Exception ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findServiciosEntities());
        }

        return res;
    }

    @Override
    public Response editar(Servicios s) {
        Response res = new Response();
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        
        Servicios servicioActual = ctrl.findServicios(s.getIdServicio());
        
        servicioActual.setNombreServicio(s.getNombreServicio());
        servicioActual.setDescripcionServicio(s.getDescripcionServicio());
        servicioActual.setPrecioServicio(s.getPrecioServicio());
       
        
        try {
            ctrl.edit(servicioActual);
            
            res = listarServicios();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findServiciosEntities());
        } catch (Exception ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findServiciosEntities());
        }
        
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.destroy(id);
            
            res = listarServicios();
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findServiciosEntities());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findServiciosEntities());
        }
        
        return res;
    }
}
