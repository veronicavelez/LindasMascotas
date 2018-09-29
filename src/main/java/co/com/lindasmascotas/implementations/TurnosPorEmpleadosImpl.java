
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.TurnosPorEmpleadosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import co.com.lindasmascotas.services.TurnosPorEmpleadosSvc;
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
public class TurnosPorEmpleadosImpl implements TurnosPorEmpleadosSvc {

    @Override
    public Response listarTurnosPorEmpleados() {
        TurnosPorEmpleadosJpaController ctrl = new TurnosPorEmpleadosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try{
            List<TurnosPorEmpleados> list = ctrl.findTurnosPorEmpleadosEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
          return res;
    }

    @Override
    public Response crear(TurnosPorEmpleados templ) {
        Response res = new Response();
        TurnosPorEmpleadosJpaController ctrl = new TurnosPorEmpleadosJpaController(UPfactory.getFACTORY());
        
        try{
            
            ctrl.create(templ);
            
            res = listarTurnosPorEmpleados();
            
        } catch (Exception ex) {
            Logger.getLogger(TurnosPorEmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosPorEmpleadosEntities());
        }
        return res;
    }

    @Override
    public Response editar(TurnosPorEmpleados templ) {
        Response res = new Response();
        TurnosPorEmpleadosJpaController ctrl = new TurnosPorEmpleadosJpaController(UPfactory.getFACTORY());
        TurnosPorEmpleados turnosPorEmplActual = ctrl.findTurnosPorEmpleados(templ.getIdTurnosPorEmpl());
        
        
        try{
            ctrl.edit(turnosPorEmplActual);
            
            res = listarTurnosPorEmpleados();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TurnosPorEmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosPorEmpleadosEntities());
        } catch (Exception ex) {
            Logger.getLogger(TurnosPorEmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosPorEmpleadosEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer Id) {
        Response res = new Response();
        TurnosPorEmpleadosJpaController ctrl = new TurnosPorEmpleadosJpaController(UPfactory.getFACTORY());
        
        try{
            ctrl.destroy(Id);
            
            res = listarTurnosPorEmpleados();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TurnosPorEmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findTurnosPorEmpleadosEntities());
        }
        return res;
    }
    
    
    
}
