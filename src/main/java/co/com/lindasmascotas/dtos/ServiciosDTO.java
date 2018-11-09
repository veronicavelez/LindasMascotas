
package co.com.lindasmascotas.dtos;

import java.math.BigInteger;
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
    
    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public BigInteger getPrecioServicio() {
        return precioServicio;
    }

    public void setPrecioServicio(BigInteger precioServicio) {
        this.precioServicio = precioServicio;
    }

    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public List<CitasDTO> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<CitasDTO> citasList) {
        this.citasList = citasList;
    }

    public List<ServicioPorEmpleadoDTO> getServicioPorEmpleadoList() {
        return servicioPorEmpleadoList;
    }

    public void setServicioPorEmpleadoList(List<ServicioPorEmpleadoDTO> servicioPorEmpleadoList) {
        this.servicioPorEmpleadoList = servicioPorEmpleadoList;
    }
}
    
