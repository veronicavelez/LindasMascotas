package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CitasJpaController;
import co.com.lindasmascotas.JPAcontrollers.EmpleadosJpaController;
import co.com.lindasmascotas.JPAcontrollers.ServiciosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.dtos.CitasDTO;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.services.CitasSvc;
import co.com.lindasmascotas.util.Mail;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.internal.helper.Helper;

public class CitasImpl implements CitasSvc {

    @Override
    public Response listarCitas() {
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());
        Response res = new Response();

        try {
            List<Citas> lis = ctrl.findCitasEntities();

            res.setStatus(true);
            res.setData(lis);

        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }

        return res;
    }

    @Override
    public Response crear(CitasDTO c) {
        Response res = new Response();
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        try {
            Citas crearcita = new Citas();
            crearcita.setIdCita(c.getIdCita());
            crearcita.setTelefonoMovil(c.getTelefonoMovil());
            crearcita.setFechaCita(c.getFechaCita());
            crearcita.setIdPropietario(c.getIdPropietario());
            crearcita.setIdTipoServicio(c.getIdTipoServicio());
            crearcita.setNombreMascota(c.getNombreMascota().toUpperCase());
            crearcita.setIdEmpleado(c.getIdEmpleado());
            /*
            if (c != null) {
                System.out.println("co.com.lindasmascotas.implementations.CitasImpl.crear()");
            }
             */
            if(!validarDisponibilidad(crearcita)){
                                             
                
               ctrl.create(crearcita);
               //Mail.enviarNotificacionCita(c);
               res.setStatus(true);
               
            }else{
                res.setStatus(false);
                res.setMessage("No se tiene cita disponible");
            }

            res.setData(listarCitas().getData());
            
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);

            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCitasEntities());
        }

        return res;
    }

    @Override
    public Response editar(CitasDTO c) {
        Response res = new Response();
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        Citas citaActual = ctrl.findCitas(c.getIdCita());

        citaActual.setNombreMascota(c.getNombreMascota());
        citaActual.setTelefonoMovil(c.getTelefonoMovil());
        citaActual.setFechaCita(c.getFechaCita());

        try {

            c.setNombreMascota(c.getNombreMascota().toUpperCase());

            ctrl.edit(citaActual);

            res = listarCitas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);

            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCitasEntities());
        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);

            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCitasEntities());
        }

        return res;
    }

    @Override
    public Response Cancelar(CitasDTO c) {
        Response res = new Response();
        CitasJpaController ctrl = new CitasJpaController(UPfactory.getFACTORY());

        Citas citaActual = ctrl.findCitas(c.getIdCita());
        Calendar fechaCita = Calendar.getInstance();
        fechaCita.setTime(citaActual.getFechaCita());
        boolean val = validarHoraCita(fechaCita);

        try {
            ctrl.edit(citaActual);

            res = listarCitas();
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);

            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCitasEntities());

        } catch (Exception ex) {
            Logger.getLogger(CitasImpl.class.getName()).log(Level.SEVERE, null, ex);

            res.setStatus(false);
            res.setMessage(MessageExceptions.messageException(ex.getMessage()));
            res.setData(ctrl.findCitasEntities());
        }

        return res;
    }

    private boolean validarHoraCita(Calendar fechaCita) {
        Calendar fechaActual = Calendar.getInstance();

        if (fechaActual.equals(fechaCita)) {

            if ((fechaCita.get(Calendar.HOUR) - fechaActual.get(Calendar.HOUR)) > 4) {
                return true;
            } else {
                return false;
            }

        } else {
            return true;

        }
    }

    private boolean validarDisponibilidad(Citas c) {
        EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());

        Empleados emple = ctrl.findEmpleados(c.getIdEmpleado().getIdEmpleado());
        String fechaC = separarFechaHora(c.getFechaCita(), "fecha");
        String horaC = separarFechaHora(c.getFechaCita(), "hora");

        if (emple.getCitasList().isEmpty()) {
            return false;
        } else {
            for (Citas ce : emple.getCitasList()) {
                String fechaCE = separarFechaHora(ce.getFechaCita(), "fecha");                
                if (fechaCE.equals(fechaC)) {
                    String horaCE = separarFechaHora(ce.getFechaCita(), "hora");
                    
                    if(horaCE.equals(horaC)){
                        return true;
                    }                 
                }
            }
        }
        return false;
    }

    private String separarFechaHora(Date fechaCita, String tipo) {
        String dateFrmt = null;

        if (tipo.equals("fecha")) {
            dateFrmt = DateFormat.getDateInstance(DateFormat.SHORT).format(fechaCita);
            return dateFrmt;

        } else if (tipo.equals("hora")) {
            dateFrmt = DateFormat.getTimeInstance(DateFormat.SHORT).format(fechaCita);
            return dateFrmt;
        }

        return dateFrmt;
    }

    @Override
    public Response horarioEmple(Integer idEmpleado, Integer idServicio) {
        Response res = new Response();
        EmpleadosJpaController ctrl = new EmpleadosJpaController(UPfactory.getFACTORY());
        ServiciosJpaController ctr = new ServiciosJpaController(UPfactory.getFACTORY());
        
        
        return res;
   }
    
   
    
}
