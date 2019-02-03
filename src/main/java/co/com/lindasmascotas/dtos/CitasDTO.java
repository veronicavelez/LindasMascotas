
package co.com.lindasmascotas.dtos;

import co.com.lindasmascotas.entities.Citas;
import co.com.lindasmascotas.entities.Empleados;
import co.com.lindasmascotas.entities.Propietarios;
import co.com.lindasmascotas.entities.Servicios;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ISABEL MEDINA
 */
public class CitasDTO {
    private Integer idCita;
    private String nombreMascota;
    private BigInteger telefonoMovil;
    private Date fechaCita;
    private PropietariosDTO idPropietario;
    private ServiciosDTO idTipoServicio;
    private EmpleadosDTO idEmpleado;

    public CitasDTO() {
    }

    public CitasDTO(Integer idCita) {
        this.idCita = idCita;
    }

    public CitasDTO(Integer idCita, String nombreMascota, BigInteger telefonoMovil, Date fechaCita, PropietariosDTO idPropietario, ServiciosDTO idTipoServicio, EmpleadosDTO idEmpleado) {
        this.idCita = idCita;
        this.nombreMascota = nombreMascota;
        this.telefonoMovil = telefonoMovil;
        this.fechaCita = fechaCita;
        this.idPropietario = idPropietario;
        this.idTipoServicio = idTipoServicio;
        this.idEmpleado = idEmpleado;
    }
    
    public static CitasDTO setData(Citas c){
        return new CitasDTO(c.getIdCita(), c.getNombreMascota(), c.getTelefonoMovil(), c.getFechaCita(),
                PropietariosDTO.setData(c.getIdPropietario()), ServiciosDTO.setData(c.getIdTipoServicio()), EmpleadosDTO.setData(c.getIdEmpleado()));
    }

    public Integer getIdCita() {
        return idCita;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public BigInteger getTelefonoMovil() {
        return telefonoMovil;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public PropietariosDTO getIdPropietario() {
        return idPropietario;
    }

    public ServiciosDTO getIdTipoServicio() {
        return idTipoServicio;
    }

    public EmpleadosDTO getIdEmpleado() {
        return idEmpleado;
    }
    
    
}
