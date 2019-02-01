
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Procedimientos;
import java.math.BigDecimal;
import java.util.Date;


public class ProcedimientosDTO {
    
     private Integer idProcedimiento;
    private String nombreProcedimiento;
    private String descripcion;
    private BigDecimal peso;
    private Date fecha;
    private EmpleadosDTO idEmpleado;
   // private Mascotas idMascota;
    private ServiciosDTO idTipoServicio;
    //private Sexos idSexo;
    //private Vacunas idVacuna;

    public ProcedimientosDTO() {
    }

    public ProcedimientosDTO(Integer idProcedimiento, String nombreProcedimiento, String descripcion, BigDecimal peso, Date fecha, EmpleadosDTO idEmpleado, ServiciosDTO idTipoServicio) {
        this.idProcedimiento = idProcedimiento;
        this.nombreProcedimiento = nombreProcedimiento;
        this.descripcion = descripcion;
        this.peso = peso;
        this.fecha = fecha;
        this.idEmpleado = idEmpleado;
        this.idTipoServicio = idTipoServicio;
    }
    
    public static ProcedimientosDTO setData(Procedimientos p){
        return new ProcedimientosDTO(p.getIdProcedimiento(), p.getNombreProcedimiento(),
                p.getDescripcion(), p.getPeso(), p.getFecha(), EmpleadosDTO.setData(p.getIdEmpleado()), ServiciosDTO.setData(p.getIdTipoServicio()));
    }

    public Integer getIdProcedimiento() {
        return idProcedimiento;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public Date getFecha() {
        return fecha;
    }

    public EmpleadosDTO getIdEmpleado() {
        return idEmpleado;
    }

    public ServiciosDTO getIdTipoServicio() {
        return idTipoServicio;
    }

    
    
}
