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
 * @author Veronica
 */
@Entity
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByIdEmpleado", query = "SELECT e FROM Empleados e WHERE e.idEmpleado = :idEmpleado")
    , @NamedQuery(name = "Empleados.findByNombreEmpleado", query = "SELECT e FROM Empleados e WHERE e.nombreEmpleado = :nombreEmpleado")
    , @NamedQuery(name = "Empleados.findByApellidosEmpleado", query = "SELECT e FROM Empleados e WHERE e.apellidosEmpleado = :apellidosEmpleado")
    , @NamedQuery(name = "Empleados.findByFechaNacimiento", query = "SELECT e FROM Empleados e WHERE e.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Empleados.findByIdTipoRh", query = "SELECT e FROM Empleados e WHERE e.idTipoRh = :idTipoRh")
    , @NamedQuery(name = "Empleados.findByCorreoElectronico", query = "SELECT e FROM Empleados e WHERE e.correoElectronico = :correoElectronico")
    , @NamedQuery(name = "Empleados.findByDireccion", query = "SELECT e FROM Empleados e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "Empleados.findByTelefonoFijo", query = "SELECT e FROM Empleados e WHERE e.telefonoFijo = :telefonoFijo")
    , @NamedQuery(name = "Empleados.findByTelefonoMovil", query = "SELECT e FROM Empleados e WHERE e.telefonoMovil = :telefonoMovil")
    , @NamedQuery(name = "Empleados.findByEstadoEmpleado", query = "SELECT e FROM Empleados e WHERE e.estadoEmpleado = :estadoEmpleado")
    , @NamedQuery(name = "Empleados.findByFechaContratoInicial", query = "SELECT e FROM Empleados e WHERE e.fechaContratoInicial = :fechaContratoInicial")
    , @NamedQuery(name = "Empleados.findByFechaContratoFinal", query = "SELECT e FROM Empleados e WHERE e.fechaContratoFinal = :fechaContratoFinal")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "apellidos_empleado")
    private String apellidosEmpleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_rh")
    private int idTipoRh;
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
    @Column(name = "estado_empleado")
    private boolean estadoEmpleado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_contrato_inicial")
    @Temporal(TemporalType.DATE)
    private Date fechaContratoInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_contrato_final")
    @Temporal(TemporalType.DATE)
    private Date fechaContratoFinal;
    @JoinColumn(name = "id_barrio", referencedColumnName = "id_barrio")
    @ManyToOne(optional = false)
    private Barrios idBarrio;
    @JoinColumn(name = "id_cargo", referencedColumnName = "id_cargo")
    @ManyToOne(optional = false)
    private Cargos idCargo;
    @JoinColumn(name = "id_ciudad", referencedColumnName = "id_ciudad")
    @ManyToOne(optional = false)
    private Ciudades idCiudad;
    @JoinColumn(name = "id_genero", referencedColumnName = "id_genero")
    @ManyToOne(optional = false)
    private Generos idGenero;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne(optional = false)
    private Perfiles idPerfil;
    @JoinColumn(name = "id_tipo_contrato", referencedColumnName = "id_tipo_contrato")
    @ManyToOne(optional = false)
    private TiposContrato idTipoContrato;
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_doc")
    @ManyToOne(optional = false)
    private TiposDocumento idTipoDocumento;
    @JoinColumn(name = "id_tipo_sangre", referencedColumnName = "id_tipo_sangre")
    @ManyToOne(optional = false)
    private TiposSangre idTipoSangre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private List<Procedimientos> procedimientosList;

    public Empleados() {
    }

    public Empleados(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Empleados(Integer idEmpleado, String nombreEmpleado, String apellidosEmpleado, Date fechaNacimiento, int idTipoRh, String correoElectronico, String direccion, long telefonoMovil, boolean estadoEmpleado, Date fechaContratoInicial, Date fechaContratoFinal) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidosEmpleado = apellidosEmpleado;
        this.fechaNacimiento = fechaNacimiento;
        this.idTipoRh = idTipoRh;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.estadoEmpleado = estadoEmpleado;
        this.fechaContratoInicial = fechaContratoInicial;
        this.fechaContratoFinal = fechaContratoFinal;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidosEmpleado() {
        return apellidosEmpleado;
    }

    public void setApellidosEmpleado(String apellidosEmpleado) {
        this.apellidosEmpleado = apellidosEmpleado;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdTipoRh() {
        return idTipoRh;
    }

    public void setIdTipoRh(int idTipoRh) {
        this.idTipoRh = idTipoRh;
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

    public boolean getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public void setEstadoEmpleado(boolean estadoEmpleado) {
        this.estadoEmpleado = estadoEmpleado;
    }

    public Date getFechaContratoInicial() {
        return fechaContratoInicial;
    }

    public void setFechaContratoInicial(Date fechaContratoInicial) {
        this.fechaContratoInicial = fechaContratoInicial;
    }

    public Date getFechaContratoFinal() {
        return fechaContratoFinal;
    }

    public void setFechaContratoFinal(Date fechaContratoFinal) {
        this.fechaContratoFinal = fechaContratoFinal;
    }

    public Barrios getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(Barrios idBarrio) {
        this.idBarrio = idBarrio;
    }

    public Cargos getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargos idCargo) {
        this.idCargo = idCargo;
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

    public Perfiles getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfiles idPerfil) {
        this.idPerfil = idPerfil;
    }

    public TiposContrato getIdTipoContrato() {
        return idTipoContrato;
    }

    public void setIdTipoContrato(TiposContrato idTipoContrato) {
        this.idTipoContrato = idTipoContrato;
    }

    public TiposDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TiposDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public TiposSangre getIdTipoSangre() {
        return idTipoSangre;
    }

    public void setIdTipoSangre(TiposSangre idTipoSangre) {
        this.idTipoSangre = idTipoSangre;
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
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.lindasmascotas.entities.Empleados[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
