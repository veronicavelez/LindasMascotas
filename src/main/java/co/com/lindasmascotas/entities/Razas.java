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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "razas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Razas.findAll", query = "SELECT r FROM Razas r")
    , @NamedQuery(name = "Razas.findByIdRaza", query = "SELECT r FROM Razas r WHERE r.idRaza = :idRaza")
    , @NamedQuery(name = "Razas.findByNombreRaza", query = "SELECT r FROM Razas r WHERE r.nombreRaza = :nombreRaza")
    , @NamedQuery(name = "Razas.findRazasByEspecies", query = "SELECT r FROM Razas r WHERE r.idEspecie = :idEspecie")})
public class Razas implements Serializable {

    @JoinColumn(name = "id_especie", referencedColumnName = "id_especie")
    @ManyToOne(optional = false)
    private Especies idEspecie;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_raza")
    private Integer idRaza;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_raza")
    private String nombreRaza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRaza")
    private List<Mascotas> mascotasList;

    public Razas() {
    }

    public Razas(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public Razas(Integer idRaza, String nombreRaza) {
        this.idRaza = idRaza;
        this.nombreRaza = nombreRaza;
    }

    public Integer getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Integer idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombreRaza() {
        return nombreRaza;
    }

    public void setNombreRaza(String nombreRaza) {
        this.nombreRaza = nombreRaza;
    }

    @XmlTransient
    @JsonIgnore
    public List<Mascotas> getMascotasList() {
        return mascotasList;
    }

    public void setMascotasList(List<Mascotas> mascotasList) {
        this.mascotasList = mascotasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRaza != null ? idRaza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Razas)) {
            return false;
        }
        Razas other = (Razas) object;
        if ((this.idRaza == null && other.idRaza != null) || (this.idRaza != null && !this.idRaza.equals(other.idRaza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Razas[ idRaza=" + idRaza + " ]";
    }
    
    public Especies getIdEspecie() {
        return idEspecie;
}

    public void setIdEspecie(Especies idEspecie) {
        this.idEspecie = idEspecie;
    }
    
}
