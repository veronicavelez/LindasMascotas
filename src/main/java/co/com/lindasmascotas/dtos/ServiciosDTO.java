
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.ServicioPorEmpleado;
import co.com.lindasmascotas.entities.Servicios;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class ServiciosDTO {
    
    private Integer idServicio;
    private String nombreServicio;
    private BigInteger precioServicio;
    private String descripcionServicio;
    private List<CitasDTO> citasList;
    private List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList;
   // private List<ProcedimientosDTO> procedimientosList;   

    public ServiciosDTO() {
    }

    public ServiciosDTO(Integer idServicio) {
        this.idServicio = idServicio;
    }
    
    public ServiciosDTO(Integer idServicio, String nombreServicio, BigInteger precioServicio, String descripcionServicio, List<CitasDTO> citasList, List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precioServicio = precioServicio;
        this.descripcionServicio = descripcionServicio;
        this.citasList = citasList;
        this.servicioPorEmpleadoList = servicioPorEmpleadoList;
    }

    public ServiciosDTO(Integer idServicio, String nombreServicio, BigInteger precioServicio, String descripcionServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.precioServicio = precioServicio;
        this.descripcionServicio = descripcionServicio;
    }
    
    public static ServiciosDTO setData(Servicios s){
        List<CitasDTO> citasList = new ArrayList<CitasDTO>();
        List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList = new ArrayList<ServicioPorEmpleadoDTO>();
                
        for(Citas c: s.getCitasList()){
            citasList.add(CitasDTO.setData(c));
        }
        
        for(ServicioPorEmpleado spe: s.getServicioPorEmpleadoList()){
            servicioPorEmpleadoList.add(ServicioPorEmpleadoDTO.setData(spe));
        }
        
        return new ServiciosDTO(s.getIdServicio(), s.getNombreServicio(), s.getPrecioServicio(), 
                s.getDescripcionServicio(), citasList, servicioPorEmpleadoList);
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public BigInteger getPrecioServicio() {
        return precioServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public List<CitasDTO> getCitasList() {
        return citasList;
    }

    public List<ServicioPorEmpleadoDTO> getServicioPorEmpleadoList() {
        return servicioPorEmpleadoList;
    }
    
    
}
    
