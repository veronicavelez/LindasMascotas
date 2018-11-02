/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author ISABEL MEDINA
 */
@Entity
@Table(name = "servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s")
    , @NamedQuery(name = "Servicios.findByIdServicio", query = "SELECT s FROM Servicios s WHERE s.idServicio = :idServicio")
    , @NamedQuery(name = "Servicios.findByNombreServicio", query = "SELECT s FROM Servicios s WHERE s.nombreServicio = :nombreServicio")
    , @NamedQuery(name = "Servicios.findByPrecioServicio", query = "SELECT s FROM Servicios s WHERE s.precioServicio = :precioServicio")
    , @NamedQuery(name = "Servicios.findByDescripcionServicio", query = "SELECT s FROM Servicios s WHERE s.descripcionServicio = :descripcionServicio")})
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_servicio")
    private Integer idServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_servicio")
    private String nombreServicio;
    @Column(name = "precio_servicio")
    private BigInteger precioServicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descripcion_servicio")
    private String descripcionServicio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoServicio")
    private List<Citas> citasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idServicio")
    private List<ServicioPorEmpleado> servicioPorEmpleadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoServicio")
    private List<Procedimientos> procedimientosList;

    public Servicios() {
    }

    public Servicios(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public Servicios(Integer idServicio, String nombreServicio, String descripcionServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
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

    @XmlTransient
    @JsonIgnore
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    @XmlTransient
    @JsonIgnore
    public List<ServicioPorEmpleado> getServicioPorEmpleadoList() {
        return servicioPorEmpleadoList;
    }

    public void setServicioPorEmpleadoList(List<ServicioPorEmpleado> servicioPorEmpleadoList) {
        this.servicioPorEmpleadoList = servicioPorEmpleadoList;
    }

    @XmlTransient
    @JsonIgnore
    public List<Procedimientos> getProcedimientosList() {
        return procedimientosList;
    }

    public void setProcedimientosList(List<Procedimientos> procedimientosList) {
        this.procedimientosList = procedimientosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServicio != null ? idServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        if ((this.idServicio == null && other.idServicio != null) || (this.idServicio != null && !this.idServicio.equals(other.idServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Servicios[ idServicio=" + idServicio + " ]";
    }
    
}
