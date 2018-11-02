
package co.com.lindasmascotas.dtos;

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
    private Propietarios idPropietario;
    private Servicios idTipoServicio;
    private Empleados idEmpleado;

    public CitasDTO() {
    }

    public CitasDTO(Integer idCita) {
        this.idCita = idCita;
    }

    public CitasDTO(Integer idCita, String nombreMascota, BigInteger telefonoMovil, Date fechaCita, Propietarios idPropietario, Servicios idTipoServicio, Empleados idEmpleado) {
        this.idCita = idCita;
        this.nombreMascota = nombreMascota;
        this.telefonoMovil = telefonoMovil;
        this.fechaCita = fechaCita;
        this.idPropietario = idPropietario;
        this.idTipoServicio = idTipoServicio;
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public BigInteger getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(BigInteger telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Propietarios getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Propietarios idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Servicios getIdTipoServicio() {
        return idTipoServicio;
    }

    public void setIdTipoServicio(Servicios idTipoServicio) {
        this.idTipoServicio = idTipoServicio;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
}
