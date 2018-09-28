
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.VacunasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Vacunas;
import co.com.lindasmascotas.services.VacunasSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VacunasImpl implements VacunasSvc {

    @Override
    public Response listarVacunas() {
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Vacunas> list = ctrl.findVacunasEntities();
            
            res.setStatus(true);
            res.setData(list);
        } catch (Exception e){
            
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        return res;
    }

    @Override
    public Response crear(Vacunas v) {
        Response res = new Response();
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
        
        try {
            ctrl.create(v);
            
            res = listarVacunas();
        } catch (Exception ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findVacunasEntities());
        }
        return res;
    }

    @Override
    public Response editar(Vacunas v) {
        Response res = new Response();
        VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
        
        Vacunas vacunaActual = ctrl.findVacunas(v.getIdVacuna());

        vacunaActual.setNombreVacuna(v.getNombreVacuna());
        vacunaActual.setDescripcionVacuna(v.getDescripcionVacuna());

        try {
            ctrl.edit(vacunaActual);
            
            res = listarVacunas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findVacunasEntities());
        } catch (Exception ex) {
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findVacunasEntities());
        }
        return res;
    }

    @Override
    public Response eliminar(Integer id) {
        Response res = new Response();
         VacunasJpaController ctrl = new VacunasJpaController(UPfactory.getFACTORY());
         
        try {
            ctrl.destroy(id); 
            
            res = listarVacunas();
        } catch (NonexistentEntityException ex){
            Logger.getLogger(VacunasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findVacunasEntities());
        }
        return res;
    } 
}
