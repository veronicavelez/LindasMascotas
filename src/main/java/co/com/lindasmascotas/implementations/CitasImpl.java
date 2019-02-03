package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.CitasJpaController;
import co.com.lindasmascotas.JPAcontrollers.EmpleadosJpaController;
import co.com.lindasmascotas.JPAcontrollers.PropietariosJpaController;
import co.com.lindasmascotas.JPAcontrollers.ServicioPorEmpleadoJpaController;
import co.com.lindasmascotas.JPAcontrollers.ServiciosJpaController;
import co.com.lindasmascotas.JPAcontrollers.TurnosPorEmpleadosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.dtos.CitasDTO;
import co.com.lindasmascotas.dtos.PropietariosDTO;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.entities.Propietarios_;
import co.com.lindasmascotas.entities.ServicioPorEmpleado;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.entities.TurnosPorEmpleados;
import co.com.lindasmascotas.services.CitasSvc;
import co.com.lindasmascotas.util.Mail;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.text.DateFormat;
import java.util.ArrayList;
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
            
            Propietarios propietario = new Propietarios(c.getIdPropietario().getIdPropietario());
            Servicios servicio = new Servicios(c.getIdTipoServicio().getIdServicio());
            Empleados empleado = new Empleados(c.getIdEmpleado().getIdEmpleado());
                    
            crearcita.setIdCita(c.getIdCita());
            crearcita.setTelefonoMovil(c.getTelefonoMovil());
            crearcita.setFechaCita(c.getFechaCita());
            crearcita.setIdPropietario(propietario);
            crearcita.setIdTipoServicio(servicio);
            crearcita.setNombreMascota(c.getNombreMascota().toUpperCase());
            crearcita.setIdEmpleado(empleado);
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

            citaActual.setNombreMascota(c.getNombreMascota().toUpperCase());

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
    public Response horarioEmple(Integer idEmpleado, Date fechaCita) {
        Response res = new Response();
        
        TurnosPorEmpleadosJpaController ctrl = new TurnosPorEmpleadosJpaController(UPfactory.getFACTORY());
        EmpleadosJpaController ctr = new EmpleadosJpaController(UPfactory.getFACTORY());
        CitasJpaController ct = new CitasJpaController(UPfactory.getFACTORY());
        Calendar c = Calendar.getInstance();
        int nD =-1;
        
        try {
            c.setTime(fechaCita);
            nD = c.get(Calendar.DAY_OF_WEEK);
            Integer horaIni = 0;
            Integer horaFin = 0;
            Date horaIniEmp = null;
            Date horaFinEmp = null;
            List<String> horario = new ArrayList<String>();
            List<TurnosPorEmpleados> templ = ctrl.findIdTurnosPorEmplByEmpleado(idEmpleado);
            
            for(TurnosPorEmpleados tpe : templ) {
                if (tpe.getIdDetalleTurnos().getDias() == nD) {
                   horaIniEmp = tpe.getIdDetalleTurnos().getHoraInicial();
                   horaFinEmp = tpe.getIdDetalleTurnos().getHoraFinal();
                   break;
                }
            }
            
            c.setTime(horaIniEmp);
            horaIni = c.get(Calendar.HOUR_OF_DAY);
            c.setTime(horaFinEmp);
            horaFin = c.get(Calendar.HOUR_OF_DAY);
            
            for (int i = horaIni; i < horaFin; i++) {
                
                horario.add(i + ":00");
                horario.add(i + ":30");
            }
            
            res.setData(horario);
            res.setStatus(true);
        } catch (Exception e) {
            
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error");
        }
        
        
        return res;
   }

    @Override
    public Response empleadosPorServicio(Integer idServicio) {
        Response res = new Response();
        ServicioPorEmpleadoJpaController ctrl = new ServicioPorEmpleadoJpaController(UPfactory.getFACTORY());
        
        try {
            List<ServicioPorEmpleado> serviciopempl = ctrl.findEmpleadosPorServicio(idServicio);
            List<Empleados> empleadoserv = new ArrayList<Empleados>();
            
            for(ServicioPorEmpleado spe:serviciopempl){
                
                empleadoserv.add(spe.getIdEmpleado());
            
            }
            
            res.setStatus(true);
            res.setData(empleadoserv);
        } catch (Exception e) {
            res.setStatus(false);
            res.setMessage("Ha ocurrido un error, intente más tarde.");
        }
        
        return res;
    }    

    
    
    @Override
    public Response propietario(Integer idPropietario) {
       Response res = new Response();
        PropietariosJpaController ctrl = new PropietariosJpaController(UPfactory.getFACTORY());
       
       try{
           Propietarios prop = ctrl.findPropietarios(idPropietario);
           
           if(prop != null){
               res.setStatus(true);
               res.setData(prop);
           }else{
               res.setStatus(true);
               res.setMessage("No existe un Propietario con ese número de cédula");
           }
           
       }catch (Exception e){
           res.setStatus(false);
           res.setMessage("Ha ocurrido un error, intente mas tarde.");
       }      
      
       return res;
   } 
    
}
