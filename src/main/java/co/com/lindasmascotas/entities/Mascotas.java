/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Isa
 */
@Entity
@Table(name = "mascotas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascotas.findAll", query = "SELECT m FROM Mascotas m")
    , @NamedQuery(name = "Mascotas.findByIdMascota", query = "SELECT m FROM Mascotas m WHERE m.idMascota = :idMascota")
    , @NamedQuery(name = "Mascotas.findByNombreMascota", query = "SELECT m FROM Mascotas m WHERE m.nombreMascota = :nombreMascota")
    , @NamedQuery(name = "Mascotas.findByFechaNacimiento", query = "SELECT m FROM Mascotas m WHERE m.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Mascotas.findByPeso", query = "SELECT m FROM Mascotas m WHERE m.peso = :peso")
    , @NamedQuery(name = "Mascotas.findByEstado", query = "SELECT m FROM Mascotas m WHERE m.estado = :estado")
    , @NamedQuery(name = "Mascotas.findByVive", query = "SELECT m FROM Mascotas m WHERE m.vive = :vive")})
public class Mascotas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mascota")
    private Integer idMascota;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_mascota")
    private String nombreMascota;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso")
    private BigDecimal peso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vive")
    private boolean vive;
    @JoinColumn(name = "id_especie", referencedColumnName = "id_especie")
    @ManyToOne(optional = false)
    private Especies idEspecie;
    @JoinColumn(name = "id_propietario", referencedColumnName = "id_propietario")
    @ManyToOne(optional = false)
    private Propietarios idPropietario;
    @JoinColumn(name = "id_raza", referencedColumnName = "id_raza")
    @ManyToOne(optional = false)
    private Razas idRaza;
    @JoinColumn(name = "id_sexo", referencedColumnName = "id_sexo")
    @ManyToOne(optional = false)
    private Sexos idSexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMascota")
    private List<Procedimientos> procedimientosList;

    public Mascotas() {
    }

    public Mascotas(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public Mascotas(Integer idMascota, String nombreMascota, BigDecimal peso, boolean estado, boolean vive) {
        this.idMascota = idMascota;
        this.nombreMascota = nombreMascota;
        this.peso = peso;
        this.estado = estado;
        this.vive = vive;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean getVive() {
        return vive;
    }

    public void setVive(boolean vive) {
        this.vive = vive;
    }

    public Especies getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Especies idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Propietarios getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Propietarios idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Razas getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Razas idRaza) {
        this.idRaza = idRaza;
    }

    public Sexos getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Sexos idSexo) {
        this.idSexo = idSexo;
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
        hash += (idMascota != null ? idMascota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascotas)) {
            return false;
        }
        Mascotas other = (Mascotas) object;
        if ((this.idMascota == null && other.idMascota != null) || (this.idMascota != null && !this.idMascota.equals(other.idMascota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Mascotas[ idMascota=" + idMascota + " ]";
    }
    
}
