
package co.com.lindasmascotas.implementations;


import co.com.lindasmascotas.JPAcontrollers.PropietariosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.services.PropietariosSvc;
import co.com.lindasmascotas.util.UPfactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropietariosImpl implements PropietariosSvc{

    @Override
    public List<Propietarios> listarPropietarios() {
    PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());

     return ctrl.findPropietariosEntities();
    }

    @Override
    public List<Propietarios> crear(Propietarios p) {

    PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());
 
        try {
            Calendar fechaNac = Calendar.getInstance();
            fechaNac.setTime(p.getFechaNacimiento());
            int edad = calculaEdad(fechaNac);
            if(edad >= 18 ){
                ctrl.create(p);
            }   
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listarPropietarios();
    }

    @Override
    public List<Propietarios> editar(Propietarios p) {
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY()); 
        Propietarios propietarioActual = ctrl.findPropietarios(p.getIdPropietario());
        
        propietarioActual.setNombrePropietario(p.getNombrePropietario());
        propietarioActual.setApellidosPropietario(p.getApellidosPropietario());
        propietarioActual.setFechaNacimiento(p.getFechaNacimiento());
        propietarioActual.setCorreoElectronico(p.getCorreoElectronico());
        propietarioActual.setDireccion(p.getDireccion());
        propietarioActual.setTelefonoFijo(p.getTelefonoFijo());
        propietarioActual.setTelefonoMovil(p.getTelefonoMovil());
        
        
        
        try {
            ctrl.edit(propietarioActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarPropietarios();
    }

    /*Pendiente validar */

    @Override
    public List<Propietarios> estado(Propietarios p) {
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY()); 
        Propietarios propietarioActual = ctrl.findPropietarios(p.getIdPropietario());
        
        propietarioActual.setEstado(p.getEstado());
        
        try {
            ctrl.edit(propietarioActual);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PropietariosImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listarPropietarios();
    
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
