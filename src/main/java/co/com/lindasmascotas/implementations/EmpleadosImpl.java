
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.EmpleadosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.services.EmpleadosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpleadosImpl implements EmpleadosSvc{

    @Override
    public Response listarEmpleados() {
            EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());
            Response res = new Response();
            
            try {
                List<Empleados> list = ctrl.findEmpleadosEntities();
                
                res.setStatus(true);
                res.setData(list);
            } catch (Exception e){
                res.setStatus(false);
                res.setMessage("Ha ocurrido un error, intente más tarde.");
            }

            return res;
    }

    @Override
    public Response crear(Empleados e) {
        Response res = new Response();
           EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());
                
        try {
            Calendar fechaNaci = Calendar.getInstance();
            fechaNaci.setTime(e.getFechaNacimiento());
            int edadEmpl  = calculaEdad(fechaNaci);
            if(edadEmpl >= 18){
               ctrl.create(e);
                
                 res = listarEmpleados();
            } 
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEmpleadosEntities());
            
                  
        }
        return res;
    }
    

    @Override
    public Response editar(Empleados e) {
        Response res = new Response();
                EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());
                Empleados empleadoActual = ctrl.findEmpleados(e.getIdEmpleado());
                
                empleadoActual.setNombreEmpleado(e.getNombreEmpleado());
                empleadoActual.setApellidosEmpleado(e.getApellidosEmpleado());
                empleadoActual.setFechaNacimiento(e.getFechaNacimiento());
                empleadoActual.setCorreoElectronico(e.getCorreoElectronico());
                empleadoActual.setDireccion(e.getDireccion());
                empleadoActual.setTelefonoFijo(e.getTelefonoFijo());
                empleadoActual.setTelefonoMovil(e.getTelefonoMovil());
                empleadoActual.setFechaContratoInicial(e.getFechaContratoInicial());
                empleadoActual.setFechaContratoFinal(e.getFechaContratoFinal());
                empleadoActual.setEstadoEmpleado(e.getEstadoEmpleado());
                
                
                
                
        try {
            ctrl.edit(empleadoActual);
            
            res = listarEmpleados();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEmpleadosEntities());
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEmpleadosEntities());
        }
        return res;
    }


    @Override
        public Response estado(Empleados e) {
            Response res = new Response();
              EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());
              
              Empleados empleadoActual = ctrl.findEmpleados(e.getIdEmpleado());
              
              empleadoActual.setEstadoEmpleado(e.getEstadoEmpleado());
        try {
            ctrl.edit(empleadoActual);
            
            res = listarEmpleados();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEmpleadosEntities());
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findEmpleadosEntities());
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
