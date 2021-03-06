
package co.com.lindasmascotas.implementations;


import co.com.lindasmascotas.JPAcontrollers.PropietariosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.dtos.PropietariosDTO;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.services.PropietariosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropietariosImpl implements PropietariosSvc{

    @Override
    public Response listarPropietarios() {
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
    
        try {
            List<Propietarios> lis = ctrl.findPropietariosEntities();
            List<PropietariosDTO> dto = new ArrayList<PropietariosDTO>();
            
            for(Propietarios p: lis){
                dto.add(PropietariosDTO.setData(p));
            }
            
            res.setStatus(true);
            res.setData(dto);
        } catch (Exception e){
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        return res;
    }

    @Override
    public Response crear(Propietarios p) {
        Response res = new Response();
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());
 
        try {
            p.setNombrePropietario(p.getNombrePropietario().toUpperCase());
            p.setApellidosPropietario(p.getApellidosPropietario().toUpperCase());
            p.setCorreoElectronico(p.getCorreoElectronico().toUpperCase());
            p.setDireccion(p.getDireccion().toUpperCase());
            
            Calendar fechaNac = Calendar.getInstance();
            fechaNac.setTime(p.getFechaNacimiento());
            int edad = calculaEdad(fechaNac);
            if(edad >= 18 ){
                ctrl.create(p);
                
            }else{
                res.setStatus(false);
                res.setMessage("No fue posible realizar el registro, el empleado debe de ser mayor a 18 años");
            }
            res = listarPropietarios();
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPropietariosEntities());
        }
        return res;
    }

    @Override
    public Response editar(Propietarios p) {
        Response res = new Response();
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY()); 
        Propietarios propietarioActual = ctrl.findPropietarios(p.getIdPropietario());
        
        propietarioActual.setNombrePropietario(p.getNombrePropietario());
        propietarioActual.setApellidosPropietario(p.getApellidosPropietario());
        propietarioActual.setFechaNacimiento(p.getFechaNacimiento());
        propietarioActual.setCorreoElectronico(p.getCorreoElectronico());
        propietarioActual.setDireccion(p.getDireccion());
        propietarioActual.setTelefonoFijo(p.getTelefonoFijo());
        propietarioActual.setTelefonoMovil(p.getTelefonoMovil());
        propietarioActual.setEstado(p.getEstado());

        
        try {
            p.setNombrePropietario(p.getNombrePropietario().toUpperCase());
            p.setApellidosPropietario(p.getApellidosPropietario().toUpperCase());
            p.setCorreoElectronico(p.getCorreoElectronico().toUpperCase());
            p.setDireccion(p.getDireccion().toUpperCase());
            
            ctrl.edit(propietarioActual);
            
            res = listarPropietarios();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPropietariosEntities());
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPropietariosEntities());
        }
        
        return res;
    }

    /*Pendiente validar */

    @Override
    public Response estado(Propietarios p) {
        Response res = new Response();
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY()); 
        Propietarios propietarioActual = ctrl.findPropietarios(p.getIdPropietario());
        
        propietarioActual.setEstado(p.getEstado());
        
        try {
            ctrl.edit(propietarioActual);
            
            res = listarPropietarios();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPropietariosEntities());
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findPropietariosEntities());
        }
        
        return res;
    
    }
  
     private int calculaEdad(Calendar fechaNac) {
        Calendar today = Calendar.getInstance();

        int diff_year = today.get(Calendar.YEAR) -  fechaNac.get(Calendar.YEAR);
        int diff_month = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        int diff_day = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);

        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
        }
        return diff_year;
    }

    
    
}
