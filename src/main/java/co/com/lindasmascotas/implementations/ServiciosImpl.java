
package co.com.lindasmascotas.implementations;

import co.com.lindasmascotas.JPAcontrollers.ServicioPorEmpleadoJpaController;
import co.com.lindasmascotas.JPAcontrollers.ServiciosJpaController;
import co.com.lindasmascotas.JPAcontrollers.exceptions.IllegalOrphanException;
import co.com.lindasmascotas.JPAcontrollers.exceptions.NonexistentEntityException;
import co.com.lindasmascotas.dtos.CitasDTO;
import co.com.lindasmascotas.dtos.EmpleadosDTO;
import co.com.lindasmascotas.dtos.ServicioPorEmpleadoDTO;
import co.com.lindasmascotas.dtos.ServiciosDTO;
import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.ServicioPorEmpleado;
import co.com.lindasmascotas.entities.Servicios;
import co.com.lindasmascotas.services.ServiciosSvc;
import co.com.lindasmascotas.util.MessageExceptions;
import co.com.lindasmascotas.util.Response;
import co.com.lindasmascotas.util.UPfactory;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiciosImpl implements ServiciosSvc {

    @Override
    public Response listarServicios() {
        ServiciosJpaController ctrl = new ServiciosJpaController(UPfactory.getFACTORY());
        Response res = new Response();
        
        try {
            
            List<ServiciosDTO> listDTO = new ArrayList<ServiciosDTO>();
            List<Servicios> list = ctrl.findServiciosEntities();
            
            for(Servicios s:list){
                List<CitasDTO> citasDTO = new ArrayList<CitasDTO>();
                List<ServicioPorEmpleadoDTO> servPorEmpl = new ArrayList<ServicioPorEmpleadoDTO>();
                
                for(Citas c:s.getCitasList()){
                    CitasDTO citas = new CitasDTO(c.getIdCita());
                    citasDTO.add(citas);
                }
                
                for(ServicioPorEmpleado spe: s.getServicioPorEmpleadoList()) {
                    Empleados e = spe.getIdEmpleado();
                    EmpleadosDTO empl = EmpleadosDTO.setData(e);
                    
                    
                    ServicioPorEmpleadoDTO setvpempl = new ServicioPorEmpleadoDTO(spe.getIdServEmpl(),
                            empl, new ServiciosDTO(spe.getIdServicio().getIdServicio()));
                         
                    servPorEmpl.add(setvpempl);
                }
                                    
                ServiciosDTO sDto = new ServiciosDTO(s.getIdServicio(), s.getNombreServicio(), 
                        s.getPrecioServicio(), s.getDescripcionServicio(), citasDTO,
                        servPorEmpl);
                                
                listDTO.add(sDto);
            }
            
            res.setStatus(true);
            res.setData(listDTO);
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
            
            if(!s.getServicioPorEmpleadoList().isEmpty()){
               
              ctrl.transactionCreate(s);
               res = listarServicios();
            } else {
                res = listarServicios();
                res.setStatus(false);
                res.setMessage("No fue posible realizar el registro.");
            }    
            
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
            
            s.setNombreServicio(s.getNombreServicio().toUpperCase());
            s.setDescripcionServicio(s.getDescripcionServicio().toUpperCase());
            
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
    
    /*
    @Override
    public Response servicioporempl() {
        Response res = new Response();
        ServicioPorEmpleadoJpaController ctrl = new ServicioPorEmpleadoJpaController(UPfactory.getFACTORY());
        
        try{
            List<ServicioPorEmpleado> list = ctrl.findServicioPorEmpleadoEntities();
            List<ServicioPorEmpleadoDTO> listDTO = new ArrayList<ServicioPorEmpleadoDTO>();
            
            for(ServicioPorEmpleado se:list){
                Empleados e = se.getIdEmpleado();
                EmpleadosDTO empl = new EmpleadosDTO(e.getIdEmpleado(), 
                        e.getNombreEmpleado(), e.getApellidosEmpleado(), 
                        e.getFechaNacimiento(), e.getCorreoElectronico(), 
                        e.getDireccion(), e.getTelefonoFijo(), e.getTelefonoMovil(),
                        e.getEstadoEmpleado(), e.getFechaContratoInicial(),
                        e.getFechaContratoFinal(), e.getTipoRh());
                
                Servicios s = se.getIdServicio();
                ServiciosDTO sDto = new ServiciosDTO(s.getIdServicio(), s.getNombreServicio(), 
                        s.getPrecioServicio(), s.getDescripcionServicio());
                
                ServicioPorEmpleadoDTO dto = new ServicioPorEmpleadoDTO(se.getIdServEmpl(), 
                        empl, sDto);
                
                listDTO.add(dto);
            }
            
            res.setStatus(true);
            res.setData(listDTO);
            
        } catch(Exception ex){
            Logger.getLogger(ServiciosImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            res.setStatus(false);
        }
        
        
        return res;
    }*/
}
