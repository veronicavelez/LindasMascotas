package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.MascotasJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Mascotas;
import co.com.lindasmascotas.services.MascotasSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MascotasImpl implements MascotasSvc {

    @Override
    public Response listarMascotas() {
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            List<Mascotas> list  = ctrl.findMascotasEntities();
            
            res.setStatus(false);
            res.setData(list);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

        return res;
    } 

    @Override
    public Response crear(Mascotas m) {
        Response res = new Response();
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());

        try {
            
            m.setNombreMascota(m.getNombreMascota().toUpperCase());
            
            if (m.getIdPropietario() != null) {
                
               ctrl.create(m); 
             
            }
               res = listarMascotas();
        } catch (Exception ex) {
            Logger.getLogger(EspeciesImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findMascotasEntities());
        }
        return res;
    }

    @Override
    public Response editar(Mascotas m) {
        Response res = new Response();
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());
        Mascotas mascotaActual = ctrl.findMascotas(m.getIdMascota());

        mascotaActual.setNombreMascota(m.getNombreMascota());
        mascotaActual.setFechaNacimiento(m.getFechaNacimiento());
        mascotaActual.setPeso(m.getPeso());
        mascotaActual.setVive(m.getVive());
        mascotaActual.setEstado(m.getEstado());
        mascotaActual.setIdPropietario(m.getIdPropietario());
     
        try {
            ctrl.edit(mascotaActual);
            
            res = listarMascotas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findMascotasEntities());
        } catch (Exception ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findMascotasEntities());
        }
        return res;

    }

    @Override
    public Response estado(Mascotas m) {
        Response res = new Response();
        MascotasJpaController ctrl = new MascotasJpaController(UPfactory.getFACTORY());
        Mascotas mascotaActual = ctrl.findMascotas(m.getIdMascota());

        mascotaActual.setEstado(m.getEstado());

        try {
            ctrl.edit(mascotaActual);
            
            res = listarMascotas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findMascotasEntities());
        } catch (Exception ex) {
            Logger.getLogger(MascotasImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findMascotasEntities());
        }

        return res;

    }

}
