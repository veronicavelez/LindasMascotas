/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Veronica
 */
@Entity
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c")
    , @NamedQuery(name = "Citas.findByIdCita", query = "SELECT c FROM Citas c WHERE c.idCita = :idCita")
    , @NamedQuery(name = "Citas.findByNombreMascota", query = "SELECT c FROM Citas c WHERE c.nombreMascota = :nombreMascota")
    , @NamedQuery(name = "Citas.findByTelefonoMovil", query = "SELECT c FROM Citas c WHERE c.telefonoMovil = :telefonoMovil")
    , @NamedQuery(name = "Citas.findByFechaCita", query = "SELECT c FROM Citas c WHERE c.fechaCita = :fechaCita")})
public class Citas implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_cita")
    @Temporal(TemporalType.TIME)
    private Date horaCita;
  

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_mascota")
    private String nombreMascota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono_movil")
    private BigInteger telefonoMovil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_cita")
    @Temporal(TemporalType.TIME)
    private Date fechaCita;
    @JoinColumn(name = "id_propietario", referencedColumnName = "id_propietario")
    @ManyToOne(optional = false)
    private Propietarios idPropietario;
    @JoinColumn(name = "id_tipo_servicio", referencedColumnName = "id_servicio")
    @ManyToOne(optional = false)
    private Servicios idTipoServicio;

    public Citas() {
    }

    public Citas(Integer idCita) {
        this.idCita = idCita;
    }

    public Citas(Integer idCita, String nombreMascota, BigInteger telefonoMovil, Date fechaCita) {
        this.idCita = idCita;
        this.nombreMascota = nombreMascota;
        this.telefonoMovil = telefonoMovil;
        this.fechaCita = fechaCita;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Citas[ idCita=" + idCita + " ]";
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

  

}
