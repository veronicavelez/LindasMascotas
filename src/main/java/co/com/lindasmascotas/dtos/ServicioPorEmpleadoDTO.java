
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.ServicioPorEmpleado;

public class ServicioPorEmpleadoDTO {
    
    private Integer idServEmpl;
    private EmpleadosDTO idEmpleado;
    private ServiciosDTO idServicio;

    public ServicioPorEmpleadoDTO() {
    }

    public ServicioPorEmpleadoDTO(Integer idServEmpl, EmpleadosDTO idEmpleado, ServiciosDTO idServicio) {
        this.idServEmpl = idServEmpl;
        this.idEmpleado = idEmpleado;
        this.idServicio = idServicio;
    }
    
    public static ServicioPorEmpleadoDTO setData(ServicioPorEmpleado spe){
            return new ServicioPorEmpleadoDTO(spe.getIdServEmpl(), EmpleadosDTO.setData(spe.getIdEmpleado()), ServiciosDTO.setData(spe.getIdServicio()));
                    
    }                

    public Integer getIdServEmpl() {
        return idServEmpl;
    }

    public EmpleadosDTO getIdEmpleado() {
        return idEmpleado;
    }

    public ServiciosDTO getIdServicio() {
        return idServicio;
    }

    
    
}
