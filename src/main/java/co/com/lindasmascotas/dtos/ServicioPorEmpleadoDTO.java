
package co.com.lindasmascotas.dtos;

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

    public Integer getIdServEmpl() {
        return idServEmpl;
    }

    public void setIdServEmpl(Integer idServEmpl) {
        this.idServEmpl = idServEmpl;
    }

    public EmpleadosDTO getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(EmpleadosDTO idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ServiciosDTO getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServiciosDTO idServicio) {
        this.idServicio = idServicio;
    }
    
    
}
