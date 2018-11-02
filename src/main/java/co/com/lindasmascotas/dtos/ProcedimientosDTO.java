
package co.com.lindasmascotas.dtos;

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

    public Integer getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(Integer idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EmpleadosDTO getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(EmpleadosDTO idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public ServiciosDTO getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(ServiciosDTO idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }
    
}
