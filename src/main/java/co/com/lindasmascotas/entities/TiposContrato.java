/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "tipos_contrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposContrato.findAll", query = "SELECT t FROM TiposContrato t")
    , @NamedQuery(name = "TiposContrato.findByIdTipoContrato", query = "SELECT t FROM TiposContrato t WHERE t.idTipoContrato = :idTipoContrato")
    , @NamedQuery(name = "TiposContrato.findByNombreContrato", query = "SELECT t FROM TiposContrato t WHERE t.nombreContrato = :nombreContrato")})
public class TiposContrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_contrato")
    private Integer idTipoContrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_contrato")
    private String nombreContrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoContrato")
    private List<Empleados> empleadosList;

    public TiposContrato() {
    }

    public TiposContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public TiposContrato(Integer idTipoContrato, String nombreContrato) {
        this.idTipoContrato = idTipoContrato;
        this.nombreContrato = nombreContrato;
    }

    public Integer getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(Integer idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public String getNombreContrato() {
        return nombreContrato;
    }

    public void setNombreContrato(String nombreContrato) {
        this.nombreContrato = nombreContrato;
    }

    @XmlTransient
    @JsonIgnore
    public List<Empleados> getEmpleadosList() {
        return empleadosList;
    }

    public void setEmpleadosList(List<Empleados> empleadosList) {
        this.empleadosList = empleadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoContrato != null ? idTipoContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposContrato)) {
            return false;
        }
        TiposContrato other = (TiposContrato) object;
        if ((this.idTipoContrato == null && other.idTipoContrato != null) || (this.idTipoContrato != null && !this.idTipoContrato.equals(other.idTipoContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.TiposContrato[ idTipoContrato=" + idTipoContrato + " ]";
    }
    
}
