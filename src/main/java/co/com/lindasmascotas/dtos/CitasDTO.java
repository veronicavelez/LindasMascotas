
package co.com.lindasmascotas.dtos;

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
    private Date horaCita;
    private Integer idCita;
    private String nombreMascota;
    private BigInteger telefonoMovil;
    private Date fechaCita;
    private Propietarios idPropietario;
    private Servicios idTipoServicio;

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
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

}
