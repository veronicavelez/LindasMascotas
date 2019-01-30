/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.lindasmascotas.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "propietarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Propietarios.findAll", query = "SELECT p FROM Propietarios p")
    , @NamedQuery(name = "Propietarios.findByIdPropietario", query = "SELECT p FROM Propietarios p WHERE p.idPropietario = :idPropietario")
    , @NamedQuery(name = "Propietarios.findByNombrePropietario", query = "SELECT p FROM Propietarios p WHERE p.nombrePropietario = :nombrePropietario")
    , @NamedQuery(name = "Propietarios.findByApellidosPropietario", query = "SELECT p FROM Propietarios p WHERE p.apellidosPropietario = :apellidosPropietario")
    , @NamedQuery(name = "Propietarios.findByFechaNacimiento", query = "SELECT p FROM Propietarios p WHERE p.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Propietarios.findByCorreoElectronico", query = "SELECT p FROM Propietarios p WHERE p.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "Propietarios.findByDireccion", query = "SELECT p FROM Propietarios p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Propietarios.findByTelefonoFijo", query = "SELECT p FROM Propietarios p WHERE p.telefonoFijo = :telefonoFijo")
    , @NamedQuery(name = "Propietarios.findByTelefonoMovil", query = "SELECT p FROM Propietarios p WHERE p.telefonoMovil = :telefonoMovil")
    , @NamedQuery(name = "Propietarios.findByEstado", query = "SELECT p FROM Propietarios p WHERE p.estado = :estado")})
public class Propietarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_propietario")
    private Integer idPropietario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_propietario")
    private String nombrePropietario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellidos_propietario")
    private String apellidosPropietario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "correo_electronico")
    private String correoElectronico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono_fijo")
    private Integer telefonoFijo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono_movil")
    private long telefonoMovil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @JoinColumn(name = "id_barrio", referencedColumnName = "id_barrio")
    @ManyToOne(optional = false)
    private Barrios idBarrio;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private Ciudades idCiudad;
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
    @ManyToOne(optional = false)
    private Generos idGenero;
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_doc")
    @ManyToOne(optional = false)
    private TiposDocumento idTipoDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPropietario")
    private List<Citas> citasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPropietario")
    private List<Mascotas> mascotasList;

    public Propietarios() {
    }

    public Propietarios(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public Propietarios(Integer idPropietario, String nombrePropietario, String apellidosPropietario, Date fechaNacimiento, String correoElectronico, String direccion, long telefonoMovil, boolean estado) {
        this.idPropietario = idPropietario;
        this.nombrePropietario = nombrePropietario;
        this.apellidosPropietario = apellidosPropietario;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.estado = estado;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getApellidosPropietario() {
        return apellidosPropietario;
    }

    public void setApellidosPropietario(String apellidosPropietario) {
        this.apellidosPropietario = apellidosPropietario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(Integer telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public long getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(long telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Barrios getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(Barrios idBarrio) {
        this.idBarrio = idBarrio;
    }

    public Ciudades getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudades idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Generos getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Generos idGenero) {
        this.idGenero = idGenero;
    }

    public TiposDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TiposDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    @XmlTransient
    @JsonIgnore
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

   //@XmlTransient
   // @JsonIgnore
    public List<Mascotas> getMascotasList() {
        return mascotasList;
    }

    public void setMascotasList(List<Mascotas> mascotasList) {
        this.mascotasList = mascotasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPropietario != null ? idPropietario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Propietarios)) {
            return false;
        }
        Propietarios other = (Propietarios) object;
        if ((this.idPropietario == null && other.idPropietario != null) || (this.idPropietario != null && !this.idPropietario.equals(other.idPropietario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Propietarios[ idPropietario=" + idPropietario + " ]";
    }
    
}
